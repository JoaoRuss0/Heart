package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String email;
    private String name;
    private String password;
    private String tipo;

    public UserDTO() {

    }

    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDTO(String name, String email, String password, String tipo) {
        this.tipo = tipo;
        this.name = name;
        this.email = email;
        this.password = password;
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
    public String getTipo() {
        return tipo;
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
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
