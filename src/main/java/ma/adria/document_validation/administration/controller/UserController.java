package ma.adria.document_validation.administration.controller;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.request.user.*;
import ma.adria.document_validation.administration.dto.response.user.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.UserPageResponseDTO;
import ma.adria.document_validation.administration.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userservice;
    @PostMapping("/add")
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO user){

        return userservice.createUtilisateur(user);
    }

    @GetMapping(value = "/page")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserPageResponseDTO>> page( UserPageRequestDTO userPageRequestDTO) {
        return ResponseEntity.ok().body(userservice.getPage(userPageRequestDTO));
    }
    @GetMapping("admin/details/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UtilisateurDTO> getUserById(@PathVariable String id) {

        return ResponseEntity.ok().body(userservice.getUserById(id));
    }
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EditUserResponseDTO> edit(@RequestBody @Valid EditUserRequestDTO user) {
        return ResponseEntity.ok() //userid
                .body(userservice.editUser(user));
    }
    @PutMapping(value = "/profile/edit")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<EditUserProfileResponseDTO> editProfile(@RequestBody @Valid EditUserProfileRequestDTO user) {
        return ResponseEntity.ok() //KeycloakID
                .body(userservice.editProfile(user));
    }

    @PutMapping(value = "/resetPassword")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request) {
        userservice.resetPassword(request);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("user/details")
    public ResponseEntity<UtilisateurDTO> getCurrentUserDetails() {
        return ResponseEntity.ok().body(userservice.getCurrentUserDetails());
    }
   @GetMapping("/bykeycloak/{keycloakID}")
    public UtilisateurDTO getUserByKeycloakID(@PathVariable("keycloakID") String keyID){
       return userservice.getUserByKeycloakId(keyID);
   }
   @PostMapping("/test")
    public void f(){
        System.out.println("khdama");
   }
}

