package ma.adria.document_validation.administration.reposetiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.adria.document_validation.administration.model.entities.Utilisateur;

import java.util.List;


@Repository
public interface UserReposetory extends JpaRepository<Utilisateur, Long>{
	boolean existsByEmail(String email);
	Utilisateur findByEmail(String email);
	List<Utilisateur> findByNom(String nom);
	List<Utilisateur> findByPrenom(String prenom);
	List<Utilisateur> findByStatut(String statut);
}
