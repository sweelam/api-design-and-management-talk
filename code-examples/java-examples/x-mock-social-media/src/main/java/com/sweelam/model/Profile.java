package com.sweelam.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profiles")
public record Profile (String username,List<Post> posts) {}
