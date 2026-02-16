package com.siddhartha.Alumini_Searcher.Controller;

import com.siddhartha.Alumini_Searcher.Services.AluminiService;
import com.siddhartha.Alumini_Searcher.dto.RequestDto;
import com.siddhartha.Alumini_Searcher.dto.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumni")
public class AluminiController {

    private final AluminiService aluminiService;

    public AluminiController(AluminiService aluminiService) {
        this.aluminiService = aluminiService;
    }

    // POST → search alumni with filters
    @PostMapping("/search")
    public ResponseEntity<List<Response>> searchAlumni(@Valid @RequestBody RequestDto requestDto) {
        List<Response> results = aluminiService.searchAlumini(requestDto);
        return ResponseEntity.ok(results);
    }

    // GET → fetch all alumni
    @GetMapping("/all")
    public ResponseEntity<List<Response>> getAll() {
        List<Response> all = aluminiService.getAllAlumni();
        return ResponseEntity.ok(all);
    }
}
