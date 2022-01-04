package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

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
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator="PRESCRICAO_SEQ")
    private int id;

    @NotNull
    private String comentario;

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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PROFISSIONAL")
    private ProfissionalDeSaude profissionalDeSaude;

    @NotNull
    @OneToOne
    @JoinColumn(name = "OBSERVACAO_ID")
    private Observacao observacao;

    public Prescricao() {
    }

    public Prescricao(String comentario, GregorianCalendar dataInicio, GregorianCalendar dataFinal, Prescricao.tipoPrescricao tipoPrescricao, Doente doente, ProfissionalDeSaude profissionalDeSaude, Observacao observacao) {
        this.comentario = comentario;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.tipoPrescricao = tipoPrescricao;
        this.doente = doente;
        this.profissionalDeSaude = profissionalDeSaude;
        this.observacao = observacao;
    }

    /*Getters*/
    public int getId() {
        return id;
    }
    public String getComentario() {
        return comentario;
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
    public Doente getDoente() {
        return doente;
    }
    public ProfissionalDeSaude getProfissionalDeSaude() {
        return profissionalDeSaude;
    }
    public Observacao getObservacao() {
        return observacao;
    }

    /*Setters*/
    public void setId(int id) {
        this.id = id;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
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
    public void setDoente(Doente doente) {
        this.doente = doente;
    }
    public void setProfissionalDeSaude(ProfissionalDeSaude profissionalDeSaude) {
        this.profissionalDeSaude = profissionalDeSaude;
    }
    public void setObservacao(Observacao observacao) {
        this.observacao = observacao;
    }
}
