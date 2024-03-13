package ma.adria.document_validation.administration.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Utilisateurdto {
	private String prenom;
	private String nom;
	private String num_tele;
	private String email;
	private String profil;	
	private String statut;
	private Date email_validated_at;
	private int nbr_max_transactions;
	private int size_max;
}
