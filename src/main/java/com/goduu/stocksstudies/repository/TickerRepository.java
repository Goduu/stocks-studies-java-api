package com.goduu.stocksstudies.repository;


import java.util.List;

import com.goduu.stocksstudies.models.Ticker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends MongoRepository<Ticker, String> {    

    @Query("{                                                 "+
            " '$and':[{                                       "+
            "     '$or': [{'ticker':{'$regex': ?0}},          "+
            "         {'description':{'$regex': ?0}}]         "+
            "    },                                           "+
            "     {'exchange': ?1}                            "+
            "     ]                                           "+
            "},                                               "+ 
            "{'ticker': 1,'description':1, '_id': 0}")
    List<Ticker> findAllByDescriptionAndTickerAndExchange(String search, String exchange);

    
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
