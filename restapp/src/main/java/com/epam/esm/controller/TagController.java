package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping
    public ResponseEntity<List<TagDto>> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "5") int size) {
        List<TagDto> tags = tagService.findAll(page, size);
        for (TagDto tag : tags) {
            addLinks(tag);
        }
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> find(@PathVariable Long id) {
        TagDto tag = tagService.find(id);
        addLinks(tag);
        return ResponseEntity.status(HttpStatus.OK).body(tag);
    }

    @PostMapping
    public ResponseEntity<TagDto> add(@RequestBody TagDto newTag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.add(newTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/name")
    public ResponseEntity<TagDto> findByName(@RequestParam(required = false) String name) {
        TagDto tag = tagService.findByName(name);
        addLinks(tag);
        return ResponseEntity.status(HttpStatus.OK).body(tag);
    }

    @GetMapping("/most-widely-used-tag")
    public ResponseEntity<TagDto> findMostWidelyUsedTag() {
        TagDto tag = tagService.findMostWidelyUsedTag();
        addLinks(tag);
        return ResponseEntity.status(HttpStatus.OK).body(tag);
    }

    private void addLinks(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).find(tag.getId())).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).add(new TagDto())).withRel("create"));
        tag.add(linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete"));
    }
}
