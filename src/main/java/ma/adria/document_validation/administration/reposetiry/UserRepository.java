package ma.adria.document_validation.administration.reposetiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ma.adria.document_validation.administration.model.entities.Utilisateur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<Utilisateur, UUID> , JpaSpecificationExecutor<Utilisateur> {

	boolean existsByEmail(String email);

	Utilisateur findByEmail(String email);

	Utilisateur findByKeycloakId(String keycloakId);

	boolean existsByIdNotNull();
}
