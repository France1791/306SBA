package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps
 * to) a table
 * name 'course' in the database. A Course object contains fields that represent
 * course
 * information and a mapping of 'courses' that indicate an inverse or
 * referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate
 * code.
 */
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "instructor", length = 50, nullable = false)
    private String instructor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.PERSIST })
    @JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "courses_id"), inverseJoinColumns = @JoinColumn(name = "student_email"))
    private Set<Student> students = new HashSet<>();

    // no args constructor

    public Course() {

    }

    // all args constructor

    public Course(String name, String instructor, Set<Student> students) {
        this.name = name;
        this.instructor = instructor;
        this.students = students;

    }

    // required args constructor
    public Course(String name, String instructor) {
        this.name = name;
        this.instructor = instructor;
    }

    // Getters and setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}