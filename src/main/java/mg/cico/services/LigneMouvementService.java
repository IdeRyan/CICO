package mg.cico.services;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import mg.cico.DAO.LigneMouvementDAO;
import mg.cico.models.LigneMouvement;

public class LigneMouvementService {

    private final LigneMouvementDAO dao;

    public LigneMouvementService(Connection c) {
        this.dao = new LigneMouvementDAO(c);
    }

    public void addLigne(LigneMouvement l) {
        validate(l);
        if (l.getDateMouvement() == null)
            l.setDateMouvement(LocalDateTime.now());
        dao.save(l);
    }

    public void updateLigne(LigneMouvement l) {
        validate(l);
        l.setDateMouvement(LocalDateTime.now());
        dao.update(l);
    }

    public void deleteLigne(int idLigne) {
        if (idLigne <= 0)
            throw new IllegalArgumentException("ID Ligne invalide");
        dao.delete(idLigne);
    }

    public LigneMouvement getLigneById(int idLigne) {
        if (idLigne <= 0)
            throw new IllegalArgumentException("ID Ligne invalide");
        return dao.findById(idLigne);
    }

    public List<LigneMouvement> getLignesByFiche(int idFiche) {
        if (idFiche <= 0)
            throw new IllegalArgumentException("ID Fiche invalide");
        return dao.findByFiche(idFiche);
    }

    public List<LigneMouvement> getAllLignes() {
        return dao.findAll();
    }

    private void validate(LigneMouvement l) {
        if (l.getIdFiche() <= 0)
            throw new IllegalArgumentException("ID Fiche invalide");
        if (l.getRefArcticle() == null || l.getRefArcticle().isBlank())
            throw new IllegalArgumentException("Référence article obligatoire");
        if (l.getQuantite() <= 0)
            throw new IllegalArgumentException("Quantité doit être supérieure à 0");
    }
}
