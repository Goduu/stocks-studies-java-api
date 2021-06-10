package com.goduu.stocksstudies.repository;

import java.util.List;
import java.util.Optional;

import com.goduu.stocksstudies.models.Grid;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GridRepository extends MongoRepository<Grid, String> {

    List<Grid> findAllByUserId(String userId);
    
    Optional<Grid> findAllByUserIdAndIdentifier(String userId, String identifier);
    

}
