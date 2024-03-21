package ma.adria.document_validation.administration.model.entities;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;

@Entity(name = "utilisateurs")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Utilisateur extends AbstractEntity{
	@Column(nullable = false)
	private String prenom;
	@Column(nullable = false)
	private String nom;
	@Column(nullable = false)
	private String numTele;
	@Column(unique = true,nullable = false)
	private String email;
	@Enumerated(EnumType.STRING)
	private UserProfile profil;
	@Column(nullable = false)
	private String password;
	@Column(nullable = true,unique = true)
	private String keycloakId;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus statut;
	@Column(nullable = true)
	private Date emailValidatedAt;
	@Column(nullable = false)
	private int nbrMaxTransactions;
	@Column(nullable = false)
	private int sizeMax;
}
