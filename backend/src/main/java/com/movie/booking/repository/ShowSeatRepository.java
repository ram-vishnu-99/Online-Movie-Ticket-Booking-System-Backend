package com.movie.booking.repository;

import com.movie.booking.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowId(Long showId);
    Optional<ShowSeat> findByShowIdAndRowNameAndSeatNumber(Long showId, String rowName, Integer seatNumber);
}
