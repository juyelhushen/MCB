package com.mcb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "marks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Marks implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int markId;
    private LocalDateTime date;
    private int mark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",nullable = false)
    @JsonIgnoreProperties("marks")
    private Students students;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id",nullable = false)
    @JsonIgnoreProperties("marks")
    private Subjects subjects;

}
