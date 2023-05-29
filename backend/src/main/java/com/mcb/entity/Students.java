package com.mcb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Students implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",nullable = false)
    @JsonIgnoreProperties("students")
    private Groups groups;

    @OneToMany(mappedBy ="students", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnoreProperties("students")
    private List<Marks> marks;



}
