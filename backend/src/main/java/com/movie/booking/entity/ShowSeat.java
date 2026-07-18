package com.movie.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "show_seats",
    uniqueConstraints = @UniqueConstraint(columnNames = {"show_id", "row_name", "seat_number"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
    
    @Column(name = "row_name", nullable = false)
    private String rowName; // 'A', 'B', 'C', etc.
    
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber; // 1, 2, 3, etc.
    
    @Column(nullable = false)
    private Boolean isBooked = false;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking; // References the booking if booked
}
