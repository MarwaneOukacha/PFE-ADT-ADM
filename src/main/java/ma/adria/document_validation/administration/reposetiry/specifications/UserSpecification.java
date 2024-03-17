package ma.adria.document_validation.administration.reposetiry.specifications;

import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class UserSpecification {

    public Specification<Utilisateur> firstNameLike(String prenom) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("prenom")), "%" + prenom + "%");
    }

    public Specification<Utilisateur> statusEqual(UserStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("statut"), status);
    }
    public Specification<Utilisateur> lastNameLike(String nom) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nom")), "%" + nom + "%");
    }
    public Specification<Utilisateur> telephoneNumberEquals(String telephoneNumber) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(telephoneNumber)) {
                return criteriaBuilder.equal(root.get("numTele"), telephoneNumber);
            }
            return null;
        };
    }
    public Specification<Utilisateur> emailEquals(String email) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(email)) {
                return criteriaBuilder.equal(root.get("email"), email);
            }
            return null;
        };
    }

    public Specification<Utilisateur> profileEquals(UserProfile profile) {
        return (root, query, criteriaBuilder) -> {
            if (profile != null) {
                return criteriaBuilder.equal(root.get("profil"), profile);
            }
            return null;
        };
    }
    public Specification<Utilisateur> emailDifferent(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("email"), email);
    }
}
