package com.example.spring_security_tutorial.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    public void setId(long id){
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "year_of_birth")
    private int  year_of_birth;

    @Column(name = "password")
    private String password;

    public long getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getname(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getYear_of_birth(){
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth){
        this.year_of_birth = year_of_birth;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
