package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.QueryDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Provide a centralized request handling mechanism to
 * handle all types of requests for gift certificate.
 *
 * @author Andrey Gretchenko
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }
//    public GiftCertificateController () {
//        this.giftCertificateService = new GiftCertificateServiceImpl();
//    }

    /**
     * Returns all GiftCertificateDto objects of tags from repository.
     *
     * @return list of GiftCertificateDto objects of retrieved gift certificates
     */
    @GetMapping
    public List<GiftCertificateDto> findAll() {
        return giftCertificateService.findAll();
    }

    /**
     * Returns GiftCertificateDto object for gift certificate with provided id from repository.
     *
     * @param id id of gift certificate to find
     * @return GiftCertificateDto object of gift certificate with provided id in repository
     * @throws AppException if gift certificate with provided id is not present in repository
     */
    @GetMapping("/{id}")
    public GiftCertificateDto find(@PathVariable Long id) {
        return giftCertificateService.find(id);
    }

    /**
     * Adds gift certificate to repository according to provided dto object.
     *
     * @param newGiftCertificate GiftCertificateDto object on basis of which is created new gift certificate
     *                           in repository
     * @return GiftCertificateDto gift certificate dto of created in repository gift certificate
     * @throws AppException if fields in provided GiftCertificateDTO object is not valid
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto add(@RequestBody GiftCertificateDto newGiftCertificate) {
        return giftCertificateService.add(newGiftCertificate);
    }

    /**
     * Removes gift certificate with provided id from repository.
     *
     * @param id id of gift certificate to delete from repository
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    /**
     * Updates gift certificate according to request body.
     *
     * @param updatedCertificateDto GiftCertificateDto object according to which is necessary to update gift certificate
     *                              in repository
     * @param id                    id of updated gift certificate
     * @return GiftCertificateDto gift certificate dto of updated gift certificate in repository
     * @throws AppException if fields in provided GiftCertificateDto is not valid or gift certificate with provided
     *                      id is not present in repository
     */
    @PutMapping("/{id}")
    public GiftCertificateDto update(@RequestBody GiftCertificateDto updatedCertificateDto, @PathVariable Long id) {
        return giftCertificateService.update(updatedCertificateDto, id);
    }

    /**
     * Retrieves gift certificates from repository according to provided request parameters.
     *
     * @param tagName    (optional) request parameter for search by tag
     * @param contains   (optional) request parameter for search by phrase contained in name or description of gift
     *                   certificate
     * @param sortByName (optional) request parameter for sorting by name, ascending or descending
     * @param sortByDate (optional) request parameter for sorting by date, ascending or descending
     * @return List<GiftCertificate> list of gift certificates from repository according to provided query
     * @throws AppException if QueryDto fields is not valid
     */
    @GetMapping("/query")
    public List<GiftCertificateDto> findByQuery(@RequestParam(required = false) String tagName,
                                                @RequestParam(required = false) String contains,
                                                @RequestParam(required = false) String sortByName,
                                                @RequestParam(required = false) String sortByDate) {
        return giftCertificateService.findByQuery(new QueryDto(tagName, contains, sortByName, sortByDate));
    }

    /**
     * Updates gift certificate fields according to provided in request body fields.
     *
     * @param updatedCertificateDto GiftCertificateDto object according to which is necessary to update gift certificate
     *                              in repository
     * @param id                    id of updated gift certificate
     * @return GiftCertificateDto gift certificate dto of updated gift certificate in repository
     * @throws AppException if fields in provided GiftCertificateDto is not valid or gift certificate with provided
     *                      id is not present in repository
     */
    @PatchMapping("/{id}")
    public GiftCertificateDto patch(@RequestBody GiftCertificateDto updatedCertificateDto, @PathVariable Long id) {
        return giftCertificateService.patch(updatedCertificateDto, id);
    }
}
