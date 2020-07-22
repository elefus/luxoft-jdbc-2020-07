package com.luxoft.trainings.jva005.day_4.helper.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<ENTITY, ID> {

    Optional<ENTITY> get(ID id);

    List<ENTITY> getAll();

    void create(ENTITY account);

    void update(ENTITY account);

    void delete(ID id);
}
