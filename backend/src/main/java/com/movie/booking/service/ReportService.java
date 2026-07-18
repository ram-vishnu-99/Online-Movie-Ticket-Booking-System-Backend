package com.movie.booking.service;

import com.movie.booking.entity.Booking;
import com.movie.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private BookingRepository bookingRepository;

    public Map<String, Object> getRevenueReport() {
        List<Booking> confirmedBookings = bookingRepository.findByStatus("CONFIRMED");

        double totalRevenue = confirmedBookings.stream()
                .mapToDouble(Booking::getTotalAmount)
                .sum();

        long totalTickets = confirmedBookings.stream()
                .mapToLong(b -> b.getBookedSeats().split(",").length)
                .sum();

        // Calculate Revenue by Movie
        Map<String, Double> revenueByMovie = confirmedBookings.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getShow().getMovie().getTitle(),
                        Collectors.summingDouble(Booking::getTotalAmount)
                ));

        // Calculate Revenue by Theater
        Map<String, Double> revenueByTheater = confirmedBookings.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getShow().getTheater().getName(),
                        Collectors.summingDouble(Booking::getTotalAmount)
                ));

        // Format a response map
        Map<String, Object> report = new HashMap<>();
        report.put("totalRevenue", totalRevenue);
        report.put("totalTicketsSold", totalTickets);
        report.put("totalBookingsCount", confirmedBookings.size());
        report.put("revenueByMovie", revenueByMovie);
        report.put("revenueByTheater", revenueByTheater);

        return report;
    }
}
