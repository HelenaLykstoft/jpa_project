package facade;

import entities.Person;
import entities.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Populator {
    public void populate(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person pers1 = new Person("Rikke","1974-01-01 00:00");
            Person pers2 = new Person("Peter","1963-01-01 00:00");
            Person pers3 = new Person("Helena","1998-01-01 00:00");
            Phone ph1 = new Phone("16545678", "Home");
            Phone ph2 = new Phone("12345677", "Work");
            Phone ph3 = new Phone("12373676", "Work");
            pers1.addPhones(ph1);
            pers2.addPhones(ph2);
            pers3.addPhones(ph3);
            em.persist(pers1);
            em.persist(pers2);
            em.persist(pers3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        Populator pop = new Populator();
        pop.populate();
    }
}
