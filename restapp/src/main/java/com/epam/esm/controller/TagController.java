package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Provide a centralized request handling mechanism to
 * handle all types of requests for tags.
 *
 * @author Andrey Gretchenko
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Returns all TagDto objects of tags from repository.
     *
     * @return list of TagDto objects of retrieved tags
     */
    @GetMapping
    public List<TagDto> findAll() {
        return tagService.findAll();
    }

    /**
     * Returns TagDto object for tag with provided id from repository.
     *
     * @param id id of tag to find
     * @return TagDto object of tag with provided id in repository
     * @throws AppException if tag with provided id is not present in repository
     */
    @GetMapping("/{id}")
    public TagDto find(@PathVariable Long id) {
        return tagService.find(id);
    }

    /**
     * Adds tag to repository according to request body.
     *
     * @param newTag TagDto object on basis of which is created new tag in repository
     * @return TagDto tag dto of created in repository tag
     * @throws AppException if fields in provided TagDto object is not valid or tag with the same name is alredy
     *                          in repository
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto add(@RequestBody TagDto newTag) {
        return tagService.add(newTag);
    }

    /**
     * Removes tag with provided id from repository.
     *
     * @param id id of tag to delete from repository
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
