package ma.adria.document_validation.administration.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateUserResponseDTO {

    private UUID id;
    private String prenom;
    private String nom;
    private String numTele;
    private String email;
    private UserProfile profil;
    private UserStatus statut;
    private Date emailValidatedAt;
    private int nbrMaxTransactions;
    private int sizeMax;
}
