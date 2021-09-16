package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagMapper implements Mapper<Tag, TagDto> {
    @Override
    public TagDto mapToDto(Tag entity) {
        TagDto tagDto = new TagDto();
        tagDto.setId(entity.getId());
        tagDto.setName(entity.getName());
        return tagDto;
    }

    @Override
    public Tag mapToEntity(TagDto dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }

    public Set<TagDto> mapToListDto(Set<Tag> entities) {
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    public Set<Tag> mapToListEntity(Set<TagDto> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }
}
