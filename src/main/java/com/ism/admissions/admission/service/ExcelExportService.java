package com.ism.admissions.admission.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ExcelExportService {
    /**
     * Génère un fichier Excel pour une classe spécifique contenant tous les admis validés.
     */
    ByteArrayInputStream exportAdmisParClasse(Long classeId) throws IOException;

    // Export global (Arborescence complète en ZIP)
    byte[] exportFullArchiveZip() throws IOException;
}
