package com.flight.search.runner;

import com.flight.search.repo.FlightSearchRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class GraphBuilder implements ApplicationRunner {
    private static final Map<String, List<FlightNode>> flightsGraph = new HashMap<>();
    private final FlightSearchRepo flightSearchRepo;
    private final ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor();

    public GraphBuilder(FlightSearchRepo flightSearchRepo) {
        this.flightSearchRepo = flightSearchRepo;
    }

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(this::buildGraph, ex);
    }

    private void buildGraph() {
        flightSearchRepo.findAll()
                .forEach(flight -> {
                    String src = flight.getDepartureAirport();
                    String dest = flight.getArrivalAirport();

                    Instant departureTime = flight.getDepartureTime();
                    Instant arrivalTime = flight.getArrivalTime();

                    float duration =
                            LocalDateTime.ofInstant(arrivalTime, ZoneOffset.UTC).getHour() -
                                    LocalDateTime.ofInstant(departureTime, ZoneOffset.UTC).getHour() ;

                    flightsGraph.computeIfAbsent(src, k -> new ArrayList<>()).add(new FlightNode(dest, duration));
                });
    }

    public List<FlightNode> findPath(String from, String to) {
        Deque<FlightNode> Q = new ArrayDeque<>();
        Map<FlightNode, FlightNode> parentMap = new HashMap<>();

        var startNode = new FlightNode(from, null);
        Q.offer(startNode);
        parentMap.put(startNode, null);  // Starting node has no parent


        Set<FlightNode> visited = new HashSet<>();
        visited.add(startNode);

        while (!Q.isEmpty()) {
            var current = Q.poll();

            if (current.airPort().equals(to)) {
                return buildThePath(parentMap, current);
            }

            for (var neighbour: flightsGraph.get(current.airPort())) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    Q.offer(neighbour);
                }
            }
        }

        return Collections.emptyList();
    }

    private List<FlightNode> buildThePath(Map<FlightNode, FlightNode> parentMap, FlightNode endNode) {
        List<FlightNode> path = new ArrayList<>();
        FlightNode step = endNode;

        while (step != null) {
            path.add(step);
            step = parentMap.get(step);  // Go to the parent of the current node
        }

        Collections.reverse(path);  // The path is constructed from end to start, so reverse it
        return path;
    }
}
