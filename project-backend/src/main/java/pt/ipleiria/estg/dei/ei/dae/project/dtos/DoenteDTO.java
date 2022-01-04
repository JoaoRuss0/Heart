package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class DoenteDTO implements Serializable {

    private String name;
    private String email;
    private String password;
    private String newpassword;
    private String currentpassword;

    public DoenteDTO() {

    }

    public DoenteDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public DoenteDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public DoenteDTO(String name, String email, String currentpassword, String newpassword) {
        this.name = name;
        this.email = email;
        this.currentpassword = currentpassword;
        this.newpassword = newpassword;
    }

    /*Getters*/
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getNewpassword() {
        return newpassword;
    }
    public String getCurrentpassword() {
        return currentpassword;
    }

    /*Setters*/
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }
}
