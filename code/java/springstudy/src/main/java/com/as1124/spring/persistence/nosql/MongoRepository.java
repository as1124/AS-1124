package com.as1124.spring.persistence.nosql;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;

@EnableMongoRepositories
@Repository
public class MongoRepository {

	public MongoDbFactory createMongoClient() {
		SimpleMongoClientDbFactory clientFactory = new SimpleMongoClientDbFactory("");
		return clientFactory;
	}

	public MongoOperations createMongoTemplate(MongoClient mongoClien) {
		return new MongoTemplate(mongoClien, "");
	}

}
