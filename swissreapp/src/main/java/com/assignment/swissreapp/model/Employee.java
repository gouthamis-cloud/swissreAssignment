package com.assignment.swissreapp.model;


import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {


    private Long id;

    private String name;
    private String city;
    private String state;
    private String category; // employee, manager, director
    private Long managerId;
    private Integer salary;

    private LocalDate doj;



}
