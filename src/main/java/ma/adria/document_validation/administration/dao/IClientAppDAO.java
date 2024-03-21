package ma.adria.document_validation.administration.dao;

import ma.adria.document_validation.administration.model.entities.ClientApplication;
import ma.adria.document_validation.administration.model.enums.clientStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface IClientAppDAO {
    List<ClientApplication> findAll();

    ClientApplication save(ClientApplication client);

    ClientApplication findClientById(UUID id);

    boolean existsByCompanyName(String companyName);

    boolean existsById(UUID id);

    Page<ClientApplication> getPage(Pageable pageable, Specification<ClientApplication> clientSpecification);

    long count();


}
