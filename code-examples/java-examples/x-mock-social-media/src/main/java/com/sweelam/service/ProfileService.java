package com.sweelam.service;

import com.sweelam.model.Profile;
import org.bson.Document;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public interface ProfileService {
    List<Document> getAllProfiles();

    Flux<Profile> getAllProfilesReactively();

    Document getById(String profileId);

    void save(List<Map<String, Object>> entity);

    Flux<Profile> saveProfiles(final List<Profile> profiles);
}
