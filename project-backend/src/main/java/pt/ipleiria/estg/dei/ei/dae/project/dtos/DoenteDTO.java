package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DoenteDTO implements Serializable {

    private String name;
    private String email;
    private int idade;
    private double peso;
    private double altura;
    private List<PrescricaoDTO> prescricoes;
    private String password;

    public DoenteDTO() {
        prescricoes = new ArrayList<>();
    }

    public DoenteDTO(String name, String email, int idade, double peso, double altura, List<PrescricaoDTO> prescricoes) {
        this.name = name;
        this.email = email;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.prescricoes=prescricoes;
    }

    public DoenteDTO(String name, String email, int idade, double peso, double altura, String password) {
        this.name = name;
        this.email = email;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.prescricoes = new ArrayList<>();
        this.password = password;
    }

    /*Getters*/
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
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
    public String getPassword() {
        return password;
    }
    public List<PrescricaoDTO> getPrescricoes() {
        return prescricoes;
    }



    /*Setters*/
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public void setPrescricoes(List<PrescricaoDTO> prescricoes) {
        this.prescricoes = prescricoes;
    }
    public void setPassword(String Password) {
        this.password = password;
    }
}
