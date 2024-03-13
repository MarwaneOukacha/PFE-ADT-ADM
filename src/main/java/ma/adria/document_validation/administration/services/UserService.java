package ma.adria.document_validation.administration.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ma.adria.document_validation.administration.dto.Utilisateurdto;
import ma.adria.document_validation.administration.model.entities.Utilisateur;

@Service	
public interface UserService {
	public ResponseEntity<Utilisateurdto> createUtilisateur(Utilisateur utilisateur);
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateurs();
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursByPrenom(String prenom);
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursByNom(String nom);
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursBloque();
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursDeBloque();
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursActiver();
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursDesactiver();
	public ResponseEntity<Utilisateurdto> getUtilisateurById(long id); 
	public ResponseEntity<Utilisateurdto> getUtilisateurByEmail(String email); 
	public ResponseEntity<Utilisateurdto> updateUtilisateur(String email,Utilisateur utilisateur);
	public ResponseEntity<HttpStatus> bloquerUtilisateur(String email);
	public ResponseEntity<HttpStatus> debloquerUtilisateur(String email);
	public ResponseEntity<HttpStatus> activerUtilisateur(String email);
	public ResponseEntity<HttpStatus> desactiverUtilisateur(String email);
	public ResponseEntity<HttpStatus> updateNbrTransactionUtilisateur(String email,int nbr);
	public ResponseEntity<List<Utilisateurdto>> rechercherUtilisateurMulticriteres(Utilisateurdto dto);
}
