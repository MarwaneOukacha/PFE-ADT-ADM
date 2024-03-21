package ma.adria.document_validation.administration.dto.response.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageResponseDTO {
    private UUID id;
    private String prenom;
    private String nom;
    private String email;
    private String statut;
    private int nbrMaxTransactions;
    private int sizeMax;
}
