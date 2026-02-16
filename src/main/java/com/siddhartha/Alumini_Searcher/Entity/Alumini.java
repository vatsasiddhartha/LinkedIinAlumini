package com.siddhartha.Alumini_Searcher.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "alumni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumini {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String university;
    private String location;
    private String linkedinHeadline;
    private Integer passoutYear;

    // ✅ renamed
    private String jobRole;

    private LocalDateTime createdAt;
}
