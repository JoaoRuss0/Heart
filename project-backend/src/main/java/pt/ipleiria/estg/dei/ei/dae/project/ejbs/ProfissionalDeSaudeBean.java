package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.ProfissionalDeSaude;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class ProfissionalDeSaudeBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    UserBean userBean;

    public ProfissionalDeSaude create(String name, String email, String password) throws MyConstraintViolationException, MyEntityExistsException, MyPasswordTooShortException {
        if(userBean.find(email) != null)
        {
            throw new MyEntityExistsException("User with email = '" + email + "' already exists.");
        }

        if(password.length() < 8)
        {
            throw new MyPasswordTooShortException("Password has to be at least 8 in length.");
        }

        ProfissionalDeSaude profissionalDeSaude;

        try {
            profissionalDeSaude = new ProfissionalDeSaude(name, email, password);
            entityManager.persist(profissionalDeSaude);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return profissionalDeSaude;
    }

    public ProfissionalDeSaude update(String name, String email, String newpassword, String currentpassword) throws MyConstraintViolationException, MyEntityNotFoundException, MyPasswordTooShortException, MyIncorrectPasswordException {
        ProfissionalDeSaude profissionalDeSaude = findOrFail(email);

        if(currentpassword != null && newpassword != null && (currentpassword.length() < 8 || newpassword.length() < 8))
        {
            throw new MyPasswordTooShortException("New or current password have to be at least 8 in length.");
        }

        try {
            profissionalDeSaude.setName(name);

            if(currentpassword != null && newpassword != null){
                if(User.hashPassword(currentpassword).equals(profissionalDeSaude.getPassword())) {
                    profissionalDeSaude.setPassword(User.hashPassword(newpassword));
                }else{
                    throw new MyIncorrectPasswordException("Current password is different from the provided current password.");
                }
            }
            entityManager.merge(profissionalDeSaude);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return profissionalDeSaude;
    }

    public String delete(String email) throws MyEntityNotFoundException {
        ProfissionalDeSaude profissionalDeSaude = findOrFail(email);
        entityManager.remove(profissionalDeSaude);

        return email;
    }

    public ProfissionalDeSaude findOrFail(String email) throws MyEntityNotFoundException {
        ProfissionalDeSaude profissionalDeSaude = find(email);

        if(profissionalDeSaude == null){
            throw new MyEntityNotFoundException("Profissional de Saude with email = '" + email + "' not found.");
        }

        return profissionalDeSaude;
    }

    public ProfissionalDeSaude find(String email) {
        return entityManager.find(ProfissionalDeSaude.class, email);
    }
}
