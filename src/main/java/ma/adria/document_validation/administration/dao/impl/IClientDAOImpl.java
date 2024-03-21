package ma.adria.document_validation.administration.dao.impl;


import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IClientAppDAO;
import ma.adria.document_validation.administration.model.entities.ClientApplication;
import ma.adria.document_validation.administration.model.enums.clientStatus;
import ma.adria.document_validation.administration.reposetiry.ClientApplicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class IClientDAOImpl implements IClientAppDAO {
    private final ClientApplicationRepository clientRepository;
    @Override
    public List<ClientApplication> findAll() {

        return clientRepository.findAll();
    }

    @Override
    public ClientApplication save(ClientApplication client) {
        return clientRepository.save(client);
    }

    @Override
    public ClientApplication findClientById(UUID id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public boolean existsByCompanyName(String companyName) {
        return clientRepository.existsByCompanyName(companyName);
    }

    @Override
    public boolean existsById(UUID id) {
        return clientRepository.existsById(id);
    }

    @Override
    public Page<ClientApplication> getPage(Pageable pageable, Specification<ClientApplication> clientSpecification) {
        return clientRepository.findAll(clientSpecification,pageable);
    }

    @Override
    public long count() {
        return clientRepository.count();
    }

}
