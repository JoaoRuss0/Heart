package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public Doente() {
    }

    public Doente(String name, String email, String password, int idade, double peso, double altura) {
        super(name, email, password);
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
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
}
