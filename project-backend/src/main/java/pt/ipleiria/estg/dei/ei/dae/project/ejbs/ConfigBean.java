package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.DadoBiomedico;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
import java.util.Collections;
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

            dadoBiomedicoBean.create("Altura", "Altura do doente", 272.0, 30.0, "cm", Arrays.asList("Baixo","Alto","Médio"));
            dadoBiomedicoBean.create("Peso", "Peso do doente", 500.0, 1.0, "kg", Arrays.asList("Gordo","Magro","Obeso","Normal"));
            dadoBiomedicoBean.create("Idade", "Idade do doente", 140.0, 0.0, "years", Arrays.asList("Bebé","Criança","Adulto","Idoso"));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}