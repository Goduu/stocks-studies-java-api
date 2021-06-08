package com.nelioalves.workshopmongo.repository;

import java.util.List;

import com.nelioalves.workshopmongo.models.Grid;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GridRepository extends MongoRepository<Grid, String> {

    List<Grid> findAllByUser(String userId);

}
