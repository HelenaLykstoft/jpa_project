package entities;

import javax.persistence.*;

@Entity
@Table(name = "partner")

public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column (name = "partnerName", nullable = false, unique = true, length = 40)
    private String partnerName;

    @Column (name = "partnerAge", nullable = false, unique = true, length = 2)
    private int partnerAge;


    @OneToOne
    @JoinColumn (name = "person_id",unique = true)
    private Person person;

    public Partner() {
    }

    public Partner(String partnerName, int partnerAge) {
        this.partnerName = partnerName;
        this.partnerAge = partnerAge;
    }

    public String getPartnerName() {
        return partnerName;
    }

//    public void setPartnerName(String partnerName) {
//        this.partnerName = partnerName;
//    }
//
//    public int getPartnerAge() {
//        return partnerAge;
//    }
//
//    public void setPartnerAge(int partnerAge) {
//        this.partnerAge = partnerAge;
//    }

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