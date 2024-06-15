package com.pagination.demo.tv.repo

import com.pagination.demo.tv.dto.Show
import com.pagination.demo.tv.dto.TvContent
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface TvContentRepo : ReactiveMongoRepository<TvContent, String> {
    suspend fun saveAll(tvContentList: List<TvContent>): Flow<TvContent>

    @Query("{}")
    suspend fun getAll(): Flow<TvContent>
}


interface TvShowRepo : ReactiveMongoRepository<Show, Long> {
    suspend fun saveAll(tvShows: List<Show>): Flow<Show>

    suspend fun findAllBy(pageable: Pageable): Flow<Show>
}