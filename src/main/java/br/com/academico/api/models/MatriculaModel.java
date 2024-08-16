package br.com.academico.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "MATRICULAS")
public class MatriculaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "idAluno", nullable = false)
  private AlunoModel aluno;
  @ManyToOne
  @JoinColumn(name = "idTurma", nullable = false)
  private TurmaModel turma;
}
