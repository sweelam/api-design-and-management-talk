package com.flight.search.runner;

import com.flight.search.repo.FlightSearchRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.flight.search.utils.TimeUtils.getDurationInHours;

@Component
@Slf4j
public class GraphBuilder implements ApplicationRunner {
    private static final Map<String, List<FlightNode>> flightsGraph = new HashMap<>();
    private final FlightSearchRepo flightSearchRepo;
    private final ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor();
    private final String activeProfile;

    public GraphBuilder(FlightSearchRepo flightSearchRepo,
                        @Value("${active.profile:}") final String activeProfile) {
        this.flightSearchRepo = flightSearchRepo;
        this.activeProfile = activeProfile;
    }

    @Override
    public void run(ApplicationArguments args) {
        if ("test".equals(activeProfile)) {
            this.buildGraph();
            return;
        }

        CompletableFuture.runAsync(this::buildGraph, ex);
    }

    private void buildGraph() {
        flightSearchRepo.findAll()
                .forEach(flight -> {
                    String src = flight.getDepartureAirport();
                    String dest = flight.getArrivalAirport();

                    float duration =
                            getDurationInHours(flight.getDepartureTime(), flight.getArrivalTime(), ZoneOffset.UTC);

                    flightsGraph.computeIfAbsent(src, k -> new ArrayList<>()).add(new FlightNode(dest, duration));
                });
    }

    public List<String> findPath(String from, String to) {
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
                    parentMap.put(neighbour, current);
                }
            }
        }

        return Collections.emptyList();
    }

    private List<String> buildThePath(Map<FlightNode, FlightNode> parentMap, FlightNode endNode) {
        List<String> path = new ArrayList<>();
        FlightNode step = endNode;

        while (step != null) {
            path.add(step.airPort());
            step = parentMap.get(step);
        }

        Collections.reverse(path);
        return path;
    }
}
