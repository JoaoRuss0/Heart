package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
@Entity
@Table(name = "Prescricoes")
@NamedQueries({
        @NamedQuery(
                name = "getAllPrescricoes",
                query = "SELECT p FROM Prescricao p ORDER BY p.id"
        )
})
public class Prescricao {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    private String causa;

    @NotNull
    private GregorianCalendar dataInicio;

    @NotNull
    private GregorianCalendar dataFinal;

    @NotNull
    private tipoPrescricao tipoPrescricao;

    public enum tipoPrescricao{
        prescricaoExercicioFisico,
        prescricaoMedica,
        prescricaoNutricao
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PRESCRICOES")
    private Doente doente;

    public Prescricao(String causa, GregorianCalendar dataInicio, GregorianCalendar dataFinal, Prescricao.tipoPrescricao tipoPrescricao, Doente doente) {
        this.causa = causa;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.tipoPrescricao = tipoPrescricao;
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

    public GregorianCalendar getDataInicio() {
        return dataInicio;
    }

    public GregorianCalendar getDataFinal() {
        return dataFinal;
    }

    public Prescricao.tipoPrescricao getTipoPrescricao() {
        return tipoPrescricao;
    }

    public void setDataInicio(GregorianCalendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFinal(GregorianCalendar dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setTipoPrescricao(Prescricao.tipoPrescricao tipoPrescricao) {
        this.tipoPrescricao = tipoPrescricao;
    }
}
