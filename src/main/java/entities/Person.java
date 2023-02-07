package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Transient
    private Integer age;

    @ElementCollection
    @Column(name = "address")
    @CollectionTable(name = "person_addresses", joinColumns = @JoinColumn(name = "person_id"))
    private Set<String> addresses = new LinkedHashSet<>();

    @Column(name = "birth_date", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP) // Not needed, but its good to be explicit
    private LocalDateTime birthdate;

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

    @OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Phone> phones = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "person_cars",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "cars_id"))
    private Set<Car> cars = new LinkedHashSet<>();

    public Set<Car> getCars() {
        return cars;
    }

    public void addCars(Car cars) {
        this.cars.add(cars);
        cars.getPersons().add(this);
    }

    public void removeCars(Car cars) {
        this.cars.remove(cars);
        cars.getPersons().remove(this);
    }


    public Set<Phone> getPhones() {
        return phones;
    }

    public void addPhones(Phone phone) {
        this.phones.add(phone);
        phone.setPerson(this);
    }

    public void removePhone(Phone phone){
        this.phones.remove(phone);
        phone.setPerson(this);
    }

    public LocalDateTime getBirth_date() {
        return birthdate;
    }

    public void setBirth_date(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bd = LocalDateTime.parse(birthdate,formatter);
        this.birthdate = bd;
    }

    public Set<String> getAddresses() {
        return addresses;
    }

    public void addAddress(String address) {
        this.addresses.add(address);
    }

    public void removeAddress(String address) {
        this.addresses.removeIf(a -> Objects.equals(a, address));
    }


    public Person(){

    }

    public Person(String name, String birthdate) {
        this.name = name;
        setBirth_date(birthdate);
        setAge();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge() {
        java.time.Duration duration = java.time.Duration.between(birthdate,LocalDateTime.now());
        this.age = (int) duration.toDays() / 365;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

