package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for processing tag-related operations
 *
 * @author Andrey Gretchenko
 */
@Service
public interface TagService {
    /**
     * Adds tag to repository according to provided dto object.
     *
     * @param tagDto TagDto object on basis of which is created new tag in repository
     * @return TagDto tag dto of created in repository tag
     * @throws AppException if fields in provided TagDto object is not valid or tag with the same name is already
     *                         in repository
     */
    TagDto add(TagDto tagDto);

    /**
     * Returns TagDto object for tag with provided id from repository.
     *
     * @param id id of tag to find
     * @return TagDto object of tag with provided id in repository
     * @throws AppException if tag with provided id is not present in repository
     */
    TagDto find(Long id);

    /**
     * Returns list of all tags from repository.
     *
     * @return List of TagDto in repository
     */
    List<TagDto> findAll();

    /**
     * Returns TagDto object for tag with provided name from repository.
     *
     * @param name name of tag to find
     * @return TagDto object of tag with provided name in repository
     * @throws AppException if tag with provided name is not present in repository
     */
    TagDto findByName(String name);

    /**
     * Removes tag with provided id from repository.
     *
     * @param id id of entity to remove
     */
    void delete(Long id);

    /**
     * Checks if tag that corresponds to provided TagDto exists in repository.
     *
     * @param tagDto TagDto object of tag to find
     * @return true if tag that corresponds to provided TagDto object exists in repository,
     * otherwise returns false
     */
    boolean exist(TagDto tagDto);
}
