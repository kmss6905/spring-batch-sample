package com.example.springbatch.repository;

import com.example.springbatch.domain.PeopleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class) // ? <-- 정말 왜 쓰는 지 알아?
@DataJpaTest
class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    void save() {
        // given
        peopleRepository.save(PeopleEntity.of("robert", "june"));
        peopleRepository.save(PeopleEntity.of("mick", "jane"));

        // when
        List<PeopleEntity> peoples = peopleRepository.findAll();

        // then
        assertThat(peoples).hasSize(2);
    }
}