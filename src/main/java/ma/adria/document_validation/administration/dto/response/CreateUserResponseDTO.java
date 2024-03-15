package ma.adria.document_validation.administration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;

import javax.persistence.Enumerated;
import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateUserResponseDTO {
    private UUID ID;
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
