package ma.adria.document_validation.administration.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.document_validation.administration.dto.request.ADTConst.CreateADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.request.ADTConst.EditADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.response.ADTConst.ADTConstResponseDTO;
import ma.adria.document_validation.administration.services.adtconstants.IADTConstService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "ADT-const controller")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/ADTConst")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class AdtConstController {

    private final IADTConstService adtConstService;

    @PostMapping(value = "/add")
    public ResponseEntity<ADTConstResponseDTO> add(@RequestBody @Valid CreateADTConstRequestDTO createADTConstRequestDTO) {
        return ResponseEntity.ok()
                .body(adtConstService.createADTConst(createADTConstRequestDTO));
    }


    @PatchMapping(value = "/edit")
    public ResponseEntity<ADTConstResponseDTO> update(@RequestBody @Valid EditADTConstRequestDTO editADTConstRequestDTO) {
        return ResponseEntity.ok()
                .body(adtConstService.updateADTConst(editADTConstRequestDTO));
    }
    @GetMapping(value = "/details")
    public ResponseEntity<ADTConstResponseDTO> getADTConstById(@RequestParam String id) {
        return ResponseEntity.ok()
                .body(adtConstService.getADTConstById(id));
    }



    @GetMapping(value = "/all")
    public ResponseEntity<List<ADTConstResponseDTO>> all() {
        return ResponseEntity.ok()
                .body(adtConstService.getAllADTConst());
    }

}