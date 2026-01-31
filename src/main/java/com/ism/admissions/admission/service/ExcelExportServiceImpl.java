package com.ism.admissions.admission.service;

import com.ism.admissions.admission.domain.Admission;
import com.ism.admissions.admission.repository.AdmissionRepository;
import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.classe.repository.ClasseRepository;
import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.ecole.repository.EcoleRepository;
import com.ism.admissions.exception.business.ExportGenerationException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class ExcelExportServiceImpl implements ExcelExportService {

    private final AdmissionRepository admissionRepository;
    private final EcoleRepository ecoleRepository;
    private final ClasseRepository classeRepository;

    @Override
    public ByteArrayInputStream exportAdmisParClasse(Long classeId) {
        // 1. Récupérer les admissions pour la classe donnée
        List<Admission> admissions = admissionRepository.findByCandidatureClasseId(classeId);

        // Optionnel : Lever une exception si la classe est vide (règle métier)
        if (admissions.isEmpty()) {
            throw new ExportGenerationException("Aucun admis trouvé pour cette classe. Exportation impossible.");
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Admis");

            // 2. Création de l'en-tête (Header) selon les contraintes du projet (ISM 2026)
            String[] columns = {"Nom", "Prénom", "Sexe", "Date de Naissance", "École", "Classe", "Contacts"};
            Row headerRow = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Font en gras pour l'en-tête
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // 3. Remplissage des données à partir des entités liées
            int rowIdx = 1;
            for (Admission admission : admissions) {
                Row row = sheet.createRow(rowIdx++);
                var candidat = admission.getCandidature().getCandidat();
                var classe = admission.getCandidature().getClasse();

                row.createCell(0).setCellValue(candidat.getNom());
                row.createCell(1).setCellValue(candidat.getPrenom());
                row.createCell(2).setCellValue(candidat.getSexe().toString());
                row.createCell(3).setCellValue(candidat.getDateNaissance().toString());
                row.createCell(4).setCellValue(classe.getEcole().getNom());
                row.createCell(5).setCellValue(classe.getLibelle());
                row.createCell(6).setCellValue(candidat.getTelephone() + " / " + candidat.getEmail());
            }

            // Ajustement automatique de la largeur des colonnes
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new ExportGenerationException("Erreur lors de la génération du fichier Excel pour la classe " + classeId);
        }
    }

    @Override
    public byte[] exportFullArchiveZip() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {

            List<Ecole> ecoles = ecoleRepository.findAll();
            if (ecoles.isEmpty()) {
                throw new ExportGenerationException("Aucune école trouvée dans le système.");
            }

            for (Ecole ecole : ecoles) {
                // Utilisation de ton architecture academic/classe
                List<Classe> classes = classeRepository.findByEcoleId(ecole.getId());

                for (Classe classe : classes) {
                    try {
                        // On réutilise la méthode d'export de classe
                        ByteArrayInputStream excelIn = exportAdmisParClasse(classe.getId());

                        // Arborescence : "Nom_Ecole/Nom_Classe.xlsx"
                        String fileName = ecole.getNom().replace(" ", "_") + "/" +
                                classe.getLibelle().replace(" ", "_") + ".xlsx";

                        ZipEntry entry = new ZipEntry(fileName);
                        zos.putNextEntry(entry);

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = excelIn.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                        zos.closeEntry();
                    } catch (ExportGenerationException e) {
                        // On ignore les classes vides pour le ZIP ou on log l'info
                        continue;
                    }
                }
            }
            zos.finish();
            return baos.toByteArray();

        } catch (IOException e) {
            throw new com.ism.admissions.exception.business.ExportGenerationException("Échec critique de la génération de l'archive ZIP.");
        }
    }
}