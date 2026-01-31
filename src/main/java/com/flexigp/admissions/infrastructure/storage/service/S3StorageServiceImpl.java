package com.flexigp.admissions.infrastructure.storage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageServiceImpl implements FileStorageService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.endpoint:}") // Optionnel, utile pour MinIO en local
    private String endpoint;

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        // 1. Générer un nom de fichier unique pour éviter les collisions
        String fileName = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ) // Rend l'image lisible par le front
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("Fichier uploadé avec succès : {}", fileName);

            // 2. Retourner l'URL complète pour accéder au fichier
            return String.format("%s/%s/%s", endpoint, bucketName, fileName);

        } catch (IOException e) {
            log.error("Erreur lors de la lecture du fichier", e);
            throw new RuntimeException("Échec de l'upload du fichier");
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        // Extraire la clé (path) à partir de l'URL
        String key = fileUrl.substring(fileUrl.lastIndexOf(bucketName) + bucketName.length() + 1);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        log.info("Fichier supprimé de S3 : {}", key);
    }
}