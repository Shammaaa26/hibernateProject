package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;



@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;
    
    @ManyToMany
    @JoinTable(
    		name = "student_course",
    		joinColumns = @JoinColumn(name = "student_id"),
    		inverseJoinColumns = @JoinColumn(name = "course_id")
    		)
    private List<Course> courses = new ArrayList<>();
    public List<Course> getCourse() {
    	return courses;
    }
    

    

    
    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    
    @Override
    public String toString() {
        return "Student [id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email + "]";
    }
    public void addCourse(Course course) {
    	courses.add(course);
    }
}