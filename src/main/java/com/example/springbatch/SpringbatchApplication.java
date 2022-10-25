package com.example.springbatch;

import com.example.springbatch.domain.PeopleEntity;
import com.example.springbatch.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SpringbatchApplication implements CommandLineRunner {

    private final PeopleRepository peopleRepository;

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(SpringbatchApplication.class, args)));
    }

    @Override
    public void run(String... args) throws Exception {

    }

//    private void test() {
//        PeopleEntity savedPeople = peopleRepository.save(PeopleEntity.of("minshik", "kim"));
//        PeopleEntity findPeople = peopleRepository.findById(1L)
//                .orElseThrow(IllegalArgumentException::new);
//
//        assert findPeople.getFirstName().equals(savedPeople.getFirstName());
//        assert findPeople.getLastName().equals(savedPeople.getLastName());
//    }
}
