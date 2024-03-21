package ma.adria.document_validation.administration.model.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.adria.document_validation.administration.model.enums.clientStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;
@Entity(name = "clientApplications")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClientApplication extends AbstractEntity{
    @Column(nullable = false)
    private String codeApp;
    @Column(nullable = true)
    private String secret;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private clientStatus statut;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int nbrMaxTransactions;
    @Column(nullable = false)
    private int sizeMax;
    @Column(nullable = true)
    private  String companyName;
    private String keycloakClientId;
}
