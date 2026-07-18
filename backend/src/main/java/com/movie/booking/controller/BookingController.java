package com.movie.booking.controller;

import com.movie.booking.entity.Booking;
import com.movie.booking.service.BookingService;
import com.movie.booking.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ReportService reportService;

    // Request class for creating a booking
    public static class BookingRequest {
        public Long userId;
        public Long showId;
        public List<Long> seatIds;
    }

    @PostMapping
    public ResponseEntity<?> bookTicket(@RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(
                    request.userId,
                    request.showId,
                    request.seatIds
            );
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }

    @GetMapping("/reports/revenue")
    public ResponseEntity<?> getRevenueReport() {
        try {
            Map<String, Object> report = reportService.getRevenueReport();
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
