package ma.adria.document_validation.administration.util;

import lombok.*;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.exception.ResourceNotFoundException;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter @Getter
@Component
public class UserUtils {
    private final IUserDAO userDAO;
    public Utilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String keycloakID=authentication.getName();
            Utilisateur utilisateur=userDAO.findByKeycloakUserId(keycloakID);
            return utilisateur;
        } else {
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND) ;
        }
    }
}
