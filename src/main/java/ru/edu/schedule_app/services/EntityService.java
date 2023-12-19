package ru.edu.schedule_app.services;

import java.util.List;

/**
 * @param <E> - Entity class
 * @param <D> - Dto class
 */
public interface EntityService<E, D> {

    E convertToEntity(D dto);

    D convertToDto(E entity);

    E getById(String id);

    List<E> getByIds(List<String> ids);

    List<String> getIds(List<E> entities);

}
