package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

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

    public Doente create(String name, String email, String password, int idade, double peso, double altura) throws MyEntityExistsException, MyConstraintViolationException {
        if(userBean.find(email) != null) {
            throw new MyEntityExistsException("Doente with email = '" + email + "' already exists.");
        }

        Doente doente;

        try{
            doente = new Doente(name, email, password, idade, peso, altura);
            entityManager.persist(doente);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return doente;
    }

    public Doente update(String name, String email, int idade, double peso, double altura) throws MyConstraintViolationException, MyEntityNotFoundException {
        Doente doente = findOrFail(email);

        try{
            doente.setName(name);
            doente.setIdade(idade);
            doente.setPeso(peso);
            doente.setAltura(altura);

            entityManager.merge(doente);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return doente;
    }

    public String delete(String email) throws MyEntityNotFoundException {
        Doente doente = findOrFail(email);
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

