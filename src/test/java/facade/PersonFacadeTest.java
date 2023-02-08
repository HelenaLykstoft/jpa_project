package facade;

import entities.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonFacadeTest {

    PersonMapper pm;
    PersonFacade pf;

    @BeforeEach
    void setUp() {
        pm = new PersonMapper();
        pf = new PersonFacade();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllPeople() {
    }

    @Test
    void getPersonById() {
    }

    @Test
    void getPersonByPhone() {
        System.out.println("Get person by phone number");
        String expected = "Helena";
        String actual = pf.getPersonByPhone("12373676").getName();
        assertEquals(expected,actual);
    }

    @Test
    void getPeopleByCar() {
    }

    @Test
    void getPeopleAboveAvgAge() {
        System.out.println("Get people above average age");
        String expected = "Helena";
        Set<Person> actual = pf.getPeopleAboveAvgAge();
        actual.forEach(person -> {
            assertTrue(person.getName().equals(expected));
        });
    }

    @Test
    void getPeopleByBirthday() {
    }
}