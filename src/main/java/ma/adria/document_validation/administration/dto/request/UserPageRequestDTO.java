package ma.adria.document_validation.administration.dto.request;

import lombok.*;
import ma.adria.document_validation.administration.dto.PageableDTO;

import javax.persistence.Column;

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
}
