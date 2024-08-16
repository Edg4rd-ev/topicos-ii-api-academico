package br.com.academico.api.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ALUNOS")
public class AlunoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "nome", length = 255, nullable = false)
  private String nome;
  @Column(name = "cpf", length = 14, nullable = false, unique = true)
  private String cpf;
  @Column(name = "dataNascimento", nullable = false)
  private Date dataNascimento;
  @Column(name = "email", length = 255, nullable = false, unique = true)
  private String email;
  @Column(name = "celular", length = 14, nullable = false)
  private String celular;
  @Column(name = "matricula", length = 13, nullable = false, unique = true)
  private String matricula;
  @Column(name = "apelido", length = 255, nullable = true)
  private String apelido;
  @OneToMany(mappedBy = "aluno")
  private List<MatriculaModel> matriculas;
}
