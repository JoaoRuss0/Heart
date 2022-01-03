package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
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

    @EJB
    PrescricaoBean prescricaoBean;

    @EJB
    DadoBiomedicoBean dadoBiomedicoBean;

    @EJB
    ObservacaoBean observacaoBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try{
            doenteBean.create("Joao", "j.j@j.j", "1234", 21, 200.99, 4.0);
            doenteBean.create("Rafa", "r.r@r.r", "1234", 21, 400.01, 10.0);
            doenteBean.create("Pedro Gamer", "p.p@p.p", "1234", 21, 30.0, 1.5);

            profissionalDeSaudeBean.create("Fernando", "f.f@f.f", "1234");
            profissionalDeSaudeBean.create("Tiago", "t.t@t.t", "1234");
            profissionalDeSaudeBean.create("Marcia", "m.m@m.m", "1234");

            administradorBean.create("A", "a.a@a.a", "1234");
            administradorBean.create("B", "b.b@b.b", "1234");
            administradorBean.create("C", "c.c@c.c", "1234");

            dadoBiomedicoBean.create("Altura", "Altura do doente", 272.0, 30.0, "cm", Arrays.asList("Baixo","Alto","Médio"));
            dadoBiomedicoBean.create("Peso", "Peso do doente", 500.0, 1.0, "kg", Arrays.asList("Gordo","Magro","Obeso","Normal"));
            dadoBiomedicoBean.create("Idade", "Idade do doente", 140.0, 0.0, "years", Arrays.asList("Bebé","Criança","Adulto","Idoso"));



            observacaoBean.create("p.p@p.p","f.f@f.f", "Altura", "2021-12-22", 180,"Alto");
            observacaoBean.create("p.p@p.p","f.f@f.f", "Idade", "2021-12-2", 10,"Bebé");
            observacaoBean.create("r.r@r.r","t.t@t.t", "Peso", "2021-2-2", 80,"Magro");

            prescricaoBean.create("Autismo de Pedro", "p.p@p.p","2021-12-21","2021-12-22",Prescricao.tipoPrescricao.prescricaoMedica, "t.t@t.t", 2);


        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}