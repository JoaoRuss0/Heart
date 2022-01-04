package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.DadoBiomedico;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class DadoBiomedicoBean {

    @PersistenceContext
    EntityManager entityManager;

    public DadoBiomedico create(String nome, String descricao, Double maximo, Double minimo, String unidadeMedida, List<String> qualificadores) throws MyConstraintViolationException, MyEntityExistsException, MyMaximumMinumumException, MyQualificadoresEmpty {
        if(find(nome) != null) {
            throw new MyEntityExistsException("Found Dado Biomedico with nome = '" + nome + "'.");
        }

        if(qualificadores.isEmpty())
        {
            throw new MyQualificadoresEmpty("At least one qualificador is needed for any dado biomédico.");
        }

        if(maximo < minimo) {
            throw new MyMaximumMinumumException("Maximum (" + maximo +") is lower than minimum (" + minimo + ").");
        }

        DadoBiomedico dadoBiomedico;

        try {
            dadoBiomedico = new DadoBiomedico(nome, descricao, maximo, minimo, unidadeMedida, qualificadores);
            entityManager.persist(dadoBiomedico);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return dadoBiomedico;
    }

    public DadoBiomedico update(String nome, String descricao, Double maximo, Double minimo, String unidadeMedida, List<String> qualificadores) throws MyEntityNotFoundException, MyConstraintViolationException, MyMaximumMinumumException, MyQualificadoresEmpty {
        DadoBiomedico dado = findOrFail(nome);

        if(qualificadores.isEmpty())
        {
            throw new MyQualificadoresEmpty("At least one qualificador is needed for any dado biomédico.");
        }

        if(maximo < minimo) {
            throw new MyMaximumMinumumException("Maximum (" + maximo +") is lower than minimum (" + minimo + ").");
        }

        try {
            dado.setDescricao(descricao);
            dado.setMinimo(minimo);
            dado.setMaximo(maximo);
            dado.setUnidadeMedida(unidadeMedida);
            dado.setQualificadores(qualificadores);
            entityManager.merge(dado);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return dado;
    }

    public String delete(String nome) throws MyEntityNotFoundException {
        DadoBiomedico dado = findOrFail(nome);
        entityManager.remove(dado);

        return nome;
    }

    public List<DadoBiomedico> getAll() {
        return entityManager.createNamedQuery("getAllDadosBiomedicos", DadoBiomedico.class).getResultList();
    }

    public DadoBiomedico findOrFail(String nome) throws MyEntityNotFoundException {
        DadoBiomedico dadoBiomedico = find(nome);

        if(dadoBiomedico == null) {
            throw new MyEntityNotFoundException("Dado Biomedico '" + nome + "' was not found.");
        }

        return dadoBiomedico;
    }

    public DadoBiomedico find(String nome) {
        return entityManager.find(DadoBiomedico.class, nome);
    }
}
