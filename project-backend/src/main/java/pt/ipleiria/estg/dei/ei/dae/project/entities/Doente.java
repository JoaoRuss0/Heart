package pt.ipleiria.estg.dei.ei.dae.project.entities;

import org.jboss.resteasy.spi.touri.MappedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Entity
@Table(name = "DOENTES")
@NamedQueries({
        @NamedQuery(
            name = "getAllDoentes",
            query = "SELECT d FROM Doente d ORDER BY d.name"
        )
})
public class Doente extends User {

    @NotNull
    private int idade;

    @NotNull
    private double peso;

    @NotNull
    private double altura;

    @OneToMany(mappedBy = "doente", cascade = CascadeType.REMOVE)
    private ArrayList<Prescricao> prescricoes;

    public Doente() {
        this.prescricoes = new ArrayList<>();
    }

    public Doente(String name, String email, String password, int idade, double peso, double altura) {
        super(name, email, password);
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.prescricoes = new ArrayList<>();
    }

    public Prescricao adicionarPrescricao(Prescricao prescricao){
        if(!prescricoes.contains(prescricao)){
            prescricoes.add(prescricao);
            return prescricao;
        }

        return null;
    }

    /*Getters*/
    public int getIdade() {
        return idade;
    }
    public double getPeso() {
        return peso;
    }
    public double getAltura() {
        return altura;
    }

    public ArrayList<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    /*Setters*/
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setPrescricoes(ArrayList<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }
}
