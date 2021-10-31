package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    DoenteBean doenteBean;

    @EJB
    DadoBiomedicoBean dadoBiomedicoBean;

    @EJB
    AdministradorBean administradorBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try{
            doenteBean.create("Joao", "j.j@j.j", "1234", 21, 200.99, 4.0);
            doenteBean.create("Rafa", "r.r@r.r", "1234", 21, 400.01, 10.0);
            doenteBean.create("Pedro Gaymer", "p.p@p.p", "1234", 21, 30.0, 1.5);

            administradorBean.create("A", "a.a@a.a", "1234");

            dadoBiomedicoBean.create("Height", 272.0, 30.0, "cm");
            dadoBiomedicoBean.create("Weight", 500.0, 1.0, "kg");
            dadoBiomedicoBean.create("Age", 140.0, 0.0, "years");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}