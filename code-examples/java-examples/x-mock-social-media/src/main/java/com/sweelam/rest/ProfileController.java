package com.sweelam.rest;

import com.sweelam.model.Profile;
import com.sweelam.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/profiles")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileService profileService;

    @Operation(
        description = "Fetch all profiles from the system, this API should provide available database profiles",
        summary = "All System Profiles"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Profile> getProfiles() {
        log.info("Current thread {}", Thread.currentThread().getId());
        return profileService.getAllProfilesReactively()
                .doOnComplete(() -> log.info("Retreival completed"));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<?> getProfile(@PathVariable String profileId) {
        return ResponseEntity.ok(profileService.getById(profileId));
    }

    @PostMapping("/v1")
    public ResponseEntity<?> addNewProfile(@RequestBody List<Map<String, Object>> entity) {
        profileService.save(entity);
        return ResponseEntity.ok("Done");
    }

    @PostMapping("/v2")
    public Flux<Profile> saveProfile(@RequestBody List<Profile> profiles) {
        return profileService.saveProfiles(profiles);
    }

}
