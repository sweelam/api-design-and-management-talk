package com.sweelam.database;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DatabaseDao {
    @Value("${spring.data.mongodb.database}")
    private String DB;

    private final MongoClient mongozClient;

    private MongoCollection<Document> getCollection(String collectionName) {
        MongoDatabase db = mongozClient.getDatabase(DB);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection;
    }

    private MongoCollection<Document> getCollection(String database, String collectionName) {
        MongoDatabase db = mongozClient.getDatabase(database);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection;
    }

    public void insert(String collectionName, Document doc) {
        var collection = getCollection(collectionName);
        collection.insertOne(doc);
    }

    public void insertMany(String collectionName, List<Document> docs) {
        var collection = getCollection(collectionName);
        collection.insertMany(docs);
    }

    public Document findOne(String collectionName) {
        var collection = getCollection(collectionName);
        return collection.find().cursor().next();
    }


    public MongoCursor<Document> find(String collectionName) {
        var collection = getCollection(collectionName);
        return collection.find().iterator();
    }

    public Document findOneById(String collectionName, String profileId) {
        var collection = getCollection(collectionName);
        
        var filter = Filters.eq("_id", new ObjectId(profileId));

        return Optional.ofNullable(collection.find(filter))
                .map(FindIterable::first)
                .orElse(new Document());
    }
}
