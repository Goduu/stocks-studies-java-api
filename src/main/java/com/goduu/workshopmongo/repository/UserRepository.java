package com.goduu.workshopmongo.repository;

import com.goduu.workshopmongo.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByName(String name);

}
