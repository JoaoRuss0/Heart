package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "Prescricoes")
@NamedQueries({
        @NamedQuery(
                name = "getAllPrescricoes",
                query = "SELECT p FROM Prescricao p ORDER BY p.id"
        )
})
@Entity
public class Prescricao {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    private String causa;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PRESCRICOES")
    private Doente doente;

    public Prescricao(String causa, Doente doente) {
        this.causa = causa;
        this.doente = doente;
    }

    public Prescricao() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public Doente getDoente() {
        return doente;
    }

    public void setDoente(Doente doente) {
        this.doente = doente;
    }
}
