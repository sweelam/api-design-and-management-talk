package com.sweelam.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sweelam.model.Profile;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
    
}
