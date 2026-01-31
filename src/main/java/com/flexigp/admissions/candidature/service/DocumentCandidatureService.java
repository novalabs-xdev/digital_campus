package com.flexigp.admissions.candidature.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentCandidatureService {
    void ajouterDocument(
            Long candidatureId,
            MultipartFile file,
            String typeDocument
    );

    void supprimerDocument(
            Long candidatureId,
            Long documentId
    );

}
