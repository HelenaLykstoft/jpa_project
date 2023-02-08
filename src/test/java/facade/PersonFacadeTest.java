package facade;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonFacadeTest {

    PersonMapper pm;

    @BeforeEach
    void setUp() {
        pm = new PersonMapper();
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

    }

    @Test
    void getPeopleByCar() {
    }

    @Test
    void getPeopleAboveAvgAge() {
    }

    @Test
    void getPeopleByBirthday() {
    }
}