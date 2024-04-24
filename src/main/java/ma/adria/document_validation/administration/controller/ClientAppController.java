package ma.adria.document_validation.administration.controller;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dto.request.clientApp.ClientPageRequestDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.CreateClientRequestDTO;
import ma.adria.document_validation.administration.dto.request.clientApp.EditClientRequestDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.ClientDetailsResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.ClientPageResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.CreateClientResponseDTO;
import ma.adria.document_validation.administration.dto.response.clientApp.EditClientResponseDTO;
import ma.adria.document_validation.administration.services.clientApplications.IClientService;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientAppController {
    private final IClientService clientService;
    @PostMapping
    public CreateClientResponseDTO createUser(@RequestBody CreateClientRequestDTO dto){
        return clientService.add(dto);
    }
    @PutMapping("")
    public EditClientResponseDTO edit(@RequestBody EditClientRequestDTO dto){
        return clientService.edit(dto);
    }
    @GetMapping("/page")
    public Page<ClientPageResponseDTO> getPage(@RequestBody ClientPageRequestDTO clientPageRequestDTO){
        return clientService.getPage(clientPageRequestDTO);
    }
    @GetMapping("/details/{id}")
    public ClientDetailsResponseDTO getClientDetails(@PathVariable String id){
        return clientService.getClientById(id);
    }
}
