package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exeption.AppException;

import java.util.List;
import java.util.Map;

/**
 * Service class responsible for processing gift certificate-related operations
 *
 * @author Andrey Gretchenko
 */
public interface GiftCertificateService {
    /**
     * Adds gift certificate to repository according to provided dto object.
     *
     * @param giftCertificateDto GiftCertificateDto object on basis of which is created new giftCertificate in repository
     * @return GiftCertificateDto giftCertificateDto of created in gift certificate repository
     * @throws AppException if fields in provided GiftCertificateDto object is not valid or giftCertificate with the same name is already
     *                         in repository
     */
    GiftCertificateDto add(GiftCertificateDto giftCertificateDto);

    /**
     * Returns GiftCertificateDto object for gift certificate with provided id from repository.
     *
     * @param id id of gift certificate to find
     * @return GiftCertificateDto object of tag with provided id in repository
     * @throws AppException if gift certificate with provided id is not present in repository
     */
    GiftCertificateDto find(Long id);

    /**
     * Returns list of all gift certificates from repository.
     *
     * @return List of GiftCertificateDto in repository
     */
    List<GiftCertificateDto> findAll(int page, int size);

    /**
     * Removes gift certificate with provided id from repository.
     *
     * @param id id of gift certificate to remove
     */
    void delete(Long id);

    /**
     * Updates gift certificate fields according to provided dto object.
     *
     * @param id id of gift certificate to update
     * @param giftCertificateDto GiftCertificateDto object on basis of which is update giftCertificate in repository
     * @return GiftCertificateDto giftCertificateDto of updated in gift certificate repository
     * @throws AppException if fields in provided GiftCertificateDto object is not valid or giftCertificate is not present
     *                         in repository
     */
    GiftCertificateDto patch(GiftCertificateDto giftCertificateDto, Long id);

    /**
     * Checks if gift certificate that corresponds to provided GiftCertificateDto and id exists in repository.
     *
     * @param id id object of gift certificate to find
     * @param giftCertificateDto GiftCertificateDto object of gift certificate to find
     * @return true if gift certificate that corresponds to provided GiftCertificateDto object and id exists in repository,
     * otherwise returns false
     */
    boolean exist(GiftCertificateDto giftCertificateDto, Long id);

    List<GiftCertificateDto> findByTagNames(String[] names, int page, int size);
}
