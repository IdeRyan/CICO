package mg.cico.models;

import java.time.LocalDate;

import mg.cico.models.enums.EtatFiche;
import mg.cico.models.enums.TypeMvt;

public class FicheMouvement {
    private int idFiche;
    private String numeroFiche;
    private LocalDate dateCreation;
    private EtatFiche etat;
    private TypeMvt typeMvt;
    private int idProjet;
    private int idResponsable;
    private String referenceDoc;
    private LocalDate dernierMaj;
    
    private String ref_article;

    public int getIdFiche() {
        return idFiche;
    }
    public void setIdFiche(int idFiche) {
        this.idFiche = idFiche;
    }
    public LocalDate getDernierMaj() {
        return dernierMaj;
    }
    public void setDernierMaj(LocalDate dernierMaj) {
        this.dernierMaj = dernierMaj;
    }
    public int getIdProjet() {
        return idProjet;
    }
    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }
    public String getRef_article() {
        return ref_article;
    }
    public void setRef_article(String ref_article) {
        this.ref_article = ref_article;
    }

    public TypeMvt getTypeMvt() {
        return typeMvt;
    }

    public void setTypeMvt(TypeMvt typeMvt) {
        this.typeMvt = typeMvt;
    }

    public EtatFiche getEtat() {
        return etat;
    }

    public void setEtat(EtatFiche etat) {
        this.etat = etat;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getNumeroFiche() {
        return numeroFiche;
    }

    public void setNumeroFiche(String numeroFiche) {
        this.numeroFiche = numeroFiche;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getReferenceDoc() {
        return referenceDoc;
    }

    public void setReferenceDoc(String referenceDoc) {
        this.referenceDoc = referenceDoc;
    }

    
}
