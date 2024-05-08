package ma.adria.document_validation.administration.reposetiry.specifications;

import ma.adria.document_validation.administration.model.entities.ClientApplication;
import ma.adria.document_validation.administration.model.enums.clientStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ClientSpecification {
    public Specification<ClientApplication> companyNameEqual(String companyName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("companyName"),  companyName );
    }

    public Specification<ClientApplication> statusEqual(clientStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("statut"), status);
    }
    public Specification<ClientApplication> codeAppEqual(String codeApp) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("codeApp"), codeApp);
    }

    public Specification<ClientApplication> nameEqual(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"),  name );
    }

    public Specification<ClientApplication> nbrMaxTransactionsGreaterThan(int nbrMaxTransactions) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("nbrMaxTransactions"), nbrMaxTransactions);
    }

    public Specification<ClientApplication> sizeMaxGreaterThan(int sizeMax) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("sizeMax"), sizeMax);
    }
}
