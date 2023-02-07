package entities;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column (name = "phonenumber", nullable = false, unique = true, length = 13)
    private String number;

    @Column (name = "description", nullable = false, length = 30)
    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id", unique = true)
    private Person person;

    // CONSTRUCTORS
    public Phone(){
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}