package com.goduu.stocksstudies.repository;

import java.util.Optional;

import com.goduu.stocksstudies.models.Watchlist;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, String> {

    Optional<Watchlist> findAllByUserId(String userId);
    
    

}
