package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {
    private static final String SELECT_ALL_TAGS = "SELECT t from Tag t where t.isBlocked=false";
    private static final String SELECT_ONE_TAG = "SELECT t FROM Tag t WHERE t.id=:id AND t.isBlocked=false";
    private static final String SELECT_ONE_TAG_BY_NAME = "SELECT t FROM Tag t WHERE t.name=:name AND t.isBlocked=false";
    private static final String ID = "id";
    private static final String NAME = "name";
    private EntityManager entityManager;

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
        return entityManager.createQuery(SELECT_ONE_TAG)
        .setParameter(ID, id)
        .getResultList()
        .stream()
        .findAny();
    }

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        tag.setBlocked(true);
        entityManager.merge(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery(SELECT_ONE_TAG_BY_NAME)
                .setParameter(NAME, name)
                .getResultList()
                .stream()
                .findAny();
    }
}
