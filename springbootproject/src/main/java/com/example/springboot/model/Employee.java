package com.example.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int rating;

    public Employee() {
    }

    public Employee(Long id, String name, int rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Method to determine category based on rating
    public String getCategory() {
        if (rating >= 90) {
            return "A";
        } else if (rating >= 75) {
            return "B";
        } else if (rating >= 60) {
            return "C";
        } else if (rating >= 40) {
            return "D";
        } else {
            return "E";
        }
    }
}
