package com.flexigp.admissions.infrastructure.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    /**
     * Upload un fichier et retourne son URL publique.
     * @param file Le fichier multipart envoyé par le front.
     * @param folder Le dossier cible (ex: "avatars", "trips").
     */
    String uploadFile(MultipartFile file, String folder);

    /**
     * Supprime un fichier à partir de son URL ou chemin.
     */
    void deleteFile(String fileUrl);
}
