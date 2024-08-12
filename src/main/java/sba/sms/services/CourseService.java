package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {

    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session s = null;

    public void createCourse(Course course) {
        Transaction transaction = null;

        try {
            // We're starting to begin a transaction where we want to make changes to our
            // database
            s = factory.openSession();
            transaction = s.beginTransaction();

            // Save course to our database
            s.persist(course);

            // commit course/ push course to our database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    public Course getCourseById(int courseId) {

        Course course = null;
        Transaction transaction = null;

        try {
            // We're starting transaction here
            s = factory.openSession();
            transaction = s.beginTransaction();

            // We're getting a specific course with our courseId from our database
            course = s.get(Course.class, courseId);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }

        return course;
    }

    public List<Course> getAllCourses() {
        // TODO: MIGHT NOT NEED THIS?
        Transaction transaction = null;
        try {
            // We're starting the transaction here!
            s = factory.openSession();
            // TODO: MIGHT NOT NEED THIS?
            transaction = s.beginTransaction();

            // Making a SQL QUERY
            String hql = "SELECT course FROM Course course";

            // Sending query to our database
            TypedQuery<Course> query = s.createQuery(hql, Course.class);

            // returning whatever we got from our database using our query
            return query.getResultList();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

    }

}
