package ma.adria.document_validation.administration.controller;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.request.*;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import ma.adria.document_validation.administration.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userservice;

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO user){

        return userservice.createUtilisateur(user);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<UserPageResponseDTO>> page(@RequestBody UserPageRequestDTO userPageRequestDTO) {

        return ResponseEntity.ok().body(userservice.getPage(userPageRequestDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUserById(@PathVariable UUID id) {

        return ResponseEntity.ok().body(userservice.getUserById(id));
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
