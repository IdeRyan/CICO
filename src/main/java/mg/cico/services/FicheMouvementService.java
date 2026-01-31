package mg.cico.services;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import mg.cico.DAO.FicheMouvementDAO;
import mg.cico.models.FicheMouvement;
import mg.cico.models.enums.EtatFiche;
import mg.cico.models.enums.TypeMvt;

public class FicheMouvementService {

    private final FicheMouvementDAO dao;

    public FicheMouvementService(Connection c) {
        this.dao = new FicheMouvementDAO(c);
    }

    public void addFiche(FicheMouvement f) {
        if (f.getNumeroFiche() == null || f.getNumeroFiche().isBlank())
            throw new IllegalArgumentException("Le numéro de fiche est obligatoire");

        if (f.getDateCreation() == null)
            f.setDateCreation(LocalDate.now());

        if (f.getDernierMaj() == null)
            f.setDernierMaj(f.getDateCreation());

        if (f.getEtat() == null)
            f.setEtat(EtatFiche.BROUILLON);

        if (f.getTypeMvt() == null)
            f.setTypeMvt(TypeMvt.ENTREE);

        dao.save(f);
    }

    public void updateFiche(FicheMouvement f) {
        if (f.getIdFiche() <= 0)
            throw new IllegalArgumentException("ID de fiche invalide");
        f.setDernierMaj(LocalDate.now());
        dao.update(f);
    }

    public void deleteFiche(int idFiche) {
        if (idFiche <= 0)
            throw new IllegalArgumentException("ID de fiche invalide");
        dao.delete(idFiche);
    }

    public FicheMouvement getFicheById(int idFiche) {
        if (idFiche <= 0)
            throw new IllegalArgumentException("ID de fiche invalide");
        return dao.findById(idFiche);
    }

    public List<FicheMouvement> getAllFiches() {
        return dao.findAll();
    }
}
