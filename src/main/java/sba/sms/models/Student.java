package sba.sms.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Student is a POJO, configured as a persistent class that represents (or maps
 * to) a table
 * name 'student' in the database. A Student object contains fields that
 * represent student
 * login credentials and a join table containing a registered student's email
 * and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional
 * relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */
@Entity
@Table(name = "Student")
public class Student {

    @Id
    @Column(length = 50, name = "email")
    private String email;

    @Column(length = 50, name = "name")
    private String name;

    @Column(length = 50, name = "password")
    private String password;

    @ManyToMany(targetEntity = Course.class, fetch = FetchType.EAGER, cascade
            = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "student_email"), inverseJoinColumns = @JoinColumn(name = "courses_id"))
    private Set<Course> courses = new HashSet<>();

    // no args constructor
    public Student() {}

    // all args constructor
    public Student(String email, String name, String password, Set<Course> Courses) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // required args constructor
    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    //Getters and Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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