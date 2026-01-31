package mg.cico.services;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import mg.cico.DAO.UtilisateurDAO;
import mg.cico.models.Utilisateur;
import mg.cico.utils.PasswordUtil;

public class UtilisateurService {
    private final UtilisateurDAO myDAO;

    public UtilisateurService(Connection c){
        this.myDAO = new UtilisateurDAO(c);
    }

    public Utilisateur authenticate(Utilisateur u){
        Utilisateur v = myDAO.findByEmail(u.getEmail());
        if(v != null && PasswordUtil.verify(u.getMotDePasse(), v.getMotDePasse())){
            v.setDernierLogin(LocalDateTime.now());
            myDAO.update(v);
            return v;
        }
        return null;
    }

    private void validateUtilisateur(Utilisateur u){
        if(u.getEmail() == null || !u.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            throw new IllegalArgumentException("Email invalide");
        }
        if(!PasswordUtil.checkPasswordStrength(u.getMotDePasse())){
            throw new IllegalArgumentException("Mot de passe trop faible");
        }
        if(myDAO.findByEmail(u.getEmail()) != null){
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }
    }

    public void addUtilisateur(Utilisateur u){
        validateUtilisateur(u);
        u.setActif(true);
        u.setDateCreation(LocalDateTime.now());
        u.setMotDePasse(PasswordUtil.hash(u.getMotDePasse()));
        myDAO.save(u);
    }

    public void updateUtilisateur(Utilisateur u){
        myDAO.update(u);
    }

    public void updatePassword(Utilisateur u){
        u.setMotDePasse(PasswordUtil.hash(u.getMotDePasse()));
        myDAO.updatePassword(u.getIdUtilisateur(), u.getMotDePasse());
    }

    public List<Utilisateur> getAllUtilisateur(){
        return myDAO.findAll();
    }
}
