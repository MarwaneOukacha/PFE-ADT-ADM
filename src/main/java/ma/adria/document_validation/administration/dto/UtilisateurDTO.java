package ma.adria.document_validation.administration.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter @Setter
public class UtilisateurDTO {
	private String prenom;
	private String nom;
	private String numTele;
	private String email;
	private String profil;	
	private String statut;
	private Date emailValidatedAt;
	private int nbrMaxTransactions;
	private int sizeMax;
	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
}
