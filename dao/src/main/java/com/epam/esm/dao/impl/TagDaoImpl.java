package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String IS_BLOCKED = "isBlocked";
    private EntityManager entityManager;

    @PersistenceContext
    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Tag> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.select(tagRoot);
        TypedQuery<Tag> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return new HashSet<>(typedQuery.getResultList());
    }

    @Override
    public Optional<Tag> findOne(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(tagRoot.get(ID), id));
        predicates.add(criteriaBuilder.equal(tagRoot.get(IS_BLOCKED), false));
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(criteriaQuery)
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(tagRoot.get(NAME), name));
        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findAny();
    }

    @Override
    public Tag findMostWidelyUsedTag() {
        String sql = "SELECT tags.* from orders inner join users u on u.id = orders.user_id " +
                "inner join tags_gift_certificates on (orders.gift_certificate_id=tags_gift_certificates.gift_certificate_id) " +
                "inner join tags on (tags.id=tags_gift_certificates.tag_id) " +
                "WHERE orders.user_id = (SELECT orders.user_id FROM orders GROUP BY orders.user_id " +
                "ORDER BY SUM(orders.cost) DESC LIMIT 1) group by tags.name order by COUNT(tags.name) desc limit 1";
        Query query = entityManager.createNativeQuery(sql, Tag.class);
        return (Tag) query.getSingleResult();
    }
}
