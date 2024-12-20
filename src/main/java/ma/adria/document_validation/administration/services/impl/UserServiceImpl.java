package ma.adria.document_validation.administration.services.impl;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.dto.keycloak.KeycloakUserDTO;
import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.request.user.*;
import ma.adria.document_validation.administration.dto.response.user.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.UserPageResponseDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakService keycloakService;

    private final UtilisateurMapper utilisateurMapper;

    private final IUserDAO userDAO;

    private final PasswordEncoder passwordencoder;

    private final IADTConstDAO iADTConstDAO;

    private final UserSpecification userSpecification;

    private final UserUtils utils;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<CreateUserResponseDTO> createUtilisateur(CreateUserRequestDTO userRequestDTO) {

        if (userDAO.existsByUserName(userRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("this user is already exist");
        }

        Utilisateur user = Utilisateur.builder()
                .email(userRequestDTO.getEmail())
                .profil(UserProfile.USER)
                .statut(UserStatus.DISABLED)
                .nom(userRequestDTO.getNom())
                .prenom(userRequestDTO.getPrenom())
                .numTele(userRequestDTO.getNumTele())
                .nbrMaxTransactions(Integer.parseInt(iADTConstDAO.findADTConstByCode(ADTConstCode.TRANSACTION_LIMIT_PER_DAY).getValue()))
                .sizeMax(Integer.parseInt(iADTConstDAO.findADTConstByCode(ADTConstCode.DOCUMENT_MAX_SIZE_MB).getValue()))
                .password(passwordencoder.encode(userRequestDTO.getPassword())).build();

        user = userDAO.save(user);

        KeycloakUserDTO keycloakUserDTO = utilisateurMapper.toKeycloakUserDTO(user, userRequestDTO.getPassword());

        String keycloakId = keycloakService.addUserToKeycloak(keycloakUserDTO);
        keycloakService.AssignRoleToUser(keycloakId,"USER");
        user.setKeycloakId(keycloakId);
        user = userDAO.save(user);
        //TODO:send email to activate acount
        CreateUserResponseDTO responseDTO = utilisateurMapper.toCreateUtilisateurResponseDTO(user);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);


    }


    @Override
    public Page<UserPageResponseDTO> getPage(UserPageRequestDTO userPageRequestDTO) {

        Specification<Utilisateur> userSpec = getUserSpecification(userPageRequestDTO, utils.getCurrentUser().getEmail());
        List<Sort.Order> orders = SortUtils.getOrders(userPageRequestDTO.getSort());

        Pageable pageable = PageRequest.of(userPageRequestDTO.getPage(), userPageRequestDTO.getSize(), Sort.by(orders));

        Page<Utilisateur> usersPage = userDAO.getPage(userSpec, pageable);
        return usersPage.map(
                utilisateurMapper::mapUserToUserSearchResponseDTO

        );
    }

    @Override
    public EditUserProfileResponseDTO editProfile(EditUserProfileRequestDTO user) {

        Utilisateur utilisateur = userDAO.findById(UUID.fromString(user.getId()));

        if (utilisateur == null) {
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID);
        }

        if (StringUtils.hasText(user.getEmail())) {
            utilisateur.setEmail(user.getEmail());
        }

        if (StringUtils.hasText(user.getPrenom())) {
            utilisateur.setPrenom(user.getPrenom());
        }

        if (StringUtils.hasText(user.getNom())) {
            utilisateur.setNom(user.getNom());
        }
        if (StringUtils.hasText(user.getNumTele())) {
            utilisateur.setNumTele(user.getNumTele());
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
        keycloakService.updatePassword(request.getNewPassword());
        userDAO.save(user);
    }

    @Override
    public UtilisateurDTO getUserById(String id) {

        Utilisateur user = userDAO.findById(UUID.fromString(id));

        return utilisateurMapper.toUtilisateurDTO(user);
    }

    @Override
    public UtilisateurDTO getUserByKeycloakId(String id) {
        Utilisateur user = userDAO.findByKeycloakUserId(id);
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toUtilisateurDTO(user);
        return utilisateurDTO;
    }

    @Override
    public EditUserResponseDTO editUser(EditUserRequestDTO user) {
        Utilisateur utilisateur = userDAO.findById(UUID.fromString(user.getId()));

        if (utilisateur == null) {
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID);
        }

        if (StringUtils.hasText(user.getEmail())) {
            utilisateur.setEmail(user.getEmail());
        }

        if (StringUtils.hasText(user.getPrenom())) {
            utilisateur.setPrenom(user.getPrenom());
        }

        if (StringUtils.hasText(user.getNom())) {
            utilisateur.setNom(user.getNom());
        }

        if (StringUtils.hasText(user.getNumTele())) {
            utilisateur.setNumTele(user.getNumTele());
        }


        if (StringUtils.hasText(user.getPassword())) {
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

        Utilisateur updateUser = userDAO.save(utilisateur);
        if (updateUser != null) {
            keycloakService.updateUsername(user.getEmail());
        }


        return utilisateurMapper.toEditUtilisateurResponseDTO(utilisateur);
    }

    @Override
    public UtilisateurDTO getCurrentUserDetails() {
        Utilisateur currentUser= utils.getCurrentUser();
        return utilisateurMapper.toUtilisateurDTO(currentUser);
    }


    private Specification<Utilisateur> getUserSpecification(UserPageRequestDTO request, String connectedUserEmail) {
        Specification<Utilisateur> spec = (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));

        spec = spec.and(userSpecification.emailDifferent(connectedUserEmail));

        // Vérifie si tous les champs de recherche sont vides ou nuls
        boolean allFieldsEmptyOrNull = !StringUtils.hasText(request.getPrenom()) &&
                !StringUtils.hasText(request.getStatut()) &&
                !StringUtils.hasText(request.getNumTele()) &&
                !StringUtils.hasText(request.getEmail()) &&
                !StringUtils.hasText(request.getNom());

        if (allFieldsEmptyOrNull) {
            return spec;
        }

        if (StringUtils.hasText(request.getEmail())) {
            spec = spec.and(userSpecification.emailEquals(request.getEmail()));
        }

        if (StringUtils.hasText(request.getPrenom())) {
            spec = spec.and(userSpecification.firstNameLike(request.getPrenom()));
        }

        if (StringUtils.hasText(request.getNom())) {
            spec = spec.and(userSpecification.lastNameLike(request.getNom()));
        }

        if (StringUtils.hasText(request.getStatut())) {
            spec = spec.and(userSpecification.statusEqual(UserStatus.valueOf(request.getStatut())));
        }

        if (StringUtils.hasText(request.getNumTele())) {
            spec = spec.and(userSpecification.telephoneNumberEquals(request.getNumTele()));
        }

        return spec;
    }

}
