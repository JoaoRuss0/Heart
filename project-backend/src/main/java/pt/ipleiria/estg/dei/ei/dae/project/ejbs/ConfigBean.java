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
            doenteBean.create("Joao", "j.j@j.j", "12341234");
            doenteBean.create("Rafa", "r.r@r.r", "12341234");
            doenteBean.create("Pedro Gamer", "p.p@p.p", "12341234");

            profissionalDeSaudeBean.create("Fernando", "f.f@f.f", "12341234");
            profissionalDeSaudeBean.create("Tiago", "t.t@t.t", "12341234");
            profissionalDeSaudeBean.create("Marcia", "m.m@m.m", "12341234");

            administradorBean.create("A", "a.a@a.a", "12341234");
            administradorBean.create("B", "b.b@b.b", "12341234");
            administradorBean.create("C", "c.c@c.c", "12341234");

            dadoBiomedicoBean.create("Altura", "Altura do doente", 272.0, 30.0, "cm", Arrays.asList("Baixo","Alto","Médio"));
            dadoBiomedicoBean.create("Peso", "Peso do doente", 500.0, 1.0, "kg", Arrays.asList("Gordo","Magro","Obeso","Normal"));
            dadoBiomedicoBean.create("Idade", "Idade do doente", 140.0, 0.0, "years", Arrays.asList("Bebé","Criança","Adulto","Idoso"));

            observacaoBean.create("p.p@p.p","t.t@t.t", "Altura", "2021-12-22", 180,"Alto");
            observacaoBean.create("r.r@r.r","f.f@f.f", "Idade", "2021-12-2", 10,"Bebé");
            observacaoBean.create("j.j@j.j","m.m@m.m", "Peso", "2021-2-2", 80,"Magro");

            prescricaoBean.create("A", "p.p@p.p","2021-12-21","2021-12-22",Prescricao.tipoPrescricao.prescricaoMedica, "t.t@t.t", 1);
            prescricaoBean.create("B", "r.r@r.r","2021-12-21","2021-12-22",Prescricao.tipoPrescricao.prescricaoMedica, "f.f@f.f", 2);
            prescricaoBean.create("C", "j.j@j.j","2021-12-21","2021-12-22",Prescricao.tipoPrescricao.prescricaoMedica, "m.m@m.m", 3);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}