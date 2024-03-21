package ma.adria.document_validation.administration.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserProfileResponseDTO {
    private String prenom;

    private String nom;

    private String numTele;

    private String email;
}
