package com.movie.booking.controller;

import com.movie.booking.entity.Show;
import com.movie.booking.entity.ShowSeat;
import com.movie.booking.service.BookingService;
import com.movie.booking.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shows")
@CrossOrigin(origins = "*")
public class ShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShowById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(showService.getShowById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable Long movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody Show show) {
        try {
            return ResponseEntity.ok(showService.createShow(show));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable Long id) {
        try {
            showService.deleteShow(id);
            return ResponseEntity.ok(Map.of("message", "Show deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Expose seats configuration for a specific show
    @GetMapping("/{id}/seats")
    public List<ShowSeat> getSeatsForShow(@PathVariable Long id) {
        return bookingService.getSeatsForShow(id);
    }
}
