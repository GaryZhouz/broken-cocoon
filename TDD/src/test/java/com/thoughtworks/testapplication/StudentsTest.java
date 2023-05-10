package com.thoughtworks.testapplication;

import com.thoughtworks.testappliction.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentsTest {

    private EntityManagerFactory factory;

    private EntityManager manager;

    private Students students;

    @BeforeEach
    public void before() {
        factory = Persistence.createEntityManagerFactory("student");
        manager = factory.createEntityManager();
        students = new Students(manager);
    }

    static class Students {
        private EntityManager manager;

        public Students(EntityManager manager) {
            this.manager = manager;
        }

        public Student save(Student student) {
            manager.persist(student);
            return student;
        }
    }

    @AfterEach
    public void after() {
        manager.clear();
        manager.close();
        factory.close();
    }

    // save
    @Test
    public void should_save_student_to_db() {
        // exercise
        manager.getTransaction().begin();
        Student saved = students.save(new Student("john", "smith", "john.smith@email.com"));
        manager.getTransaction().commit();

        // verify
        List resultList = manager.createNativeQuery("select id, first_name, last_name, email from STUDENTS s").getResultList();

        assertEquals(1, resultList.size());

        Object[] john = (Object[]) resultList.get(0);

        assertEquals(BigInteger.valueOf(saved.getId()), john[0]);
        assertEquals(saved.getFirstName(), john[1]);
        assertEquals(saved.getLastName(), john[2]);
        assertEquals(saved.getEmail(), john[3]);
    }


    // findById
    // findByEmail
}
