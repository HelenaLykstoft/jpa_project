package facade;

import entities.Car;
import entities.Partner;
import entities.Person;
import entities.Phone;

import javax.enterprise.inject.Typed;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class PersonFacade implements iSelector{
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    // MAIN
    public static void main(String[] args) {
        PersonFacade pf = new PersonFacade();
        
        // Creates person
        Person p = new Person("Helena","1999-01-01 00:01");
        p.addAddress("Hej Alle");
        Phone phone = new Phone("24242424","Home");
        p.addPhones(phone);
        p.addCars(new Car("Audi","R8",2010));
        p.setPartner(new Partner("Rasmus",19));
        Person person = PersonMapper.createPerson(p);
        Car car1 = new Car("Alfa","Romeo",2010);
        PersonMapper.addCarToPerson(person,car1);
//        System.out.println("The person got this new id: " + p.getId());

        // Gets all persons from table
//        pf.getAllPersons().forEach((person)-> System.out.println(person));

//        // Gets ONE person by ID
//        System.out.println("Get single person by ID: ");
//        Person p = pf.getPerson(1L);
//        System.out.println(p);

//        // Updates person by ID
//        p.setName("Tester2");
//        pf.updatePerson(p);
//        System.out.println("Prints the new changed persons");
//        pf.getAllPersons().forEach((person)-> System.out.println(person));
        
//        // Deletes person with id 2 and prints new table
//        System.out.println("Delete person with ID 2");
//        pf.deletePerson(2L);
        PersonMapper.getAllPersons().forEach((element)-> System.out.println(element));

        // Delete stuff, but cant delete because of foreign keys
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        TypedQuery<Person> tp = em.createNamedQuery("Person.deleteById",Person.class);
//        tp.setParameter("id",1L);
//        tp.executeUpdate();
//        em.getTransaction().commit();
//        em.close();
    }

    // ? Methods from iSelector
    @Override
    public Set<Person> getAllPeople() {
        return null;
    }

    @Override
    public Person getPersonById(Long id) {
        return null;
    }

    @Override
    public Person getPersonByPhone(String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p JOIN p.phones ph WHERE ph.number = :number", Person.class);
        tq.setParameter("number",phoneNumber);
        Person p = tq.getSingleResult();
        return p;
    }

    @Override
    public Set<Person> getPeopleByCar(Car car) {
        return null;
    }

    @Override
    public Set<Person> getPeopleAboveAvgAge() {
        return null;
    }

    @Override
    public Set<Person> getPeopleByBirthday() {
        return null;
    }
}
