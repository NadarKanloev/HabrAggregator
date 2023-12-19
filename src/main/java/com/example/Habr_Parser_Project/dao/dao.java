package com.example.Habr_Parser_Project.dao;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class dao {
    private final String JDBC_URL = "jdbc:postgresql://localhost:5432/SpringSceurity";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "admin";

    public String getJDBC_URL(){
        return JDBC_URL;
    }

    public String getUSERNAME(){
        return USERNAME;
    }

    public String getPASSWORD(){
        return PASSWORD;
    }
}
