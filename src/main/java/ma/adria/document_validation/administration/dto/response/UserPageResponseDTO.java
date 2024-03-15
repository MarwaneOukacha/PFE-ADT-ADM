package ma.adria.document_validation.administration.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageResponseDTO {
    private String prenom;
    private String nom;
    private String email;
    private String statut;
    private int nbrMaxTransactions;
    private int sizeMax;
}
