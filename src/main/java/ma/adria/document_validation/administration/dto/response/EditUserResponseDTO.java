package ma.adria.document_validation.administration.dto.response;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserResponseDTO {
    private String prenom;

    private String nom;

    private String num_tele;

    private String email;

    private String statut;

    private int nbr_max_transactions;

    private int size_max;
}
