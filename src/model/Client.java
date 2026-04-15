package model;

import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idClient;
    private String nameClient;
    private String surnameClient;
    private int ageClient;
    private int phoneClient;  
    private String emailClient;

    public Client() {
        super();
    }

    public Client(String idClient, String nameClient, String surnameClient, int ageClient, int phoneClient, String emailClient) {
        this.idClient = idClient;
        this.nameClient = nameClient;
        this.surnameClient = surnameClient;
        this.ageClient = ageClient;
        this.phoneClient = phoneClient;
        this.emailClient = emailClient;
    }

    // Getters y Setters
    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getSurnameClient() {
        return surnameClient;
    }

    public void setSurnameClient(String surnameClient) {
        this.surnameClient = surnameClient;
    }

    public int getAgeClient() {
        return ageClient;
    }

    public void setAgeClient(int ageClient) {
        this.ageClient = ageClient;
    }

    public int getPhoneClient() {
        return phoneClient;
    }

    public void setPhoneClient(int phoneClient) {
        this.phoneClient = phoneClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
}