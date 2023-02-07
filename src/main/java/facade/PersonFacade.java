package facade;

import entities.Car;
import entities.Person;
import entities.Phone;

import javax.persistence.*;
import java.util.List;

public class PersonFacade {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    // Creates a person
    public Person createPerson(Person p) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            p.getCars().forEach(car -> {
                if (car.getId() == null){
                    em.persist(car);
                } else {
                    em.merge(car);
                }
            });
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }

    // Get all persons from table
    public List<Person> getAllPersons(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> typedQuery = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = typedQuery.getResultList();
        return persons;
    }

    // Get one person by ID
    public Person getPerson(Long id){
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        return p;
    }

    // Update person
    public Person updatePerson(Person p){
        EntityManager em = emf.createEntityManager();
        Person found = em.find(Person.class, p.getId());
        if (found == null){
            throw new IllegalArgumentException("No person with that ID.");
        }
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }

    // Delete person
    public Person deletePerson(Long id){
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p==null){
            throw new IllegalArgumentException("No person with that id: " + id + " exists.");
        }
        try{
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }

    // MAIN
    public static void main(String[] args) {
        PersonFacade pf = new PersonFacade();
        // Creates person
        Person p = new Person("Helena","1999-01-01 00:01");
        p.addAddress("Hej Alle");
        Phone phone = new Phone("24242424","Home");
        p.addPhones(phone);
        p.addCars(new Car("Audi","R8",2010));
        pf.createPerson(p);
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
        pf.getAllPersons().forEach((person)-> System.out.println(person));
    }
}
