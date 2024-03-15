package ma.adria.document_validation.administration.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.dto.CredentialDTO;
import ma.adria.document_validation.administration.dto.UtilisateurKycDTO;
import ma.adria.document_validation.administration.dto.request.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.UserPageRequestDTO;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import ma.adria.document_validation.administration.exception.ResourceAlreadyExistsException;
import ma.adria.document_validation.administration.exception.ResourceNotFoundException;
import ma.adria.document_validation.administration.mapper.UtilisateurMapper;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;
import ma.adria.document_validation.administration.services.UserService;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final KeycloakService kycservice;
    private final UtilisateurMapper utilisateurMapper;
    private final IUserDAO userDAO;
    private final PasswordEncoder passwordencoder;
    private final IADTConstDAO iADTConstDAO;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<CreateUserResponseDTO> createUtilisateur(CreateUserRequestDTO utilisateur) throws JsonProcessingException {
        if (userDAO.existsByUserName(utilisateur.getEmail()) == false) {
            Utilisateur user = Utilisateur.builder()
                    .email(utilisateur.getEmail())
                    .profil(UserProfile.USER)
                    .statut(UserStatus.DISABLED)
                    .nom(utilisateur.getNom())
                    .prenom(utilisateur.getPrenom()).numTele(utilisateur.getNumTele())
                    .nbrMaxTransactions(Integer.parseInt(iADTConstDAO.findADTConstByCode(ADTConstCode.MAX_NUMBRE_TRANSACTION).getValue())).sizeMax(Integer.parseInt(iADTConstDAO.findADTConstByCode(ADTConstCode.MAX_SIZE).getValue())).password(passwordencoder.encode(utilisateur.getPassword())).build();
            userDAO.save(user);
            UtilisateurKycDTO kdto=utilisateurMapper.toUtilisateurKycDTO(user);
            kdto= kdto.toBuilder().enabled(true).credentials(Arrays.asList(CredentialDTO
                    .builder().type("password").value(user.getPassword()).temporary(false).build())).build();
            String kycID=kycservice.addUserToKeycloak(kdto);
            user=user.toBuilder().keycloakId(kycID).build();
            Utilisateur u= userDAO.save(user);
            CreateUserResponseDTO res= utilisateurMapper.toCreateUtilisateurResponseDTO(utilisateur);
            res=res.toBuilder()
                    .ID(u.getId())
                    .statut(u.getStatut())
                    .profil(u.getProfil())
                    .nbrMaxTransactions(user.getNbrMaxTransactions())
                    .sizeMax(user.getSizeMax())
                    .build();
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }else {
            throw new ResourceAlreadyExistsException("this user is already exist");
        }

    }

    @Override
    public EditUserResponseDTO editUser(EditUserRequestDTO user) {
        if(userDAO.existsByUserName(user.getEmail())){
            if (user.getNom() != null && !user.getNom().equals("")) {

            }
        }else{
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID);
        }
        return null;
    }

    @Override
    public Page<UserPageResponseDTO> getPage(UserPageRequestDTO userPageRequestDTO) {
        return null;
    }

}
