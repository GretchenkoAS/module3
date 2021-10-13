package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String NAME = "name";
    private static final String TAGS = "tags";
    private EntityManager entityManager;

    @PersistenceContext
    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(giftCertificateRoot);
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
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
    public void detach(GiftCertificate giftCertificate) {
        entityManager.detach(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findByTags(List<Tag> tags, int page, int size) {
        List<GiftCertificate> resultList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> res = giftCertificateRoot.join(TAGS, JoinType.LEFT);
        for (Tag tag : tags) {
            criteriaQuery
                    .select(giftCertificateRoot)
                    .where(criteriaBuilder.equal(res.get(NAME), tag.getName()));
            if (resultList.isEmpty()) {
                resultList.addAll(entityManager.createQuery(criteriaQuery).getResultList());
            }
            resultList.retainAll(entityManager.createQuery(criteriaQuery).getResultList());
        }
        int from = (page - 1) * size;
        int to = page * size;
        if (resultList.size() < to) {
            to = resultList.size() ;
            from = to - size;
        }
        if (resultList.size() < size) {
            from = 0;
        }
        return resultList.subList(from, to);
    }

    private Long findTagIdByNameIfExist(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(tagRoot.get(NAME), name));
        List<Tag> tags = entityManager.createQuery(criteriaQuery).getResultList();
        return tags.isEmpty() ? null : tags.get(0).getId();
    }
}
