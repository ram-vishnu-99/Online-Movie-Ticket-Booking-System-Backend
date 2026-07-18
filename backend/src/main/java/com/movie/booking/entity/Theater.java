package com.movie.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theaters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Integer rowCount; // number of rows, e.g., 8 (A to H)
    
    @Column(nullable = false)
    private Integer colCount; // number of columns, e.g., 10 (1 to 10)
}
