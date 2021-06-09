package com.goduu.stocksstudies.repository;

import com.goduu.stocksstudies.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByName(String name);
    
    User findUserByEmail(String email);

    
}
