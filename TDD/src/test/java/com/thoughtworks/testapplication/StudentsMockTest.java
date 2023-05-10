package com.thoughtworks.testapplication;

import com.thoughtworks.testappliction.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentsMockTest {

    private EntityManager manager;
    private Students repository;
    private Student john = new Student("john", "smith", "john.smith@email.com");

    @BeforeEach
    public void before() {
        manager = mock(EntityManager.class);
        repository = new Students(manager);
    }

    @Test
    public void should_generate_id_for_saved_entity() {
        repository.save(john);

        verify(manager).persist(john);
    }

    @Test
    public void should_be_able_to_load_saved_student_by_id() {
        when(manager.find(any(), any())).thenReturn(john);

        assertEquals(john, repository.findById(1).get());

        verify(manager).find(Student.class, 1L);
    }

    @Test
    public void should_be_able_to_load_saved_student_by_email() {
        TypedQuery query = mock(TypedQuery.class);

        when(manager.createQuery(any(), any())).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(singletonList(john));

        assertEquals(john, repository.findByEmail("john.smith@email.com").get());

        verify(manager).createQuery("SELECT s from Student s where s.email = :email", Student.class);
        verify(query).setParameter("email", "john.smith@email.com");
    }

    static class Students {
        private EntityManager manager;

        public Students(EntityManager manager) {
            this.manager = manager;
        }

        public void save(Student student) {
            manager.persist(student);
        }

        public Optional<Student> findById(long id) {
            return Optional.ofNullable(manager.find(Student.class, id));
        }

        public Optional<Student> findByEmail(String mail) {
            TypedQuery<Student> query = manager.createQuery("SELECT s from Student s where s.email = :email", Student.class);
            query.setParameter("email", "john.smith@email.com");
            return Optional.ofNullable(query.getResultList().get(0));
        }
    }

}
