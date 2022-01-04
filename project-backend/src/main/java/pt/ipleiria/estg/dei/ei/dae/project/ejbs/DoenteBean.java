package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class DoenteBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    UserBean userBean;

    @EJB
    PrescricaoBean prescricaoBean;

    public Doente create(String name, String email, String password) throws MyEntityExistsException, MyConstraintViolationException, MyPasswordTooShortException {
        if(userBean.find(email) != null) {
            throw new MyEntityExistsException("Doente with email = '" + email + "' already exists.");
        }

        if(password.length() < 8)
        {
            throw new MyPasswordTooShortException("Password has to be at least 8 in length.");
        }

        Doente doente;

        try{
            doente = new Doente(name, email, password);
            entityManager.persist(doente);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return doente;
    }

    public Doente update(String name, String email, String currentpassword, String newpassword) throws MyConstraintViolationException, MyEntityNotFoundException, MyPasswordTooShortException, MyIncorrectPasswordException {
        Doente doente = findOrFail(email);

        if(currentpassword != null && newpassword != null && (currentpassword.length() < 8 || newpassword.length() < 8))
        {
            throw new MyPasswordTooShortException("New or current password have to be at least 8 in length.");
        }

        try{
            doente.setName(name);

            if(currentpassword != null && newpassword != null){
                if(User.hashPassword(currentpassword).equals(doente.getPassword())) {
                    doente.setPassword(User.hashPassword(newpassword));
                }else{
                    throw new MyIncorrectPasswordException("Current password is different from the provided current password.");
                }
            }

            entityManager.merge(doente);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return doente;
    }

    public String delete(String email) throws MyEntityNotFoundException {
        Doente doente = findOrFail(email);

        for (Prescricao prescricao: doente.getPrescricoes()) {
            prescricaoBean.delete(prescricao.getId());
        }

        for (Observacao observacao: doente.getObservacoes()) {
            entityManager.remove(observacao);
        }

        entityManager.remove(doente);

        return email;
    }

    public List<Doente> getAll() {
        return entityManager.createNamedQuery("getAllDoentes", Doente.class).getResultList();
    }

    public Doente findOrFail(String email) throws MyEntityNotFoundException {
        Doente doente = find(email);

        if(doente == null){
            throw new MyEntityNotFoundException("Doente with email = '" + email + "' not found.");
        }

        return doente;
    }

    public Doente find(String email) {
        return entityManager.find(Doente.class, email);
    }
}

