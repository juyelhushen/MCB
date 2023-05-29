package com.mcb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "groups")
    @JsonIgnoreProperties("groups")
    private List<Students> students;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "groups")
    @JsonIgnoreProperties("groups")
    private List<SubjectTeacher> subjectTeachers;

}
