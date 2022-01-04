package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.*;

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

    public Administrador create(String name, String email, String password) throws MyConstraintViolationException, MyEntityExistsException, MyPasswordTooShortException {
        if(userBean.find(email) != null)
        {
            throw new MyEntityExistsException("User with email = '" + email + "' already exists.");
        }

        if(password.length() < 8)
        {
            throw new MyPasswordTooShortException("Password has to be at least 8 in length.");
        }

        Administrador administrador;

        try {
            administrador = new Administrador(name, email, password);
            entityManager.persist(administrador);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return administrador;
    }

    public Administrador update(String name, String email, String currentpassword, String newpassword) throws MyConstraintViolationException, MyEntityNotFoundException, MyPasswordTooShortException, MyIncorrectPasswordException {
        Administrador administrador = findOrFail(email);

        if(currentpassword != null && newpassword != null && (currentpassword.length() < 8 || newpassword.length() < 8))
        {
            throw new MyPasswordTooShortException("New or current password have to be at least 8 in length.");
        }

        try {
            administrador.setName(name);

            if(currentpassword != null && newpassword != null){
                if(User.hashPassword(currentpassword).equals(administrador.getPassword())) {
                    administrador.setPassword(User.hashPassword(newpassword));
                }else{
                    throw new MyIncorrectPasswordException("Current password is different from the provided current password.");
                }
            }

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
