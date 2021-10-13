package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                                            @RequestParam(required = false, defaultValue = "5") int size) {
        List<GiftCertificateDto> certificates = giftCertificateService.findAll(page, size);
        for (GiftCertificateDto certificate : certificates) {
            addLinks(certificate);
        }
        return ResponseEntity.status(HttpStatus.OK).body(certificates);
    }


    @GetMapping("/query")
    public ResponseEntity<List<GiftCertificateDto>> findByTagNames(@RequestParam String[] names,
                                                                   @RequestParam(required = false, defaultValue = "1") int page,
                                                                   @RequestParam(required = false, defaultValue = "5") int size) {
        List<GiftCertificateDto> certificates = giftCertificateService.findByTagNames(names, page, size);
        for (GiftCertificateDto certificate : certificates) {
            addLinks(certificate);
        }
        return ResponseEntity.status(HttpStatus.OK).body(certificates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.find(id);
        addLinks(certificate);
        return ResponseEntity.status(HttpStatus.OK).body(certificate);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> add(@RequestBody GiftCertificateDto newGiftCertificate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateService.add(newGiftCertificate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> patch(@RequestBody GiftCertificateDto updatedCertificateDto, @PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.patch(updatedCertificateDto, id);
        addLinks(certificate);
        return ResponseEntity.status(HttpStatus.OK).body(certificate);
    }

    private void addLinks(GiftCertificateDto giftCertificateDto){
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).findById(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).add(new GiftCertificateDto())).withRel("create"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).patch(new GiftCertificateDto(), giftCertificateDto.getId())).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).delete(giftCertificateDto.getId())).withRel("delete"));
    }
}
