package com.movie.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
    
    @Column(nullable = false)
    private LocalDateTime bookingTime;
    
    @Column(nullable = false)
    private Double totalAmount;
    
    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED
    
    @Column(nullable = false)
    private String bookedSeats; // Comma separated seat names, e.g. "A1, A2"
}
