package ma.adria.document_validation.administration.model.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity(name = "utilisateurs")
@Data @NoArgsConstructor @AllArgsConstructor
public class Utilisateur {
	@Id @GeneratedValue
	private long ID;
	@Column(nullable = false)
	private String prenom;
	@Column(nullable = false)
	private String nom;
	@Column(nullable = false)
	private String num_tele;
	@Column(unique = true,nullable = false)
	private String email;
	private String profil;	
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String keycloak_id;
	@Column(nullable = true)
	private String statut;
	@Column(nullable = true)
	private Date email_validated_at;
	@Column(nullable = false)
	private int nbr_max_transactions;
	@Column(nullable = false)
	private int size_max;
}
