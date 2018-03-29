package com.gdc.aerodev.dao;

import javax.sql.DataSource;
import java.util.List;

/**
 * This is basic generic data access object interface. All addons can be added in child.
 *
 * @param <T> is the type of entity object, for example: {@code User}, {@code Cr}, {@code Project} and so on.
 * @param <V> is the type of ID {@code Long}, {@code Integer} or {@code Short}.
 *
 * @author Yusupov Danil
 */
public interface GenericDao<T, V> {

    T getById(V id);

    T getByName(String name);

    List<T> getAll();

    Long save(T entity);

    boolean delete(Long id);

}
