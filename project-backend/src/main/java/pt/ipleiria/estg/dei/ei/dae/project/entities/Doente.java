package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyPasswordTooShortException;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "DOENTES")
@NamedQueries({
        @NamedQuery(
            name = "getAllDoentes",
            query = "SELECT d FROM Doente d ORDER BY d.name"
        )
})
public class Doente extends User {

    @OneToMany(mappedBy = "doente", cascade = CascadeType.REMOVE)
    private ArrayList<Prescricao> prescricoes;

    @OneToMany(mappedBy = "doente", cascade = CascadeType.REMOVE)
    private ArrayList<Observacao> observacoes;

    public Doente() {
        this.prescricoes = new ArrayList<>();
        this.observacoes = new ArrayList<>();
    }

    public Doente(String name, String email, String password) throws MyPasswordTooShortException {
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
    public Observacao removerObservacao(Observacao observacao){
        if(observacoes.contains(observacao)){
            observacoes.remove(observacao);
            return observacao;
        }

        return null;
    }


    public Observacao adicionarObservacao(Observacao observacao){
        System.out.println(observacoes);
        System.out.println(observacao.getProfissionalDeSaude());
        if(!observacoes.contains(observacao)){
            observacoes.add(observacao);
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
    public void setPrescricoes(ArrayList<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }
    public void setObservacoes(ArrayList<Observacao> observacoes) {
        this.observacoes = observacoes;
    }
}
