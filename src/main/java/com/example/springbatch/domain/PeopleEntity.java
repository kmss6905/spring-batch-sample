package com.example.springbatch.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    public static PeopleEntity of(final String firstName, final String lastName) {
        // null check
        assert firstName != null;
        assert lastName != null;

        PeopleEntity people = new PeopleEntity();
        people.firstName = firstName;
        people.lastName = lastName;
        return people;
    }

}
