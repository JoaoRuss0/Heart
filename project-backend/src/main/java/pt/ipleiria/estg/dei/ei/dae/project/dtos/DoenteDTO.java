package pt.ipleiria.estg.dei.ei.dae.project.dtos;

public class DoenteDTO {

    private String name;

    private String email;

    private String password;

    private int idade;

    private double peso;

    private double altura;

    public DoenteDTO() {

    }

    public DoenteDTO(String name, String email, String password, int idade, double peso, double altura) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
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
    public int getIdade() {
        return idade;
    }
    public double getPeso() {
        return peso;
    }
    public double getAltura() {
        return altura;
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
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }
}
