package com.goduu.stocksstudies.repository;


import java.util.List;

import com.goduu.stocksstudies.models.Ticker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends MongoRepository<Ticker, String> {    
        // "{'username': {$regex: ?0 }})"
    @Query("{                                                                   "+
            " '$and':[{                                                         "+
            "     '$or': [{'ticker':{'$regex': ?0, '$options' : 'i'}},          "+
            "         {'description':{'$regex': ?0, '$options' : 'i'}}]         "+
            "    },                                                             "+
            "     {'exchange': { '$in': ?1}}                                    "+
            "     ]                                                             "+
            "},                                                                 "+ 
            "{'ticker': 1,'description':1, '_id': 0}")
    Page<Ticker> findAllByDescriptionAndTickerAndExchange(String search, List<String> exchange, Pageable pageable);
    
    @Query("{                                                                   "+
            " '$and':[{                                                         "+
            "     '$or': [{'ticker':{'$regex': ?0, '$options' : 'i'}},          "+
            "         {'description':{'$regex': ?0, '$options' : 'i'}}]         "+
            "    }                                                              "+
            "     ]                                                             "+
            "},                                                                 "+ 
            "{'ticker': 1,'description':1, '_id': 0}")
    Page<Ticker> fetchTickersBySearch(String search, Pageable pageable);
    
    @Query("{ 'ticker': { '$in': ?0 }}")
    Page<Ticker> fetchTickersInfosByList(List<String> list, Pageable pageable);

    Ticker findByTicker(String ticker);

    
		// return dumps(db.tickers
        // .find({
        //     '$and':[{
        //         '$or': [{'ticker':{'$regex': search}},
        //             {'description':{'$regex': search}}]
        //         },
        //         {'exchange': exchange}
        //         ]
        //     },
        //  {'ticker': 1,'description':1, '_id': 0})
        // .limit(el_per_pag)
        // .skip(el_per_pag*page))
}
