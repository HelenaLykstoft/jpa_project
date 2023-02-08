package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = "Person.deleteById", query = "delete from Person p where p.id = :id")
})
public class Person {

    // ? FIELDS & COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Transient
    private Integer age;

    @Column(name = "birth_date", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP) // Not needed, but its good to be explicit
    private LocalDateTime birthdate;

    @ElementCollection
    @Column(name = "address")
    @CollectionTable(name = "person_addresses", joinColumns = @JoinColumn(name = "person_id"))
    private Set<String> addresses = new HashSet<>();


    @Column(name ="created", updatable = false)
    private LocalDateTime created;

    @Column (name = "modified")
    private LocalDateTime editted;


    @PreUpdate
    public void onUpdate(){
        editted = LocalDateTime.now(ZoneId.of("GMT+02:00"));
    }

    @PrePersist
    public void onPersist(){
        editted = LocalDateTime.now(ZoneId.of("GMT+02:00"));
        created = LocalDateTime.now(ZoneId.of("GMT+02:00"));

    }

    // ? RELATIONSHIPS
    // * ONE TO ONE

    @OneToOne(mappedBy = "person", cascade = CascadeType.PERSIST,orphanRemoval = true)
    private Partner partner;

    // * ONE TO MANY
    @OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Phone> phones = new LinkedHashSet<>();

    // * MANY TO MANY
    @ManyToMany
    @JoinTable(name = "person_cars",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "cars_id"))
    private Set<Car> cars = new LinkedHashSet<>();


    // ? CONSTRUCTORS

    public Person(){

    }

    public Person(String name, String birthdate) {
        this.name = name;
        setBirth_date(birthdate);
        setAge();
    }

    // ? GETTERS

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getBirth_date() {
        return birthdate;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public Set<String> getAddresses() {
        return addresses;
    }

    public Set<Car> getCars() {
        return cars;
    }




    // ? SETTERS

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostLoad // Every time we load person, it sets the age
    public void setAge() {
        java.time.Duration duration = java.time.Duration.between(birthdate,LocalDateTime.now());
        this.age = (int) duration.toDays() / 365;
    }

    public void setBirth_date(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bd = LocalDateTime.parse(birthdate,formatter);
        this.birthdate = bd;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
        partner.setPerson(this);
    }


    // ? ADD & REMOVE
    // * CARS

    public void addCars(Car cars) {
        this.cars.add(cars);
        cars.getPersons().add(this);
    }

    public void removeCars(Car cars) {
        this.cars.remove(cars);
        cars.getPersons().remove(this);
    }


    // * PHONES
    public void addPhones(Phone phone) {
        this.phones.add(phone);
        phone.setPerson(this);
    }

    public void removePhone(Phone phone){
        this.phones.remove(phone);
        phone.setPerson(this);
    }


    // * ADDRESS
    public void addAddress(String address) {
        this.addresses.add(address);
    }

    public void removeAddress(String address) {
        this.addresses.removeIf(a -> Objects.equals(a, address));
    }

    // * PARTNER




    // ? TO STRING
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", addresses=" + addresses +
                ", birthdate=" + birthdate +
                '}';
    }
}

