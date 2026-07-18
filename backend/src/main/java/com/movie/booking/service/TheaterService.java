package com.movie.booking.service;

import com.movie.booking.entity.Theater;
import com.movie.booking.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Theater getTheaterById(Long id) {
        return theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found!"));
    }

    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    public Theater updateTheater(Long id, Theater theaterDetails) {
        Theater theater = getTheaterById(id);
        theater.setName(theaterDetails.getName());
        theater.setLocation(theaterDetails.getLocation());
        theater.setRowCount(theaterDetails.getRowCount());
        theater.setColCount(theaterDetails.getColCount());
        return theaterRepository.save(theater);
    }

    public void deleteTheater(Long id) {
        Theater theater = getTheaterById(id);
        theaterRepository.delete(theater);
    }
}
