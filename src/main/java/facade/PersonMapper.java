package facade;

import entities.Car;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonMapper {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");


    // * Creates a person
    public static Person createPerson(Person p) {
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
//            if (p.getPartner()!= null) {
//                if (p.getPartner().getId() == null) {
//                    em.persist(p.getPartner());
//                } else {
//                    em.merge(p.getPartner());
//                }
//            }
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }

    // * Get all persons from table
    public static Set<Person> getAllPersons(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> typedQuery = em.createQuery("SELECT p FROM Person p JOIN FETCH p.addresses", Person.class);
        Set<Person> persons = typedQuery.getResultList().stream().collect(Collectors.toSet());
        return persons;
    }

    // * Get one person by ID
    public static Person getPerson(Long id){
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        return p;
    }

    // * Update person
    public static Person updatePerson(Person p){
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

    // * Delete person
    public static Person deletePerson(Long id){
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

    // * Get car and add it to a person
    public static boolean addCarToPerson(Person person, Car car){
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, person.getId());
        if (p == null){
            throw new IllegalArgumentException("No person with that id.");
        }
        em.getTransaction().begin();
        if(car.getId() == null){
            em.persist(car);
        } else{
            car = em.find(Car.class, car.getId());
            // em.merge(car);
        }

        p.addCars(car);
        em.getTransaction().commit();
        em.close();
        return true;
    }
}
