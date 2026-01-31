package mg.cico.services;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import mg.cico.DAO.ProjetDAO;
import mg.cico.models.Projet;
import mg.cico.models.criteria.ProjetSearchCriteria;

public class ProjetService {

    private final ProjetDAO projetDAO;

    public ProjetService(Connection c) {
        this.projetDAO = new ProjetDAO(c);
    }

    public void createProjet(Projet p) {
        p.setDernierMaj(LocalDateTime.now());
        projetDAO.createProjet(p);
    }

    public void updateProjet(Projet p) {
        p.setDernierMaj(LocalDateTime.now());
        projetDAO.updateProjet(p);
    }

    public List<Projet> getAllProjet() {
        return projetDAO.getAllProjet();
    }

    public List<Projet> searchByCriteria(ProjetSearchCriteria criteria) {
        return projetDAO.searchByCriteria(criteria);
    }

    public Projet getProjetById(int idProjet) {
        List<Projet> projets = projetDAO.searchByCriteria(
            new ProjetSearchCriteria.Builder().idProjet(idProjet).build()
        );
        return projets.isEmpty() ? null : projets.get(0);
    }
}
