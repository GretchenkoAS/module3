package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private static final String ID = "id";
    private static final String NAME = "name";
    private final TagDao tagDao;
    private final TagMapper mapper;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper mapper, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.mapper = mapper;
        this.tagValidator = tagValidator;
    }

    @Override
    public TagDto find(Long id) {
        Optional<Tag> tagOptional = tagDao.findOne(id);
        if (tagOptional.isEmpty()) {
            throw new AppException(ErrorCode.TAG_NOT_FOUND, ID, id);
        }
        return mapper.mapToDto(tagOptional.get());
    }

    @Override
    public List<TagDto> findAll(int page, int size) {
        Set<Tag> tags = tagDao.findAll(page, size);
        return tags.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TagDto add(TagDto tagDto) {
        tagValidator.validate(tagDto);
        Tag tag = mapper.mapToEntity(tagDto);
        if (tagDao.findByName(tag.getName()).isPresent()) {
            throw new AppException(ErrorCode.TAG_ALREADY_EXIST, NAME, tagDto.getName());
        }
        Tag tagInDb = tagDao.add(tag);
        return mapper.mapToDto(tagInDb);
    }

    @Override
    public void delete(Long id) {
        if (tagDao.findOne(id).isEmpty()) {
            throw new AppException(ErrorCode.TAG_NO_CONTENT, ID, id);
        }
        tagDao.delete(id);
    }

    @Override
    public TagDto findByName(String name) {
        Optional<Tag> tagOptional = tagDao.findByName(name);
        if (tagOptional.isEmpty()) {
            throw new AppException(ErrorCode.TAG_NOT_FOUND, NAME, name);
        }
        return tagOptional.map(mapper::mapToDto).get();
    }

    @Override
    public TagDto findMostWidelyUsedTag() {
        return mapper.mapToDto(tagDao.findMostWidelyUsedTag());
    }
}
