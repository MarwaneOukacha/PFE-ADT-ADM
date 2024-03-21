package ma.adria.document_validation.administration.services.clientApplications.impl;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.dao.IClientAppDAO;
import ma.adria.document_validation.administration.dto.request.clientApp.EditClientAppNameRequestDTO;
import ma.adria.document_validation.administration.dto.response.keycloak.AddClientToKeycloakResponseDTO;
import ma.adria.document_validation.administration.dto.keycloak.KeycloakClientDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.ClientPageRequestDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.CreateClientRequestDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.EditClientRequestDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.*;
import ma.adria.document_validation.administration.exception.ResourceAlreadyExistsException;
import ma.adria.document_validation.administration.exception.ResourceNotFoundException;
import ma.adria.document_validation.administration.mapper.ClientMapper;
import ma.adria.document_validation.administration.model.entities.ClientApplication;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import ma.adria.document_validation.administration.model.enums.clientStatus;
import ma.adria.document_validation.administration.reposetiry.specifications.ClientSpecification;
import ma.adria.document_validation.administration.services.clientApplications.IClientService;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import ma.adria.document_validation.administration.util.SortUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ClientApplicationServiceImpl implements IClientService {
    private final ClientMapper clientMapper ;
    private static int codeapp=1;
    private final IClientAppDAO clientDAO ;
    private final IADTConstDAO iADTConstDAO;
    private final ClientSpecification clientSpecification ;
    private final KeycloakService keycloakService;
    @Override
    public CreateClientResponseDTO add(CreateClientRequestDTO user) {
        ClientApplication clientApplication=ClientApplication.builder()
                .codeApp("app"+Integer.toString(codeapp))
                .nbrMaxTransactions(Integer.parseInt(iADTConstDAO.findADTConstByCode(ADTConstCode.CLIENT_APP_TRANSACTION_LIMIT_PER_DAY).getValue()))
                .sizeMax(Integer.parseInt(iADTConstDAO.findADTConstByCode(ADTConstCode.CLIENT_APP_DOCUMENT_MAX_SIZE_MB).getValue()))
                .companyName(user.getCompanyName())
                .name(user.getName())
                .statut(clientStatus.DISABLED)
                .build();
        clientDAO.save(clientApplication);
        codeapp++;
        KeycloakClientDTO kdto=new KeycloakClientDTO();
        kdto.setClientId(user.getClientId());
        kdto.setRedirectUris(user.getRedirectUris());
        AddClientToKeycloakResponseDTO addClientToKeycloakResponseDTO= keycloakService.addClientToKeycloak(kdto);
        clientApplication=clientApplication.toBuilder().secret(addClientToKeycloakResponseDTO.getSecret())
                .keycloakClientId(addClientToKeycloakResponseDTO.getKeycloakID()).build();
        ClientApplication app= clientDAO.save(clientApplication);
        return clientMapper.toClientResponseApplication(app);
    }

    @Override
    public EditClientResponseDTO edit(EditClientRequestDTO editClientRequestDTO) {
        ClientApplication app= clientDAO.findClientById(UUID.fromString(editClientRequestDTO.getId()));
        if(app==null){
            throw new ResourceNotFoundException(ErrorCode.CLIENT_APP_NOT_FOUND);
        }
        if(StringUtils.hasText(editClientRequestDTO.getStatut())){
            app.setStatut(clientStatus.valueOf(editClientRequestDTO.getStatut()));
        }
        if(StringUtils.hasText(editClientRequestDTO.getName())){
            app.setName(editClientRequestDTO.getName());
        }
        if(StringUtils.hasText(editClientRequestDTO.getCompanyName())){
            app.setCompanyName(editClientRequestDTO.getCompanyName());
        }
        if(editClientRequestDTO.getSizeMax()!=0){
            app.setSizeMax(editClientRequestDTO.getSizeMax());
        }
        if(editClientRequestDTO.getNbrMaxTransactions()!=0){
            app.setNbrMaxTransactions(editClientRequestDTO.getNbrMaxTransactions());
        }
        ClientApplication clientApp= clientDAO.save(app);
        EditClientResponseDTO res=clientMapper.toEditClientResponseDTO(clientApp);
        if(clientApp!=null){
            EditClientAppNameRequestDTO appClient= EditClientAppNameRequestDTO.builder()
                    .redirectUris(new ArrayList<>())
                    .webOrigins(new ArrayList<>())
                    .clientId(clientApp.getName())
                    .build();
            keycloakService.updateClientAppName(appClient, String.valueOf(clientApp.getKeycloakClientId()));
        }
        return res;
    }

    @Override
    public Page<ClientPageResponseDTO> getPage(ClientPageRequestDTO clientPageRequestDTO) {
        Specification<ClientApplication> clientSpec = getClientSpecification(clientPageRequestDTO);
        List<Sort.Order> orders = SortUtils.getOrders(clientPageRequestDTO.getSort());
        Pageable pageable = PageRequest.of(clientPageRequestDTO.getPage() , clientPageRequestDTO.getSize() , Sort.by(orders));
        Page<ClientApplication> clientsPage = clientDAO.getPage(pageable, clientSpec);
        return clientsPage.map(
                clientMapper::mapClientToSearchClientResponseDTO
        );
    }

    @Override
    public ClientDetailsResponseDTO getClientById(String id) {
        ClientApplication app= clientDAO.findClientById(UUID.fromString(id));
        ClientDetailsResponseDTO res=clientMapper.toClientDetailsResponseDTO(app);
        res.setId(app.getId());
        return res;
    }


    private Specification<ClientApplication> getClientSpecification(ClientPageRequestDTO request) {

        Specification<ClientApplication> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(root.get("id"));

        if (StringUtils.hasText(request.getCompanyName())) {
            spec = spec.and(clientSpecification.companyNameLike(request.getCompanyName()));
        }

        if(StringUtils.hasText(request.getStatut().toString())){
            spec = spec.and(clientSpecification.statusEqual(request.getStatut()));
        }

        if (StringUtils.hasText(request.getName())) {
            spec = spec.and(clientSpecification.nameLike(request.getName()));
        }

        return  spec ;

    }

}
