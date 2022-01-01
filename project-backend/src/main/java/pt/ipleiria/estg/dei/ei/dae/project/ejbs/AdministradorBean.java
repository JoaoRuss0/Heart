package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class AdministradorBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    UserBean userBean;

    public Administrador create(String name, String email, String password) throws MyConstraintViolationException, MyEntityExistsException {
        if(userBean.find(email) != null)
        {
            throw new MyEntityExistsException("User with email = '" + email + "' already exists.");
        }

        Administrador administrador ;

        try {
            administrador = new Administrador(name, email, password);
            entityManager.persist(administrador);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return administrador;
    }

    public Administrador update(String name, String email) throws MyConstraintViolationException, MyEntityNotFoundException {
        Administrador administrador = findOrFail(email);

        try {
            administrador.setName(name);

            entityManager.merge(administrador);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return administrador;
    }

    public String delete(String email) throws MyEntityNotFoundException {
        Administrador administrador = findOrFail(email);
        entityManager.remove(administrador);

        return email;
    }

    private Administrador findOrFail(String email) throws MyEntityNotFoundException {
        Administrador administrador = find(email);

        if(administrador == null){
            throw new MyEntityNotFoundException("Administrador with email = '" + email + "' not found.");
        }

        return administrador;
    }

    public Administrador find(String email) {
        return entityManager.find(Administrador.class, email);
    }
}
