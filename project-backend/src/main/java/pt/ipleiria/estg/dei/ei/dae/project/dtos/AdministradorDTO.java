package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class AdministradorDTO implements Serializable {

    private String email;
    private String name;
    private String password;

    public AdministradorDTO() {

    }

    public AdministradorDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /*Getters*/
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    /*Setters*/
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
