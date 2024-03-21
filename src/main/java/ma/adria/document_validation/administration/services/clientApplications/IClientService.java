package ma.adria.document_validation.administration.services.clientApplications;

import ma.adria.document_validation.administration.dto.response.clientApp.ClientDetailsResponseDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.ClientPageRequestDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.CreateClientRequestDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.EditClientRequestDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.ClientNameResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.ClientPageResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.CreateClientResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.EditClientResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClientService {
    CreateClientResponseDTO add(CreateClientRequestDTO user);

    EditClientResponseDTO edit(EditClientRequestDTO editClientRequestDTO);

    Page<ClientPageResponseDTO> getPage(ClientPageRequestDTO clientPageRequestDTO);

    ClientDetailsResponseDTO getClientById(String id);

}
