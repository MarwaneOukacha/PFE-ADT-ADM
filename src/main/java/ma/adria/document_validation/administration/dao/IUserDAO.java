package ma.adria.document_validation.administration.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.UserStatus;



public interface IUserDAO {
	List<Utilisateur> findAll();
    Utilisateur save(Utilisateur user);

    Utilisateur findByUserName(String username);

    boolean existsByUserName(String username);

    Utilisateur findById(UUID id);

    Page<Utilisateur> getPage(Specification<Utilisateur> userSpecification, Pageable pageable);

    Utilisateur findByKeycloakUserId(String string);

    long count();

    long countByStatus(UserStatus userStatus);
}
