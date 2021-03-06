package com.example.crime_intent.repository;

import com.example.crime_intent.model.Crime;

import java.util.List;
import java.util.UUID;

public interface IRepository {

    List<Crime> getList();
    Crime get(UUID id);
    void insert(Crime crime);
    void insertList(List<Crime> eList);
    void delete(Crime crime);
    void update(Crime crime);
    int getPosition(UUID id);

    /*List<Crime> getCrimes();
    Crime getCrime(UUID crimeId);
    void insertCrime(Crime crime);
    void updateCrime(Crime crime);
    void deleteCrime(Crime crime);
    int getPosition(UUID crimeId);
    UUID nextPosition(UUID crimeId);
    UUID pervPosition(UUID crimeId);
    int getIndexOfCrime (Crime crime);
    Crime getCrimeWithIndex (int index);
    int repositorySize();
    public void setCrimesSelected();
    public void setCrimesUnSelected();*/

}
