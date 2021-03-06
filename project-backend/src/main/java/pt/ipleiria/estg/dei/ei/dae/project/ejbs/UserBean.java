package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyLoginFailedException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    AdministradorBean administradorBean;

    public List<User> getAll() {
        return entityManager.createNamedQuery("getAllUsers", User.class).getResultList();
    }

    public User authenticate(final String email, final String password) throws MyLoginFailedException {
        User user = entityManager.find(User.class, email);

        if (user != null && user.getPassword().equals(User.hashPassword(password))) {
            return user;
        }

        throw new MyLoginFailedException("Failed logging in with email '" + email + "': unknown email or wrong password");
    }

    public User findOrFail(String email) throws MyEntityNotFoundException {
        User user = find(email);

        if(user == null) {
            throw new MyEntityNotFoundException("User with email='" + email +"' was not found.");
        }

        return user;
    }

    public User find(String email) {
        return entityManager.find(User.class, email);
    }
}
