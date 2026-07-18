package com.movie.booking.service;

import com.movie.booking.entity.Booking;
import com.movie.booking.entity.Show;
import com.movie.booking.entity.ShowSeat;
import com.movie.booking.entity.User;
import com.movie.booking.repository.BookingRepository;
import com.movie.booking.repository.ShowRepository;
import com.movie.booking.repository.ShowSeatRepository;
import com.movie.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Transactional
    public Booking createBooking(Long userId, Long showId, List<Long> seatIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found!"));

        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("Some seats were not found!");
        }

        // Check if any seat is already booked
        for (ShowSeat seat : seats) {
            if (!seat.getShow().getId().equals(showId)) {
                throw new RuntimeException("Seats must belong to the selected show!");
            }
            if (seat.getIsBooked()) {
                throw new RuntimeException("Seat " + seat.getRowName() + seat.getSeatNumber() + " is already booked!");
            }
        }

        // Create booking details
        Double totalAmount = seats.size() * show.getPricePerSeat();
        String seatNames = seats.stream()
                .map(s -> s.getRowName() + s.getSeatNumber())
                .collect(Collectors.joining(", "));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingTime(LocalDateTime.now());
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");
        booking.setBookedSeats(seatNames);

        Booking savedBooking = bookingRepository.save(booking);

        // Mark seats as booked and link to this booking
        for (ShowSeat seat : seats) {
            seat.setIsBooked(true);
            seat.setBooking(savedBooking);
        }
        showSeatRepository.saveAll(seats);

        return savedBooking;
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserIdOrderByBookingTimeDesc(userId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<ShowSeat> getSeatsForShow(Long showId) {
        return showSeatRepository.findByShowId(showId);
    }
}
