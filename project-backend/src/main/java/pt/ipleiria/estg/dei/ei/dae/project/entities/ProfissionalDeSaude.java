package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyPasswordTooShortException;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "PROFISSIONAISDESAUDE")
@NamedQueries({
        @NamedQuery(
                name = "getAllProfissionaisDeSaude",
                query = "SELECT p FROM ProfissionalDeSaude p ORDER BY p.name"
        )
})
public class ProfissionalDeSaude extends User{

    @OneToMany(mappedBy = "profissionalDeSaude", cascade = CascadeType.REMOVE)
    private ArrayList<Prescricao> prescricoes;

    @OneToMany(mappedBy = "profissionalDeSaude", cascade = CascadeType.REMOVE)
    private ArrayList<Observacao> observacoes;

    public ProfissionalDeSaude() {
        this.prescricoes = new ArrayList<>();
        this.observacoes = new ArrayList<>();
    }

    public ProfissionalDeSaude(String name, String email, String password) throws MyPasswordTooShortException {
        super(name, email, password);
        this.prescricoes = new ArrayList<>();
        this.observacoes = new ArrayList<>();
    }

    public Prescricao adicionarPrescricao(Prescricao prescricao){
        if(!prescricoes.contains(prescricao)){
            prescricoes.add(prescricao);
            return prescricao;
        }
        return null;
    }

    public Prescricao removerPrescricao(Prescricao prescricao){
        if(prescricoes.contains(prescricao)){
            prescricoes.remove(prescricao);
            return prescricao;
        }
        return null;
    }

    public Observacao adicionarObservacao(Observacao observacao){
        if(!observacoes.contains(observacao)){
            observacoes.add(observacao);
            return observacao;
        }
        return null;
    }

    public Observacao removerObservacao(Observacao observacao){
        if(observacoes.contains(observacao)){
            observacoes.remove(observacao);
            return observacao;
        }
        return null;
    }

    /*Getters*/
    public ArrayList<Prescricao> getPrescricoes() {
        return prescricoes;
    }
    public ArrayList<Observacao> getObservacoes() {
        return observacoes;
    }

    /*Setters*/
    public void setObservacoes(ArrayList<Observacao> observacoes) {
        this.observacoes = observacoes;
    }
    public void setPrescricoes(ArrayList<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }

}
