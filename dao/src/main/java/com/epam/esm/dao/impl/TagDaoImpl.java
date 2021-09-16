package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.rowmapper.TagRowMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String SELECT_ALL_TAGS = "SELECT t from Tag t where t.isBlocked=false";
    private static final String SELECT_ONE_TAG = "SELECT t FROM Tag t WHERE t.id=:id AND t.isBlocked=false";
    private static final String ID = "id";
    private static final String ADD_TAG = "INSERT INTO tags (name) VALUES (?)";
    private static final String DELETE_TAG = "UPDATE tags SET is_blocked=true WHERE id=?";
    private static final String SELECT_ONE_TAG_BY_NAME = "SELECT * FROM tags WHERE name=? AND is_blocked=false";
    private EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @PersistenceContext
    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Tag> findAll() {
        return new HashSet<>(entityManager.createQuery(SELECT_ALL_TAGS).getResultList());
    }

    @Override
    public Optional<Tag> findOne(Long id) {
//        return entityManager.createQuery(SELECT_ONE_TAG)
//        .setParameter(ID, id)
//        .getResultList()
//        .stream()
//        .findAny();

        List list = entityManager.createNativeQuery("SELECT * from tags").getResultList();
        List list2 = entityManager.createQuery("SELECT entity from Tag entity").getResultList();
        return entityManager.createNativeQuery("SELECT * from tags").getResultList().stream().findAny();
    }

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_TAG, id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SELECT_ONE_TAG_BY_NAME, tagRowMapper, name)
                .stream()
                .findAny();
    }
}
