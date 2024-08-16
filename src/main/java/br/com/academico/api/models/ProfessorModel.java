package br.com.academico.api.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PROFESSORES")
public class ProfessorModel {
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
  @OneToMany(mappedBy = "professor")
  private List<TurmaModel> turmas;
}
