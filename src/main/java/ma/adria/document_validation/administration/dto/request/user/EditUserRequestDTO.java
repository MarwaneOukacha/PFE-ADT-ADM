package ma.adria.document_validation.administration.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequestDTO {
    private String id;

    private String prenom;

    private String nom;

    private String numTele;

    private String email;
    private String statut;

    private String password ;

    private int nbrMaxTransactions;

    private int sizeMax;
}
