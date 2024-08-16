package br.com.academico.api.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DISCIPLINAS")
public class DisciplinaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "nome", length = 255, nullable = false, unique = true)
  private String nome;
  @Column(name = "creditos", nullable = false)
  private int creditos;
  @OneToMany(mappedBy = "disciplina")
  private List<TurmaModel> turmas;
}
