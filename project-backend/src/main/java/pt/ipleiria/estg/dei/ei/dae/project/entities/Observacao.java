package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

@Entity
@Table(name = "Observacoes")
@NamedQueries({
        @NamedQuery(
                name = "getAllObservacoes",
                query = "SELECT o FROM Observacao o ORDER BY o.id"
        )
})
public class Observacao {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "DOENTE")
    private Doente doente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PROFISSIONAL")
    private ProfissionalDeSaude profissionalDeSaude;

    @NotNull
    private String nomeDadoBiomedico;

    @NotNull
    private GregorianCalendar data;

    @NotNull
    private int valorQuantitativo;

    @NotNull
    private String valorQualitativo;

    public Observacao() {

    }

    public Observacao(Doente doente, ProfissionalDeSaude profissionalDeSaude, String nomeDadoBiomedico, GregorianCalendar data, int valorQuantitativo, String valorQualitativo) {
        this.doente = doente;
        this.profissionalDeSaude = profissionalDeSaude;
        this.nomeDadoBiomedico = nomeDadoBiomedico;
        this.data = data;
        this.valorQuantitativo = valorQuantitativo;
        this.valorQualitativo = valorQualitativo;
    }

    /*Getters*/
    public int getId() {
        return id;
    }
    public Doente getDoente() {
        return doente;
    }
    public ProfissionalDeSaude getProfissionalDeSaude() {
        return profissionalDeSaude;
    }
    public String getNomeDadoBiomedico() {
        return nomeDadoBiomedico;
    }
    public GregorianCalendar getData() {
        return data;
    }
    public int getValorQuantitativo() {
        return valorQuantitativo;
    }
    public String getValorQualitativo() {
        return valorQualitativo;
    }


    /*Setters*/
    public void setId(int id) {
        this.id = id;
    }
    public void setDoente(Doente doente) {
        this.doente = doente;
    }
    public void setProfissionalDeSaude(ProfissionalDeSaude profissional) {
        this.profissionalDeSaude = profissional;
    }
    public void setNomeDadoBiomedico(String nomeDadoBiomedico) {
        this.nomeDadoBiomedico = nomeDadoBiomedico;
    }
    public void setData(GregorianCalendar data) {
        this.data = data;
    }
    public void setValorQuantitativo(int valorQuantitativo) {
        this.valorQuantitativo = valorQuantitativo;
    }
    public void setValorQualitativo(String valorQualitativo) {
        this.valorQualitativo = valorQualitativo;
    }
}
