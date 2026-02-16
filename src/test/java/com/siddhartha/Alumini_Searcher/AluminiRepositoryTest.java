package com.siddhartha.Alumini_Searcher;

import com.siddhartha.Alumini_Searcher.Entity.Alumini;
import com.siddhartha.Alumini_Searcher.Repository.AluminiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@DataJpaTest
class AluminiRepositoryTest {

    @Autowired
    private AluminiRepository repository;

    @Test
    void shouldSaveAlumni() {

        Alumini a = new Alumini();
        a.setName("Test User");

        Alumini saved = repository.save(a);

        assertNotNull(saved.getId());
    }
}
