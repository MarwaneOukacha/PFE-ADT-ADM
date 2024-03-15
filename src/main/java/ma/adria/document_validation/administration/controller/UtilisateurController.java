package ma.adria.document_validation.administration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.request.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.UserPageRequestDTO;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import ma.adria.document_validation.administration.mapper.UtilisateurMapper;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.reposetiry.UserRepository;
import ma.adria.document_validation.administration.reposetiry.specifications.UserSpecification;
import ma.adria.document_validation.administration.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UserRepository userRepository;
    private final UserSpecification userSpecification;
    private final UserService userservice;
    private final UtilisateurMapper utilisateurMapper;

    @GetMapping
    public List<Utilisateur> getUsers(@RequestBody UtilisateurDTO user) {
        Specification<Utilisateur> spec = Specification.where(null);

        if (user.getPrenom() != null && !user.getPrenom().isEmpty()) {
            spec = spec.and(userSpecification.firstNameLike(user.getPrenom()));
        }

        if (user.getNom() != null && !user.getNom().isEmpty()) {
            spec = spec.and(userSpecification.lastNameLike(user.getNom()));
        }

        if (user.getNum_tele() != null && !user.getNum_tele().isEmpty()) {
            spec = spec.and(userSpecification.telephoneNumberEquals(user.getNum_tele()));
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            spec = spec.and(userSpecification.emailEquals(user.getEmail()));
        }

        /*if (user.getProfil() != null) {
            spec = spec.and(userSpecification.profileEquals(UserProfile.valueOf(user.getProfil())));
        }

        if (user.getStatut() != null) {
            spec = spec.and(userSpecification.statusEqual());
        }*/

        return userRepository.findAll(spec);
    }
    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO user) throws JsonProcessingException {

        return userservice.createUtilisateur(user);
    }
    public ResponseEntity<EditUserResponseDTO> edit(@RequestBody @Valid EditUserRequestDTO user) {
        return ResponseEntity.ok()
                .body(userservice.editUser(user));
    }
    @GetMapping(value = "/page")
    public ResponseEntity<Page<UserPageResponseDTO>> page(UserPageRequestDTO userPageRequestDTO) {
        return ResponseEntity.ok()
                .body(userservice.getPage(userPageRequestDTO));
    }

}
