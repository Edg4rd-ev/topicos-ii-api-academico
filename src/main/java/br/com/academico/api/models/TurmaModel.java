package br.com.academico.api.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TURMAS")
public class TurmaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "dataInicio", nullable = false)
  private Date dataInicio;
  @Column(name = "dataFim", nullable = true)
  private Date dataFim;
  @ManyToOne
  @JoinColumn(name = "idProfessor", nullable = false)
  private ProfessorModel professor;
  @ManyToOne
  @JoinColumn(name = "idDisciplina", nullable = false)
  private DisciplinaModel disciplina;
  @OneToMany(mappedBy = "turma")
  private List<MatriculaModel> matriculas;
}
