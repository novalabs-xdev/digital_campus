package com.ism.admissions.candidature.service;

import com.ism.admissions.candidature.domain.Candidature;
import com.ism.admissions.candidature.domain.DocumentCandidature;
import com.ism.admissions.candidature.exception.CandidatureNotFoundException;
import com.ism.admissions.candidature.exception.DocumentNotFoundException;
import com.ism.admissions.candidature.repository.CandidatureRepository;
import com.ism.admissions.exception.business.BadRequestException;
import com.ism.admissions.infrastructure.storage.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class DocumentCandidatureServiceImpl implements DocumentCandidatureService {

    private static final Set<String> TYPES_MIME_AUTORISES = Set.of(
            "application/pdf",
            "image/jpeg",
            "image/png"
    );
    private static final long TAILLE_MAX = 10 * 1024 * 1024; // 10 MB

    private final FileStorageService fileStorageService;
    private final CandidatureRepository candidatureRepository;
    private final Tika tika = new Tika();

    @Override
    public void ajouterDocument(Long candidatureId, MultipartFile file, String typeDocument) {
        // Validation taille
        if (file.getSize() > TAILLE_MAX) {
            throw new BadRequestException("Le fichier dépasse la taille maximale autorisée (10 MB)");
        }

        // Validation type MIME via Tika (analyse le contenu, pas l'extension)
        try {
            String mimeType = tika.detect(file.getInputStream());
            if (!TYPES_MIME_AUTORISES.contains(mimeType)) {
                throw new BadRequestException(
                        "Type de fichier non autorisé : " + mimeType + ". Types acceptés : PDF, JPEG, PNG");
            }
        } catch (IOException e) {
            throw new BadRequestException("Impossible de lire le fichier uploadé");
        }

        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new CandidatureNotFoundException(candidatureId));

        // 1. Upload vers S3
        String storageKey = fileStorageService.uploadFile(file, "candidatures");

        // 2. Création du document métier
        DocumentCandidature document = DocumentCandidature.builder()
                .candidature(candidature)
                .typeDocument(typeDocument)
                .nomOriginal(file.getOriginalFilename())
                .storageKey(storageKey)
                .dateUpload(LocalDateTime.now())
                .build();

        // 3. Rattachement à la candidature
        candidature.getDocuments().add(document);

        // 4. Sauvegarde (cascade)
        candidatureRepository.save(candidature);

    }

    @Override
    public void supprimerDocument(Long candidatureId, Long documentId) {

        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new CandidatureNotFoundException(candidatureId));

        DocumentCandidature document = candidature.getDocuments().stream()
                .filter(d -> d.getId().equals(documentId))
                .findFirst()
                .orElseThrow(() -> new DocumentNotFoundException(documentId));

        // 1. Suppression du fichier S3
        fileStorageService.deleteFile(document.getStorageKey());

        // 2. Suppression du document côté domaine
        candidature.getDocuments().remove(document);

        candidatureRepository.save(candidature);

    }
}
