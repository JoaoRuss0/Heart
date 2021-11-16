package pt.ipleiria.estg.dei.ei.dae.project.dtos;

public class UserDTO {

    private String email;
    private String name;
    private String password;

    public UserDTO() {

    }

    public UserDTO(String email, String password) {
        this.name = "";
        this.email = email;
        this.password = password;
    }

    public UserDTO(String name, String email, String password) {
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
}
