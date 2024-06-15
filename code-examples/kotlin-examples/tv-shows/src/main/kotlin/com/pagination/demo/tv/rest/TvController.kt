package com.pagination.demo.tv.rest

import com.pagination.demo.tv.dto.Show
import com.pagination.demo.tv.dto.TvContent
import com.pagination.demo.tv.repo.TvContentRepo
import com.pagination.demo.tv.repo.TvShowRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/contents")
class TvController(
    @Autowired val tvConsumer: TvConsumer,
    @Autowired val tvContentRepo: TvContentRepo,
    @Autowired val tvShowRepo: TvShowRepo
) {
    @PostMapping("/tvs-migration")
    suspend fun migrateAllTvContents(): List<TvContent> {
        val allTvs = tvConsumer.getAllTvs()
        return tvContentRepo.saveAll(allTvs).toList()
    }

    @PostMapping("/shows-migration")
    suspend fun migrateAllTvShows(): List<Show> {
        val allShows = tvConsumer.getAllShows()
        return tvShowRepo.saveAll(allShows).toList()
    }

    @GetMapping
    suspend fun getAllTvs(): Flow<TvContent> = tvContentRepo.getAll()

    @GetMapping("/v2")
    fun getAllTvsV2(): Flux<TvContent> = tvContentRepo.findAll()


    @GetMapping("/shows")
    fun getShowById(@RequestParam id: Long): Mono<Show> = tvShowRepo.findById(id)

    @GetMapping("/shows-paginated")
    suspend fun getAllShows(
        @RequestParam pageSize: Int = 5,
        @RequestParam pageNo: Int = 0
    ) : Flow<Show> = tvShowRepo.findAllBy(PageRequest.of(pageNo, pageSize))

}

@Service
class TvConsumer(
    @Autowired val webClientBuilder: WebClient.Builder,
    @Value("\${application.config.search-shows}") val searchShowUri: String,
    @Value("\${application.config.all-shows}") val allShowsUri: String
) {
    suspend fun getAllTvs(): List<TvContent> = withContext(Dispatchers.IO) {
        val response = webClientBuilder.build()
            .get()
            .uri(searchShowUri)
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<TvContent>>() {})
            .awaitSingle()

        response ?: emptyList()
    }

    suspend fun getAllShows(): List<Show> = withContext(Dispatchers.IO) {
        val response = webClientBuilder.build()
            .get()
            .uri(allShowsUri)
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<Show>>() {})
            .awaitSingle()

        response ?: emptyList()
    }
}




