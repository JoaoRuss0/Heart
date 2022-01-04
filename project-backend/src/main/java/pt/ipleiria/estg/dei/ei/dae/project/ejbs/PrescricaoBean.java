package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.ProfissionalDeSaude;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyParseException;

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
public class PrescricaoBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    DoenteBean doenteBean;

    @EJB
    ProfissionalDeSaudeBean profissionalDeSaudeBean;

    @EJB
    ObservacaoBean observacaoBean;

    public Prescricao create(String comentario, String doenteEmail, String dataInicio, String dataFinal, Prescricao.tipoPrescricao tipoPrescricao, String profissionalDeSaudeEmail, int observacaoID) throws MyConstraintViolationException, MyEntityNotFoundException, MyParseException {
        Doente doente = doenteBean.findOrFail(doenteEmail);
        ProfissionalDeSaude profissionalDeSaude = profissionalDeSaudeBean.findOrFail(profissionalDeSaudeEmail);
        Observacao observacao =observacaoBean.findOrFail(observacaoID);
        Prescricao prescricao;

        try {
            prescricao = new Prescricao(comentario, stringToGregorian(dataInicio), stringToGregorian(dataFinal), tipoPrescricao, doente, profissionalDeSaude, observacao);
            entityManager.persist(prescricao);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }catch(ParseException e){
            throw new MyParseException("Error parsing either Start or End Date.");
        }

        doente.adicionarPrescricao(prescricao);
        profissionalDeSaude.adicionarPrescricao(prescricao);
        observacao.setPrescricao(prescricao);

        return prescricao;
    }

    public Prescricao update(int id, String comentario, String dataInicio, String dataFinal, Prescricao.tipoPrescricao tipoPrescricao) throws MyParseException, MyEntityNotFoundException, MyConstraintViolationException {
        Prescricao prescricao = findOrFail(id);

        try {
            prescricao.setComentario(comentario);
            prescricao.setTipoPrescricao(tipoPrescricao);
            prescricao.setDataInicio(stringToGregorian(dataInicio));
            prescricao.setDataFinal(stringToGregorian(dataFinal));
            entityManager.merge(prescricao);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }catch(ParseException e) {
            throw new MyParseException("Error parsing either Start or End Date.");
        }

        return prescricao;
    }

    public void delete(int id) throws MyEntityNotFoundException {
        Prescricao prescricao = findOrFail(id);
        prescricao.getObservacao().setPrescricao(null);
        prescricao.getDoente().removerPrescricao(prescricao);
        prescricao.getProfissionalDeSaude().removerPrescricao(prescricao);

        entityManager.remove(prescricao);
    }

    public List<Prescricao> getAll() {
        return entityManager.createNamedQuery("getAllPrescricoes", Prescricao.class).getResultList();
    }

    public Prescricao findOrFail(int id) throws MyEntityNotFoundException {
        Prescricao prescricao = find(id);

        if(prescricao == null){
            throw new MyEntityNotFoundException("Prescricao with id = '" + id + "' not found.");
        }

        return prescricao;
    }

    public Prescricao find(int id) {
        return entityManager.find(Prescricao.class, id);
    }

    private GregorianCalendar stringToGregorian(String data) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = df.parse(data);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return (GregorianCalendar) cal;
    }
}
