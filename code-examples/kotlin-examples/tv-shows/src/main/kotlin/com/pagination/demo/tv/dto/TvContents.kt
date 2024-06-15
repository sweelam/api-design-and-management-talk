package com.pagination.demo.tv.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "tv_contents")
data class TvContent(
    val score: Double,
    val show: Show,
)

@Document(collection = "tv_shows")
data class Show(
    val id: Long,
    val url: String,
    val name: String,
    val type: String,
    val language: String,
    val genres: List<String>,
    val status: String,
    val runtime: Long,
    val averageRuntime: Long,
    val premiered: String,
    val ended: String?,
    val officialSite: String?,
    val schedule: Schedule,
    val rating: Rating,
    val weight: Long,
    val network: Network?,
    val webChannel: Any?,
    val dvdCountry: Any?,
    val externals: Externals,
    val image: Image?,
    val summary: String?,
    val updated: Long,
    @JsonProperty("_links")
    val links: Links,
)

data class Schedule(
    val time: String,
    val days: List<String>,
)

data class Rating(
    val average: Any?,
)

data class Network(
    val id: Long,
    val name: String,
    val country: Country?,
    val officialSite: Any?,
)

data class Country(
    val name: String,
    val code: String,
    val timezone: String,
)

data class Externals(
    val tvrage: Any?,
    val thetvdb: Long?,
    val imdb: String?,
)

data class Image(
    val medium: String?,
    val original: String?,
)

data class Links(
    val self: Self,
    val previousepisode: Previousepisode?,
)

data class Self(
    val href: String,
)

data class Previousepisode(
    val href: String,
)

