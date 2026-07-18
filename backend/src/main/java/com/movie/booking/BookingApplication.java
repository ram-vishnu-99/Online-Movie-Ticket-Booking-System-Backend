package com.movie.booking;

import com.movie.booking.entity.Movie;
import com.movie.booking.entity.Show;
import com.movie.booking.entity.Theater;
import com.movie.booking.entity.User;
import com.movie.booking.repository.MovieRepository;
import com.movie.booking.repository.TheaterRepository;
import com.movie.booking.repository.UserRepository;
import com.movie.booking.service.ShowService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            MovieRepository movieRepository,
            TheaterRepository theaterRepository,
            ShowService showService
    ) {
        return args -> {
            // 1. Seed Users if not present
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User(null, "admin", "admin@movie.com", "admin", "ADMIN");
                userRepository.save(admin);
                System.out.println("Seeded Admin user (username: admin, password: admin)");
            }
            if (!userRepository.existsByUsername("manager")) {
                User manager = new User(null, "manager", "manager@movie.com", "manager", "THEATER_MANAGER");
                userRepository.save(manager);
                System.out.println("Seeded Manager user (username: manager, password: manager)");
            }
            if (!userRepository.existsByUsername("customer")) {
                User customer = new User(null, "customer", "customer@movie.com", "customer", "CUSTOMER");
                userRepository.save(customer);
                System.out.println("Seeded Customer user (username: customer, password: customer)");
            }

            // 2. Seed Movies if none present
            if (movieRepository.count() == 0) {
                Movie movie1 = new Movie();
                movie1.setTitle("Inception");
                movie1.setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.");
                movie1.setGenre("Sci-Fi / Thriller");
                movie1.setLanguage("English");
                movie1.setDuration(148);
                movie1.setReleaseDate(LocalDate.of(2010, 7, 16));
                movie1.setPosterUrl("https://images.unsplash.com/photo-1536440136628-849c177e76a1?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3");
                movieRepository.save(movie1);

                Movie movie2 = new Movie();
                movie2.setTitle("Interstellar");
                movie2.setDescription("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.");
                movie2.setGenre("Sci-Fi / Drama");
                movie2.setLanguage("English");
                movie2.setDuration(169);
                movie2.setReleaseDate(LocalDate.of(2014, 11, 7));
                movie2.setPosterUrl("https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3");
                movieRepository.save(movie2);

                Movie movie3 = new Movie();
                movie3.setTitle("The Dark Knight");
                movie3.setDescription("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.");
                movie3.setGenre("Action / Crime");
                movie3.setLanguage("English");
                movie3.setDuration(152);
                movie3.setReleaseDate(LocalDate.of(2008, 7, 18));
                movie3.setPosterUrl("https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3");
                movieRepository.save(movie3);

                System.out.println("Seeded initial movies (Inception, Interstellar, The Dark Knight)");
            }

            // 3. Seed Theaters if none present
            if (theaterRepository.count() == 0) {
                Theater theater1 = new Theater(null, "IMAX Screen 1", "Downtown Mall", 6, 10); // 60 seats
                Theater theater2 = new Theater(null, "Gold Class Lounge", "WestSide Plaza", 4, 6);   // 24 seats
                theaterRepository.save(theater1);
                theaterRepository.save(theater2);
                System.out.println("Seeded theaters (IMAX Screen 1, Gold Class Lounge)");

                // 4. Seed Shows if theaters were seeded and shows count is zero
                if (showService.getAllShows().isEmpty()) {
                    Movie m1 = movieRepository.findAll().get(0);
                    Movie m2 = movieRepository.findAll().get(1);
                    Theater t1 = theaterRepository.findAll().get(0);
                    Theater t2 = theaterRepository.findAll().get(1);

                    // Show 1: Inception in IMAX Screen 1 tomorrow at 6 PM
                    Show show1 = new Show();
                    show1.setMovie(m1);
                    show1.setTheater(t1);
                    show1.setStartTime(LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0));
                    show1.setEndTime(LocalDateTime.now().plusDays(1).withHour(20).withMinute(30).withSecond(0).withNano(0));
                    show1.setPricePerSeat(15.0);
                    showService.createShow(show1);

                    // Show 2: Interstellar in Gold Class Lounge tomorrow at 9 PM
                    Show show2 = new Show();
                    show2.setMovie(m2);
                    show2.setTheater(t2);
                    show2.setStartTime(LocalDateTime.now().plusDays(1).withHour(21).withMinute(0).withSecond(0).withNano(0));
                    show2.setEndTime(LocalDateTime.now().plusDays(1).withHour(23).withMinute(50).withSecond(0).withNano(0));
                    show2.setPricePerSeat(25.0);
                    showService.createShow(show2);

                    System.out.println("Scheduled initial shows with generated seats!");
                }
            }
        };
    }
}
