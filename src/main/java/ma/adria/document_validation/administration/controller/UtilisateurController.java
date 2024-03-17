package ma.adria.document_validation.administration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.dto.request.ResetPasswordRequestDTO;
import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.request.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserProfileRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.UserPageRequestDTO;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import ma.adria.document_validation.administration.mapper.UtilisateurMapper;
import ma.adria.document_validation.administration.reposetiry.UserRepository;
import ma.adria.document_validation.administration.reposetiry.specifications.UserSpecification;
import ma.adria.document_validation.administration.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UserRepository userRepository;
    private final UserSpecification userSpecification;
    private final UserService userservice;
    private final UtilisateurMapper utilisateurMapper;
    private final IUserDAO userDAO;


    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO user) throws JsonProcessingException {

        return userservice.createUtilisateur(user);
    }
    @GetMapping(value = "/page")
    public ResponseEntity<Page<UserPageResponseDTO>> page(@RequestBody UserPageRequestDTO userPageRequestDTO) {

        return ResponseEntity.ok()
                .body(userservice.getPage(userPageRequestDTO));
    }
    @GetMapping("/details")
    public ResponseEntity<UtilisateurDTO> getUserById(@RequestParam String id) {
        return ResponseEntity.ok()
                .body(userservice.getUserById(id));
    }
    @PutMapping("/edit")
    public ResponseEntity<EditUserResponseDTO> edit(@RequestBody @Valid EditUserRequestDTO user) {
        return ResponseEntity.ok()
                .body(userservice.editUser(user));
    }
    @PutMapping(value = "/profile/edit")
    public ResponseEntity<EditUserProfileResponseDTO> editProfile(@RequestBody @Valid EditUserProfileRequestDTO user) {
        return ResponseEntity.ok()
                .body(userservice.editProfile(user));
    }

    @PutMapping(value = "/resetPassword")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request) {
        userservice.resetPassword(request);
        return ResponseEntity.ok().build();
    }



}
