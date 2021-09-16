package com.epam.esm.mapper;

public interface Mapper<T, E> {
    E mapToDto(T entity);
    T mapToEntity(E dto);
}
