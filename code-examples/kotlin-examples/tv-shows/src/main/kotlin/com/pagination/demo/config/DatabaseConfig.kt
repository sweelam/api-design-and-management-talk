package com.pagination.demo.config

import com.mongodb.WriteConcern
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfig (
    @Value("\${spring.data.mongodb.database}") private val databaseName: String
) {

    @Bean
    fun mongo(): MongoDatabase {
        return MongoClients.create()
            .getDatabase(databaseName)
            .withWriteConcern(WriteConcern.MAJORITY);
    }

}