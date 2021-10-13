package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;
import java.util.Set;

/**
 * DAO interface responsible for processing CRD operations for tags
 *
 * @author Andrey Gretchenko
 */
public interface TagDao {
    /**
     * Returns list of Tags from repository.
     *
     * @return List of Tags in repository
     */
    Set<Tag> findAll(int page, int size);

    /**
     * Returns Optional of Tag with provided id from repository.
     *
     * @param id id of Tag to find
     * @return Optional<Tag> of Tag with provided id in repository
     */
    Optional<Tag> findOne(Long id);

    /**
     * Returns Optional of Tag with provided name from repository.
     *
     * @param name name of Tag to find
     * @return Optional of Tag with provided name in repository
     */
    Optional<Tag> findByName(String name);

    /**
     * Adds Tag to repository.
     *
     * @param obj of Tag to add to repository
     * @return Tag in repository
     */
    Tag add(Tag obj);

    /**
     * Removes Tag with provided id from repository.
     *
     * @param id id of Tag to remove
     */
    void delete(Long id);

    Tag findMostWidelyUsedTag();
}
