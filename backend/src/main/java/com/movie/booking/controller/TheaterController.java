package com.movie.booking.controller;

import com.movie.booking.entity.Theater;
import com.movie.booking.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/theaters")
@CrossOrigin(origins = "*")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTheaterById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(theaterService.getTheaterById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTheater(@RequestBody Theater theater) {
        try {
            return ResponseEntity.ok(theaterService.createTheater(theater));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTheater(@PathVariable Long id, @RequestBody Theater theaterDetails) {
        try {
            return ResponseEntity.ok(theaterService.updateTheater(id, theaterDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTheater(@PathVariable Long id) {
        try {
            theaterService.deleteTheater(id);
            return ResponseEntity.ok(Map.of("message", "Theater deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
