package ma.adria.document_validation.administration.reposetiry;

import ma.adria.document_validation.administration.model.entities.ClientApplication;
import ma.adria.document_validation.administration.model.enums.clientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ClientApplicationRepository extends JpaRepository<ClientApplication, UUID>, JpaSpecificationExecutor<ClientApplication> {

    long countByIdNotNull();

    ClientApplication findByCompanyName(String companyName);

    boolean existsByCompanyName(String companyName);

    long count();

}
