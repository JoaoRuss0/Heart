package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ObservacaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
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

    public Observacao create(String doenteEmail, String profissionalDeSaudeEmail, String nomeDadoBiomedico, String data, int valorQuantitativo, String valorQualitativo) throws MyEntityNotFoundException, MyParseException, MyValueNotFoundException, MyValueOutOfBoundsException, MyConstraintViolationException {
        Doente doente = doenteBean.findOrFail(doenteEmail);
        ProfissionalDeSaude profissionalDeSaude = profissionalDeSaudeBean.findOrFail(profissionalDeSaudeEmail);
        DadoBiomedico dadoBiomedico = dadoBiomedicoBean.findOrFail(nomeDadoBiomedico);
        Observacao observacao;

        if(!dadoBiomedico.getQualificadores().contains(valorQualitativo)){
            throw new MyValueNotFoundException(valorQualitativo + " not found!");
        }
        if(valorQuantitativo>dadoBiomedico.getMaximo() || valorQuantitativo < dadoBiomedico.getMinimo()){
            throw new MyValueOutOfBoundsException(valorQuantitativo + " must be lower than " + dadoBiomedico.getMaximo() + " and higher than "+ dadoBiomedico.getMinimo());
        }

        try {
            observacao = new Observacao(doente, profissionalDeSaude, nomeDadoBiomedico, stringToGregorian(data), valorQuantitativo, valorQualitativo);
            entityManager.persist(observacao);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }catch(ParseException e){
            throw new MyParseException("Error parsing either Start or End Date.");
        }

        doente.adicionarObservacao(observacao);
        profissionalDeSaude.adicionarObservacao(observacao);

        return observacao;
    }

    public Observacao updateObservacao(int id, ObservacaoDTO observacaoDTO) throws MyEntityNotFoundException, MyValueOutOfBoundsException, MyValueNotFoundException, MyParseException, MyConstraintViolationException {
        Observacao observacao = findOrFail(id);
        DadoBiomedico dado = dadoBiomedicoBean.findOrFail(observacaoDTO.getNomeDadoBiomedico());

        if(!dado.getQualificadores().contains(observacaoDTO.getValorQualitativo())){
            throw new MyValueNotFoundException(observacaoDTO.getValorQualitativo() + " not found!");
        }
        if(observacaoDTO.getValorQuantitativo()>dado.getMaximo() || observacaoDTO.getValorQuantitativo() < dado.getMinimo()){
            throw new MyValueOutOfBoundsException(observacaoDTO.getValorQuantitativo() + " must be lower than " + dado.getMaximo() + " and higher than "+ dado.getMinimo());
        }

        Doente doente = doenteBean.findOrFail(observacaoDTO.getDoenteEmail());
        ProfissionalDeSaude profissionalDeSaude = profissionalDeSaudeBean.findOrFail(observacaoDTO.getProfissionalDeSaudeEmail());

        try {
            observacao.setDoente(doente);
            observacao.setProfissionalDeSaude(profissionalDeSaude);
            observacao.setNomeDadoBiomedico(dado.getNome());
            observacao.setData(stringToGregorian(observacaoDTO.getData()));
            observacao.setValorQuantitativo(observacaoDTO.getValorQuantitativo());
            observacao.setValorQualitativo(observacaoDTO.getValorQualitativo());
            entityManager.merge(observacao);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }catch (ParseException e){
            throw new MyParseException("Error parsing either Start or End Date.");
        }

        return observacao;
    }

    public void delete(int id) throws MyEntityNotFoundException {
        Observacao observacao = findOrFail(id);
        observacao.getDoente().removerObservacao(observacao);
        observacao.getProfissionalDeSaude().removerObservacao(observacao);
        entityManager.remove(observacao);
    }

    public List<Observacao> getAll() {
        return entityManager.createNamedQuery("getAllObservacoes", Observacao.class).getResultList();
    }

    public Observacao findOrFail(int id) throws MyEntityNotFoundException {
        Observacao observacao = find(id);

        if(observacao == null){
            throw new MyEntityNotFoundException("Observacao with id = '" + id + "' not found.");
        }

        return observacao;
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
