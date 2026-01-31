package com.ism.admissions.admission.controller;

import com.ism.admissions.admission.service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AdmissionExportControllerImpl implements AdmissionExportController {
    private final ExcelExportService excelExportService;

    @Override
    public ResponseEntity<Resource> downloadExcelClasse(Long classeId) {
        try {
            ByteArrayInputStream in = excelExportService.exportAdmisParClasse(classeId);
            InputStreamResource resource = new InputStreamResource(in);

            // On définit le nom du fichier dynamiquement si possible, sinon fixe
            String fileName = "liste_admis_classe_" + classeId + ".xlsx";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        } catch (IOException e) {
            // Ici tu peux lancer une exception personnalisée gérée par ton GlobalExceptionHandler
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadAllExports() {
        return null;
    }
}
