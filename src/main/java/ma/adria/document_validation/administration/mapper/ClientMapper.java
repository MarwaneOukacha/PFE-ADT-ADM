package ma.adria.document_validation.administration.mapper;

import ma.adria.document_validation.administration.dto.request.clientApp.CreateClientRequestDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.ClientDetailsResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.ClientPageResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.CreateClientResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.EditClientResponseDTO;
import ma.adria.document_validation.administration.model.entities.ClientApplication;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true),
        componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "user.companyName", target = "companyName")
    @Mapping(source = "user.name", target = "name")
    ClientApplication toClientApplication(CreateClientRequestDTO user);

    CreateClientResponseDTO toClientResponseApplication(ClientApplication user);
    EditClientResponseDTO toEditClientResponseDTO(ClientApplication client);

    ClientPageResponseDTO mapClientToSearchClientResponseDTO(ClientApplication clientApplication);
    ClientDetailsResponseDTO toClientDetailsResponseDTO(ClientApplication app);
}
