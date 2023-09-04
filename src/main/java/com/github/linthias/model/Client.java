package com.github.linthias.model;

import java.time.LocalDate;

public class Client {
    private Long id;
    private String firstName;
    private String middleName;
    private String surname;
    private LocalDate birthdate;
    private String gender;

    public Client(Long id,
                  String firstName,
                  String middleName,
                  String surname,
                  LocalDate birthdate,
                  String gender) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getMiddleName() {
        return middleName;
    }


    public String getSurname() {
        return surname;
    }


    public LocalDate getBirthdate() {
        return birthdate;
    }


    public String getGender() {
        return gender;
    }
}
