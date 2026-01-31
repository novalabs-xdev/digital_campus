package com.flexigp.admissions.admission.generator;

import com.flexigp.admissions.candidature.domain.Candidature;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AdmissionNumberGeneratorImpl implements AdmissionNumberGenerator {
    private static final AtomicLong SEQUENCE = new AtomicLong(0);

    @Override
    public String genererNumero(Candidature candidature) {

        String annee = Year.now().toString();

        // À terme : code école depuis l’entité Ecole
        String codeEcole = "ECO";

        long seq = SEQUENCE.incrementAndGet();

        return String.format(
                "AD-%s-%s-%06d",
                annee,
                codeEcole,
                seq
        );
    }
}
