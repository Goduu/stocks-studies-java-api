package com.goduu.stocksstudies.services;

import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparkService {

	@Autowired
	JavaSparkContext sc;

	public Map<String, Long> getCount(List<String> wordList) {
		JavaRDD<String> words = sc.parallelize(wordList);
		Map<String, Long> wordCounts = words.countByValue();
		return wordCounts;
	}
	
    public Map<String, Long> aggregateSeries(Map<String, Long> quantities, Map<String, Object> series) {
        // Dataset<Row> df_start = df.groupBy("value").min("start").withColumnRenamed("min(start)", "start").withColumnRenamed("value", "value_start");
        // Dataset<Row> df_end = df.groupBy("value").max("end").withColumnRenamed("max(end)", "end").withColumnRenamed("value", "value_end");

        // Dataset<Row> df_combined = df_start.join(df_end, df_start.col("value_start").equalTo(df_end.col("value_end"))).drop("value_end").withColumnRenamed("value_start", "value").orderBy("value");


        return null;
	}

}