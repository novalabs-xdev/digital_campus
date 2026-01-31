package com.ism.admissions.config;

import com.ism.admissions.classe.domain.Classe;
import com.ism.admissions.classe.repository.ClasseRepository;
import com.ism.admissions.ecole.domain.Ecole;
import com.ism.admissions.ecole.repository.EcoleRepository;
import com.ism.admissions.user.domain.Role;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final EcoleRepository ecoleRepository;
    private final ClasseRepository classeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) {
        if (ecoleRepository.count() > 0) {
            log.info("Données déjà initialisées, skip seeding");
            return;
        }

        log.info("🌱 Démarrage du seeding des données...");

        // Créer écoles
        Ecole ism = creerEcole("ISM", "Institut Supérieur de Management");
        Ecole supdeco = creerEcole("SUPDECO", "École Supérieure de Commerce");
        Ecole pigier = creerEcole("PIGIER", "Pigier Sénégal");

        // Créer classes pour ISM
        creerClasse("MBA1", "Master Business Administration 1", ism, 30);
        creerClasse("MBA2", "Master Business Administration 2", ism, 25);
        creerClasse("M1-INFO", "Master 1 Informatique", ism, 40);

        // Créer classes pour SUPDECO
        creerClasse("M1-FINANCE", "Master 1 Finance", supdeco, 35);
        creerClasse("M2-COMPTA", "Master 2 Comptabilité", supdeco, 30);

        // Créer classes pour PIGIER
        creerClasse("L3-MARKET", "Licence 3 Marketing", pigier, 50);

        // Créer utilisateurs
        creerAdmin();
        creerDirecteur();
        creerAgent();

        log.info("✅ Seeding terminé avec succès !");
    }

    private Ecole creerEcole(String code, String nom) {
        Ecole ecole = new Ecole();
        ecole.setCode(code);
        ecole.setNom(nom);
        ecole.setActif(true);
        Ecole saved = ecoleRepository.save(ecole);
        log.info("  📚 École créée : {} ({})", nom, code);
        return saved;
    }

    private void creerClasse(String code, String libelle, Ecole ecole, Integer capacite) {
        Classe classe = new Classe();
        classe.setCode(code);
        classe.setLibelle(libelle);
        classe.setEcole(ecole);
        classe.setCapacite(capacite);
        classe.setActif(true);
        classeRepository.save(classe);
        log.info("    🎓 Classe créée : {} ({})", libelle, code);
    }

    private void creerAdmin() {
        if (userRepository.existsByEmail("admin@ism.sn")) return;

        User user = new User();
        user.setEmail("admin@ism.sn");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setPrenom("Admin");
        user.setNom("Système");
        user.setRole(Role.ADMIN);
        user.setEnabled(true);
        userRepository.save(user);
        log.info("  👤 Administrateur créé : {} ({})", "admin@ism.sn", "admin123");
    }

    private void creerDirecteur() {
        if (userRepository.existsByEmail("directeur@ism.sn")) return;

        User user = new User();
        user.setEmail("directeur@ism.sn");
        user.setPassword(passwordEncoder.encode("dir123"));
        user.setPrenom("Amadou");
        user.setNom("Diallo");
        user.setRole(Role.DIRECTEUR);
        user.setEnabled(true);
        userRepository.save(user);
        log.info("  👤 Directeur créé : {} ({})", "directeur@ism.sn", "dir123");
    }

    private void creerAgent() {
        if (userRepository.existsByEmail("agent@ism.sn")) return;

        User user = new User();
        user.setEmail("agent@ism.sn");
        user.setPassword(passwordEncoder.encode("agent123"));
        user.setPrenom("Fatou");
        user.setNom("Sall");
        user.setRole(Role.AGENT);
        user.setEnabled(true);
        userRepository.save(user);
        log.info("  👤 Agent créé : {} ({})", "agent@ism.sn", "agent123");
    }
}