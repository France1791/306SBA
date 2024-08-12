package sba.sms.services;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import java.util.List;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {

    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session s = null;

    public void createStudent(Student newStudent) {

        Transaction transaction = null;

        try {

            // Beginning transaction
            s = factory.openSession();
            transaction = s.beginTransaction();

            // Saving student to our database
            s.persist(newStudent);

            // commit our transaction to see in our database;
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    public Student getStudentByEmail(String email) {

        Student foundStudent = null;
        Transaction transaction = null;
        try {
            // Beginning transaction
            s = factory.openSession();
            transaction = s.beginTransaction();

            // We're getting a specific student with the students email from our database
            foundStudent = s.get(Student.class, email);

            // MIGHT NOT NEED;
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        }
        return foundStudent;
    }

    public boolean validateStudent(String email, String password) {

        Transaction transaction = null;

        try {
            // Beginning transaction
            s = factory.openSession();
            transaction = s.beginTransaction();

            // QUERY THAT WE SENT TO THE DATABASE
            String hqlString = "FROM  Student WHERE email = :email AND password = :password";
            Query<Student> query = s.createQuery(hqlString, Student.class);

            // Changing the variables with our own values
            query.setParameter("email", email);
            query.setParameter("password", password);

            // Getting whatever our database gave back to us
            Student student = query.getSingleResult();
            transaction.commit();

            return student != null;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        }

        return false;

    }

    public List<Student> getAllStudents() {
        Transaction transaction = null;

        try {
            String hqlString = "SELECT Student from Students";
            Query<Student> query = s.createQuery(hqlString, Student.class);
            return query.getResultList();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Course> getStudentCourses(String email) {
        Transaction transaction = null;
        try {

            // Beginning transaction
            transaction = s.beginTransaction();

            String hqlString = "SELECT course FROM Course course JOIN course.students student WHERE student.email = :email";

            Query<Course> query = s.createQuery(hqlString, Course.class);
            query.setParameter("email", email);
            List<Course> StudentCourses = query.getResultList();
            transaction.commit();
            return StudentCourses;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;
        s = factory.openSession();

        try {
            transaction = s.beginTransaction();
            Student student = s.get(Student.class, email);
            Course course = s.get(Course.class, courseId);
            student.getCourses().add(course);
            s.merge(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

}