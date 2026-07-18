package com.movie.booking.service;

import com.movie.booking.entity.Show;
import com.movie.booking.entity.ShowSeat;
import com.movie.booking.entity.Theater;
import com.movie.booking.repository.ShowRepository;
import com.movie.booking.repository.ShowSeatRepository;
import com.movie.booking.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public Show getShowById(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found!"));
    }

    @Transactional
    public Show createShow(Show show) {
        // Fetch theater to make sure rowCount/colCount are populated
        Theater theater = theaterRepository.findById(show.getTheater().getId())
                .orElseThrow(() -> new RuntimeException("Theater not found!"));
        show.setTheater(theater);

        // Save show first
        Show savedShow = showRepository.save(show);

        // Auto-generate seats for this show based on the Theater dimensions
        List<ShowSeat> showSeats = new ArrayList<>();
        int rowCount = theater.getRowCount();
        int colCount = theater.getColCount();

        for (int i = 0; i < rowCount; i++) {
            String rowName = String.valueOf((char) ('A' + i));
            for (int j = 1; j <= colCount; j++) {
                ShowSeat seat = new ShowSeat();
                seat.setShow(savedShow);
                seat.setRowName(rowName);
                seat.setSeatNumber(j);
                seat.setIsBooked(false);
                showSeats.add(seat);
            }
        }
        showSeatRepository.saveAll(showSeats);

        return savedShow;
    }

    @Transactional
    public void deleteShow(Long id) {
        Show show = getShowById(id);
        // Delete all seats associated with the show
        List<ShowSeat> seats = showSeatRepository.findByShowId(id);
        showSeatRepository.deleteAll(seats);
        // Delete show
        showRepository.delete(show);
    }
}
