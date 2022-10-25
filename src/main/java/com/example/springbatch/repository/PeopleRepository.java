package com.example.springbatch.repository;

import com.example.springbatch.domain.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<PeopleEntity, Long> {
}
