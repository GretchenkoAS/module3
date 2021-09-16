package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * DAO interface responsible for processing CRUD operations for gift certificates
 *
 * @author Andrey Gretchenko
 */
public interface GiftCertificateDao {
    /**
     * Returns list of GiftCertificate from repository.
     *
     * @return List of GiftCertificate from repository
     */
    List<GiftCertificate> findAll();

    /**
     * Returns Optional of GiftCertificate with provided id from repository.
     *
     * @param id id of GiftCertificate to find
     * @return Optional<GiftCertificate> of GiftCertificate with provided id in repository
     */
    Optional<GiftCertificate> findOne(Long id);

    /**
     * Returns Optional of GiftCertificate with provided name from repository.
     *
     * @param name name of GiftCertificate to find
     * @return Optional<GiftCertificate> of GiftCertificate with provided name in repository
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Adds GiftCertificate to repository.
     *
     * @param obj GiftCertificate to add to repository
     * @return GiftCertificate in repository
     */
    GiftCertificate add(GiftCertificate obj);

    /**
     * Updates GiftCertificate with provided id in repository.
     *
     * @param obj of GiftCertificate to update
     * @param id  of GiftCertificate to update
     * @return GiftCertificate of updated entity in repository
     */
    GiftCertificate update(GiftCertificate obj, Long id);

    /**
     * Removes GiftCertificate with provided id from repository.
     *
     * @param id id of GiftCertificate to remove
     */
    void delete(Long id);

    /**
     * Returns List of tags for gift certificate.
     *
     * @param giftCertificateId id of gift certificate
     * @return List of tags
     */
    Set<Tag> getTags(Long giftCertificateId);

    /**
     * Adds a tag to the certificate.
     *
     * @param tagId             id of tag
     * @param giftCertificateId id of gift certificate
     */
    void addTag(Long tagId, Long giftCertificateId);

    /**
     * Removes tags from the certificate.
     *
     * @param id id of gift certificate
     */
    void clearTags(Long id);

    /**
     * Retrieves gift certificates from repository according to provided query.
     *
     * @param query object for building search query
     * @return List<GiftCertificate> list of gift certificates from repository according to provided query
     */
    List<GiftCertificate> findByQuery(Query query);
}
