package br.com.academico.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.academico.api.models.AlunoModel;
import br.com.academico.api.models.DisciplinaModel;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<DisciplinaModel, Integer> {
  Optional<DisciplinaModel> findByNome(String nome);

  Optional<DisciplinaModel> findByCreditos(int creditos);

  @Query("SELECT m.aluno FROM MatriculaModel m WHERE m.turma.disciplina.id = :disciplinaId")
  List<AlunoModel> findAlunosByDisciplinaId(@Param("disciplinaId") int disciplinaId);
}
