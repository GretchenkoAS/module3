package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.rowmapper.GiftCertificateRowMapper;
import com.epam.esm.dao.rowmapper.TagRowMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.now;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT c FROM GiftCertificate c";
    private static final String SELECT_ONE_TAG_BY_NAME = "SELECT t FROM Tag t WHERE t.name=:name";
    private static final String NAME = "name";


    private static final String SELECT_ONE_GIFT_CERTIFICATE = "SELECT * FROM gift_certificates WHERE id=?";
    private static final String SELECT_ONE_GIFT_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificates WHERE name=?";
    private static final String ADD_GIFT_CERTIFICATE = "INSERT INTO gift_certificates (name, description," +
            " price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO tags_gift_certificates VALUES (?, ?)";
    private static final String CLEAR_GIFT_CERTIFICATE_TAGS = "DELETE FROM tags_gift_certificates WHERE gift_certificate_id=?";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id =?";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificates WHERE id=?";
    private static final String SELECT_TAGS = "SELECT * FROM tags INNER JOIN tags_gift_certificates " +
            "ON tags.id = tags_gift_certificates.tag_id WHERE tags_gift_certificates.gift_certificate_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateRowMapper giftCertificateRowMapper;
    private final TagRowMapper tagRowMapper;
    private EntityManager entityManager;


    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.tagRowMapper = tagRowMapper;
    }

    @PersistenceContext
    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return new ArrayList<>(entityManager.createQuery(SELECT_ALL_GIFT_CERTIFICATES).getResultList());
    }

    @Override
    public Optional<GiftCertificate> findOne(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate add(GiftCertificate certificate) {
        certificate.getTags()
                .forEach(t -> t.setId(findTagIdByNameIfExist(t.getName())));
        return entityManager.merge(certificate);
    }

    @Override
    public List<GiftCertificate> findByQuery(Query query) {
        return jdbcTemplate.query(query.buildSqlQuery(), giftCertificateRowMapper, query.getQueryParams());
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        certificate.getTags()
                .forEach(t -> t.setId(findTagIdByNameIfExist(t.getName())));
        return entityManager.merge(certificate);
    }

    @Override
    public void delete(Long id) {
        GiftCertificate giftCertificate = findOne(id).get();
        entityManager.remove(giftCertificate);
    }

    @Override
    public Set<Tag> getTags(Long giftCertificateId) {
        return new HashSet<>(jdbcTemplate.query(SELECT_TAGS, tagRowMapper, giftCertificateId));
    }

    @Override
    public void detach(GiftCertificate giftCertificate) {
        entityManager.detach(giftCertificate);
    }


    private Long findTagIdByNameIfExist(String name) {
        List tags = entityManager.createQuery(SELECT_ONE_TAG_BY_NAME)
                .setParameter(NAME, name)
                .getResultList();
        return tags.isEmpty() ? null : ((Tag) tags.get(0)).getId();
    }
}
