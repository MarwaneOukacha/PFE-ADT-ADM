package ma.adria.document_validation.administration.dto.request.user;

import lombok.*;
import ma.adria.document_validation.administration.dto.PageableDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageRequestDTO extends PageableDTO {
    private String prenom;
    private String nom;
    private String email;
    private String statut;
    private String numTele;
}
