package com.flexigp.admissions.candidature.service;

import com.flexigp.admissions.candidature.domain.Candidature;
import com.flexigp.admissions.candidature.domain.DocumentCandidature;
import com.flexigp.admissions.candidature.repository.CandidatureRepository;
import com.flexigp.admissions.infrastructure.storage.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class DocumentCandidatureServiceImpl implements DocumentCandidatureService {

    private final FileStorageService fileStorageService;
    private final CandidatureRepository candidatureRepository;

    @Override
    public void ajouterDocument(Long candidatureId, MultipartFile file, String typeDocument) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

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
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        DocumentCandidature document = candidature.getDocuments().stream()
                .filter(d -> d.getId().equals(documentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        // 1. Suppression du fichier S3
        fileStorageService.deleteFile(document.getStorageKey());

        // 2. Suppression du document côté domaine
        candidature.getDocuments().remove(document);

        candidatureRepository.save(candidature);

    }
}
