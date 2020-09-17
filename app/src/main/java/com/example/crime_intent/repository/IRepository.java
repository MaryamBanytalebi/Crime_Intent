package com.example.crime_intent.repository;

import com.example.crime_intent.model.Crime;

import java.util.List;
import java.util.UUID;

public interface IRepository<Crime> {
    List<Crime> getList();
    Crime get(UUID id);
    void insert(Crime e);

    void insert(com.example.crime_intent.model.Crime crime);

    void insertList(List<Crime> eList);
    void delete(UUID id);
    void update(Crime e);

    void update(com.example.crime_intent.model.Crime crime);

    int getPosition(UUID id);

}
