package com.goduu.stocksstudies.repository;

import java.util.List;
import java.util.Optional;

import com.goduu.stocksstudies.models.Operation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends MongoRepository<Operation, String> {

    Optional<List<Operation>> findAllByUserId(String userId);
    
    Optional<List<Operation>> findAllByUserIdAndAsset(String userId, String asset);
}
