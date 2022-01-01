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
    AdministradorBean administradorBean;

    @EJB
    ProfissionalDeSaudeBean profissionalDeSaudeBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try{
            doenteBean.create("Joao", "j.j@j.j", "1234", 21, 200.99, 4.0);
            doenteBean.create("Rafa", "r.r@r.r", "1234", 21, 400.01, 10.0);
            doenteBean.create("Pedro Gamer", "p.p@p.p", "1234", 21, 30.0, 1.5);

            administradorBean.create("A", "a.a@a.a", "1234");
            administradorBean.create("B", "b.b@b.b", "1234");
            administradorBean.create("C", "c.c@c.c", "1234");

            profissionalDeSaudeBean.create("Fernando", "f.f@f.f", "1234");
            profissionalDeSaudeBean.create("Tiago", "t.t@t.t", "1234");
            profissionalDeSaudeBean.create("Marcia", "m.m@m.m", "1234");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}