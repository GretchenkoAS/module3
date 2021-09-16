package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.rowmapper.GiftCertificateRowMapper;
import com.epam.esm.dao.rowmapper.TagRowMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.now;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificates";
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

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, giftCertificateRowMapper);
    }

    @Override
    public Optional<GiftCertificate> findOne(Long id) {
        return jdbcTemplate.query(SELECT_ONE_GIFT_CERTIFICATE, giftCertificateRowMapper, id).
                stream().findAny();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(SELECT_ONE_GIFT_CERTIFICATE_BY_NAME, giftCertificateRowMapper, name).
                stream().findAny();
    }

    @Override
    public GiftCertificate add(GiftCertificate obj) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(ADD_GIFT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setBigDecimal(3, obj.getPrice());
            ps.setInt(4, obj.getDuration());
            ps.setTimestamp(5, valueOf(now()));
            ps.setTimestamp(6, valueOf(now()));
            return ps;
        }, keyHolder);
        return findOne(keyHolder.getKey().longValue()).get();
    }

    @Override
    public List<GiftCertificate> findByQuery(Query query) {
        return jdbcTemplate.query(query.buildSqlQuery(), giftCertificateRowMapper, query.getQueryParams());
    }

    @Override
    public GiftCertificate update(GiftCertificate obj, Long id) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE, obj.getName(), obj.getDescription(), obj.getPrice(),
                obj.getDuration(), valueOf(now()), id);
        return findOne(id).get();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
    }

    @Override
    public Set<Tag> getTags(Long giftCertificateId) {
        return new HashSet<>(jdbcTemplate.query(SELECT_TAGS, tagRowMapper, giftCertificateId));

    }

    @Override
    public void clearTags(Long giftCertificateId) {
        jdbcTemplate.update(CLEAR_GIFT_CERTIFICATE_TAGS, giftCertificateId);
    }

    @Override
    public void addTag(Long tagId, Long giftCertificateId) {
        jdbcTemplate.update(ADD_TAG_TO_GIFT_CERTIFICATE, tagId, giftCertificateId);
    }
}
