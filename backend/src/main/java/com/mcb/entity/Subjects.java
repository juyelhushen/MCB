package com.mcb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Table(name = "subjects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subjects implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subjectId;
    private String title;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "subjects")
    @JsonIgnoreProperties("subjects")
    @JsonIgnore
    private List<Marks> marks;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "subjects")
    @JsonIgnoreProperties("subjects")
    @JsonIgnore
    private List<SubjectTeacher> subjectTeachers;

    public Subjects(int subjectId, String title) {
        this.subjectId = subjectId;
        this.title = title;
    }
}
