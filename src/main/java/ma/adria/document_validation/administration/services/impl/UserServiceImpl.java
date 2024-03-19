package ma.adria.document_validation.administration.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.dto.CredentialDTO;
import ma.adria.document_validation.administration.dto.request.ResetPasswordRequestDTO;
import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.UtilisateurKycDTO;
import ma.adria.document_validation.administration.dto.request.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserProfileRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.UserPageRequestDTO;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import ma.adria.document_validation.administration.exception.GenericException;
import ma.adria.document_validation.administration.exception.ResourceAlreadyExistsException;
import ma.adria.document_validation.administration.exception.ResourceNotFoundException;
import ma.adria.document_validation.administration.mapper.UtilisateurMapper;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;
import ma.adria.document_validation.administration.reposetiry.specifications.UserSpecification;
import ma.adria.document_validation.administration.services.UserService;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import ma.adria.document_validation.administration.util.SortUtils;
import ma.adria.document_validation.administration.util.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final KeycloakService kycservice;
    private final UtilisateurMapper utilisateurMapper;
    private final IUserDAO userDAO;
    private final PasswordEncoder passwordencoder;
    private final IADTConstDAO iADTConstDAO;
    private final UserSpecification userSpecification;
    private final UserUtils utils;
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
            kdto= kdto.toBuilder().enabled(true).enabled(true).credentials(Arrays.asList(CredentialDTO
                    .builder().type("password").value(utilisateur.getPassword()).temporary(false).build())).build();
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
    public Page<UserPageResponseDTO> getPage(UserPageRequestDTO userPageRequestDTO) {

        Specification<Utilisateur> userSpec = getUserSpecification(userPageRequestDTO,utils.getCurrentUser().getEmail());
        List<Sort.Order> orders = SortUtils.getOrders(userPageRequestDTO.getSort());

        Pageable pageable = PageRequest.of(userPageRequestDTO.getPage(), userPageRequestDTO.getSize(), Sort.by(orders));

        Page<Utilisateur> usersPage = userDAO.getPage(userSpec, pageable);
        return usersPage.map(
            //TODO:il faut verifier le cas ou tous les chmanps sont vide
                utilisateurMapper::mapUserToUserSearchResponseDTO

        );
    }

    @Override
    public EditUserProfileResponseDTO editProfile(EditUserProfileRequestDTO user) {

        Utilisateur utilisateur = userDAO.findByUserName(user.getEmail());

        if (utilisateur == null) {
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID);
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            utilisateur.setEmail(user.getEmail());
        }

        if (user.getPrenom() != null && !user.getPrenom().isEmpty()) {
            utilisateur.setPrenom(user.getPrenom());
        }

        if (user.getNom() != null && !user.getNom().isEmpty()) {
            utilisateur.setNom(user.getNom());
        }
        userDAO.save(utilisateur);
        return utilisateurMapper.toEditUtilisateurProfileResponseDTO(utilisateur);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO request) {
        Utilisateur connectedUser = utils.getCurrentUser();

        String encodedPassword = connectedUser.getPassword();

        if (request.getOldPassword() == null) {
            throw new GenericException(ErrorCode.ACCOUNT_USER_OLD_PASSWORD_MISSING);
        }

        if (request.getNewPassword() == null) {
            throw new GenericException(ErrorCode.ACCOUNT_USER_NEW_PASSWORD_MISSING);
        }


        if (!passwordencoder.matches(request.getOldPassword(), encodedPassword)) {
            throw new GenericException(ErrorCode.ACCOUNT_USER_OLD_PASSWORD_INCORRECT);
        }

        if (passwordencoder.matches(request.getNewPassword(), encodedPassword)) {
            throw new GenericException(ErrorCode.ACCOUNT_USER_RESET_OLD_PASSWORD_MATCH);
        }

        Utilisateur user = userDAO.findById(connectedUser.getId());

        user.setPassword(passwordencoder.encode(request.getNewPassword()));
        kycservice.updatePassword(request.getNewPassword());
        userDAO.save(user);
    }

    @Override
    public UtilisateurDTO getUserById(String id) {
        Utilisateur user= userDAO.findByKeycloakUserId(id);
        UtilisateurDTO dto=utilisateurMapper.toUtilisateurDTO(user);
        return dto;
    }

    @Override
    public EditUserResponseDTO editUser(EditUserRequestDTO user) {
        Utilisateur utilisateur = userDAO.findByUserName(user.getOldEmail());

        if (utilisateur == null) {
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID);
        }

        if (StringUtils.hasText(user.getEmail())) {
            utilisateur.setEmail(user.getEmail());
        }

        if (user.getPrenom() != null && !user.getPrenom().isEmpty()) {
            utilisateur.setPrenom(user.getPrenom());
        }

        if (user.getNom() != null && !user.getNom().isEmpty()) {
            utilisateur.setNom(user.getNom());
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordencoder.encode(user.getPassword()));
        }

        if (user.getSizeMax() != 0) {
            utilisateur.setSizeMax(user.getSizeMax());
        }

        if (user.getNbrMaxTransactions() != 0) {
            utilisateur.setNbrMaxTransactions(user.getNbrMaxTransactions());
        }

        if (user.getStatut() != null) {
            utilisateur.setStatut(UserStatus.valueOf(user.getStatut()));
        }

        Utilisateur updateUser= userDAO.save(utilisateur);
        if(updateUser!=null){
            kycservice.updateUsername(user.getEmail());
        }


        return utilisateurMapper.toEditUtilisateurResponseDTO(utilisateur);
    }



    private Specification<Utilisateur> getUserSpecification(UserPageRequestDTO request, String connectedUserEmail) {

        Specification<Utilisateur> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(root.get("id"));

        spec = spec.and(userSpecification.emailDifferent(connectedUserEmail));

        if (request.getEmail() != null) {
            spec = spec.and(userSpecification.emailEquals(request.getEmail()));
        }

        if (request.getPrenom() != null) {
            spec = spec.and(userSpecification.firstNameLike(request.getPrenom()));
        }

        if (request.getNom() != null) {
            spec = spec.and(userSpecification.lastNameLike(request.getNom()));
        }

        if (request.getStatut() != null) {
            spec = spec.and(userSpecification.statusEqual(UserStatus.valueOf(request.getStatut())));
        }
        if (request.getEmail() != null) {
            spec = spec.and(userSpecification.emailEquals(request.getEmail()));
        }
        if (request.getNumTele() != null) {
            spec = spec.and(userSpecification.telephoneNumberEquals(request.getNumTele()));
        }
        return spec;

    }

}
