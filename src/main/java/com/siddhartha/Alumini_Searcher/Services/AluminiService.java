package com.siddhartha.Alumini_Searcher.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddhartha.Alumini_Searcher.Entity.Alumini;
import com.siddhartha.Alumini_Searcher.Repository.AluminiRepository;
import com.siddhartha.Alumini_Searcher.dto.RequestDto;
import com.siddhartha.Alumini_Searcher.dto.Response;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AluminiService {

    private final AluminiRepository aluminiRepository;
    private final PhantomBusterService phantomBusterService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AluminiService(AluminiRepository aluminiRepository,
                          PhantomBusterService phantomBusterService) {
        this.aluminiRepository = aluminiRepository;
        this.phantomBusterService = phantomBusterService;
    }

    public List<Response> searchAlumini(RequestDto requestDto) {

        try {
            System.out.println("\n===== FETCHING FROM PHANTOM API =====");

            // 1️⃣ get REAL data from Phantom
            String jsonResponse = phantomBusterService.fetchAlumniFromPhantom();

            System.out.println("RAW JSON RESPONSE:");
            System.out.println(jsonResponse);

            // 2️⃣ convert string → JSON
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode results = root.path("container").path("results");

            List<Response> responses = new ArrayList<>();

            for (JsonNode row : results) {

                String name = row.path("fullName").asText("Unknown");
                String role = row.path("jobTitle").asText("Unknown");
                String university = row.path("schoolName").asText("Unknown");
                String location = row.path("location").asText("Unknown");

                System.out.println("\nROW FOUND → " + name + " | " + role + " | " + university);

                // 3️⃣ filtering
                boolean universityMatch =
                        requestDto.getUniversity() == null ||
                                university.toLowerCase()
                                        .contains(requestDto.getUniversity().toLowerCase());

                boolean roleMatch =
                        requestDto.getDesignation() == null ||
                                role.toLowerCase()
                                        .contains(requestDto.getDesignation().toLowerCase());

                if (!universityMatch || !roleMatch) {
                    System.out.println("FILTERED OUT");
                    continue;
                }

                // 4️⃣ save entity
                Alumini alumni = new Alumini();
                alumni.setName(name);
                alumni.setJobRole(role);
                alumni.setUniversity(university);
                alumni.setLocation(location);
                alumni.setLinkedinHeadline(role);
                alumni.setPassoutYear(requestDto.getPassoutYear());
                alumni.setCreatedAt(LocalDateTime.now());

                Alumini saved = aluminiRepository.save(alumni);

                System.out.println("SAVED → ID: " + saved.getId() +
                        " | " + saved.getName());

                responses.add(mapToResponse(saved));
            }

            // ⭐ FINAL RESULT PRINT
            System.out.println("\n===== FINAL RESPONSE LIST =====");
            if (responses.isEmpty()) {
                System.out.println("NO RESULTS FOUND");
            } else {
                responses.forEach(r ->
                        System.out.println(
                                r.getName() + " | " +
                                        r.getDesignation() + " | " +
                                        r.getUniversity() + " | " +
                                        r.getLocation()
                        )
                );
            }
            System.out.println("=================================\n");

            return responses;

        } catch (Exception e) {
            throw new RuntimeException("Error processing Phantom data", e);
        }
    }

    public List<Response> getAllAlumni() {

        List<Response> list = aluminiRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        // ⭐ PRINT DB DATA
        System.out.println("\n===== ALL ALUMNI FROM DATABASE =====");

        if (list.isEmpty()) {
            System.out.println("DATABASE EMPTY");
        } else {
            list.forEach(r ->
                    System.out.println(
                            r.getName() + " | " +
                                    r.getDesignation() + " | " +
                                    r.getUniversity() + " | " +
                                    r.getLocation()
                    )
            );
        }

        System.out.println("=====================================\n");

        return list;
    }

    private Response mapToResponse(Alumini a) {
        return new Response(
                a.getName(),
                a.getJobRole(),
                a.getUniversity(),
                a.getLocation(),
                a.getLinkedinHeadline(),
                a.getPassoutYear()
        );
    }
}
