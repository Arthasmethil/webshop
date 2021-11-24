package client.domain;

import application.Utils;
import constants.ClientVar;

import java.util.Objects;

public class Client {
    private Long id;
    private String name;
    private String lastname;
    private String phone;
    
    public Client() {
    }
    public Client(String name, String surname, String phone) {
        this.name = name;
        this.lastname = surname;
        this.phone = phone;
    }
    public Client(Long id, String name, String surname, String phone) {
        this.id = id;
        this.name = name;
        this.lastname = surname;
        this.phone = phone;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public static Client createRandomClient () {
        return new Client(
                ClientVar.NAMES[(int) (Math.random() * ClientVar.NAMES.length)],
                ClientVar.LASTNAMES[(int) (Math.random() * ClientVar.LASTNAMES.length)],
                ClientVar.PHONES_START[(int) (Math.random() * ClientVar.PHONES_START.length)]
                        +ClientVar.PHONES_ENDS[(int) (Math.random() * ClientVar.PHONES_ENDS.length)]
        );
    }

    public static Client createClientManual(String clientData) {
        boolean matcher = clientData.matches("\\w*[a-zA-Zа-яА-Я]\\s+\\w*[a-zA-Zа-яА-Я]\\s+[0-9]{3}-[0-9]{2,3}-[0-9]{3}");
        if (!matcher) {
            System.out.println("Wrong data, try again");
            return createClientManual(Utils.simpleStringConsoleInput());
        }
        String[] nameLastnamePhone = clientData.split(" ");
        if (nameLastnamePhone[0].length() > 45 || nameLastnamePhone[1].length() > 45 || nameLastnamePhone[2].length() > 11) {
            System.out.println("Use only letters for name/lastname (length <= 45 signs) and digits for phone(length <= 11 signs)");
            return createClientManual(Utils.simpleStringConsoleInput());
        }
        return new Client(
                nameLastnamePhone[0],
                nameLastnamePhone[1],
                nameLastnamePhone[2]
        );
    }
    
    @Override
    public String toString() {
        return "client id:"
                + id +
                " {'" + name +
                " " + lastname + "'" +
                " phone: " + phone + "}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id) && name.equals(client.name) && lastname.equals(client.lastname);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname);
    }
    
}
