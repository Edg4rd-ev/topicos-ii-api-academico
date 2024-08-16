package br.com.academico.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academico.api.models.AlunoModel;
import br.com.academico.api.models.MatriculaModel;
import br.com.academico.api.models.TurmaModel;

public interface MatriculaRepository extends JpaRepository<MatriculaModel, Integer> {
  boolean existsByAlunoAndTurma(AlunoModel aluno, TurmaModel turma);
}
