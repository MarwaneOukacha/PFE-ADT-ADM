package ma.adria.document_validation.administration.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserProfileRequestDTO {
    private String id;

    private String prenom;

    private String nom;

    private String numTele;

    private String email;

}
