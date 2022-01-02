package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyValueNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyValueOutOfBoundsException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless
public class ObservacaoBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    DoenteBean doenteBean;

    @EJB
    ProfissionalDeSaudeBean profissionalDeSaudeBean;

    @EJB
    DadoBiomedicoBean dadoBiomedicoBean;

    public Observacao create(String doenteEmail, String profissionalDeSaudeEmail, String nomeDadoBiomedico, String data, int valorQuantitativo, String valorQualificativo) throws MyEntityNotFoundException, ParseException, MyValueNotFoundException, MyValueOutOfBoundsException {

        Doente doente = doenteBean.findOrFail(doenteEmail);
        ProfissionalDeSaude profissionalDeSaude = profissionalDeSaudeBean.findOrFail(profissionalDeSaudeEmail);
        DadoBiomedico dadoBiomedico = dadoBiomedicoBean.findOrFail(nomeDadoBiomedico);

        if(!dadoBiomedico.getQualificadores().contains(valorQualificativo)){
            throw new MyValueNotFoundException(valorQualificativo + " not found!");
        }
        if(valorQuantitativo>dadoBiomedico.getMaximo() || valorQuantitativo < dadoBiomedico.getMinimo()){
            throw new MyValueOutOfBoundsException(valorQuantitativo + " must be lower than " + dadoBiomedico.getMaximo() + " and higher than "+ dadoBiomedico.getMinimo());
        }

        Observacao observacao = new Observacao(doente, profissionalDeSaude, nomeDadoBiomedico, stringToGregorian(data), valorQuantitativo, valorQualificativo);

        entityManager.persist(observacao);
        doente.adicionarObservacao(observacao);
        profissionalDeSaude.adicionarObservacao(observacao);

        return observacao;
    }


    public void delete(int id) throws Exception {
        Observacao observacao = this.findOrFail(id);


        entityManager.remove(observacao);
    }

    public Observacao findOrFail(int id) throws MyEntityNotFoundException {
        Observacao observacao = find(id);

        if(observacao == null){
            throw new MyEntityNotFoundException("Prescricao with id = '" + id + "' not found.");
        }

        return observacao;
    }

    public List<Observacao> getAll() {
        return entityManager.createNamedQuery("getAllObservacoes", Observacao.class).getResultList();
    }

    public Observacao find(int id) {
        return entityManager.find(Observacao.class, id);
    }





    private GregorianCalendar stringToGregorian(String data) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = df.parse(data);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return (GregorianCalendar) cal;
    }
}
