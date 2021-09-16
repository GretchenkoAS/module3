package com.epam.esm.mapper.impl;

import com.epam.esm.dto.QueryDto;
import com.epam.esm.entity.Query;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class QueryMapper implements Mapper<Query, QueryDto> {
    @Override
    public QueryDto mapToDto(Query entity) {
        QueryDto queryDto = new QueryDto();
        queryDto.setTagName(entity.getTagName());
        queryDto.setContains(entity.getContains());
        queryDto.setSortByName(entity.getSortByName());
        queryDto.setSortByDate(entity.getSortByDate());
        return queryDto;
    }

    @Override
    public Query mapToEntity(QueryDto dto) {
        Query query = new Query();
        query.setTagName(dto.getTagName());
        query.setContains(dto.getContains());
        query.setSortByName(dto.getSortByName());
        query.setSortByDate(dto.getSortByDate());
        return query;
    }
}
