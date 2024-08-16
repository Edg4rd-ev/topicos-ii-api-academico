package br.com.academico.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academico.api.dtos.ProfessorDisciplinasAlunosDTO;
import br.com.academico.api.dtos.ProfessorDisciplinasDTO;
import br.com.academico.api.dtos.ProfessorDto;
import br.com.academico.api.models.ProfessorModel;
import br.com.academico.api.services.ProfessorService;

import br.com.academico.api.exceptions.ConstraintException;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/professor")
@Controller
public class ProfessorController {
  @Autowired
  private ProfessorService professorService;

  @GetMapping("/list")
  public ResponseEntity<List<ProfessorDto>> getProfessors() {
    List<ProfessorDto> dtoProfessorList = professorService.GetProfessores();
    return ResponseEntity.ok(dtoProfessorList);
  }

  @GetMapping("/{matricula}")
  public ResponseEntity<ProfessorDto> getProfessorByMatricula(@PathVariable String matricula) {
    ProfessorDto professorDto = professorService.GetProfessorByMatricula(matricula);
    return ResponseEntity.ok(professorDto);
  }

  @GetMapping("/{professorId}/disciplinas")
  public ResponseEntity<ProfessorDisciplinasDTO> getProfessorDisciplinas(@PathVariable int professorId) {
    ProfessorDisciplinasDTO professorDisciplinasDTO = professorService.GetProfessorDisciplinas(professorId);
    return ResponseEntity.ok(professorDisciplinasDTO);
  }

  @GetMapping("/{professorId}/disciplinas-alunos")
  public ResponseEntity<ProfessorDisciplinasAlunosDTO> getProfessorDisciplinasAlunos(@PathVariable int professorId) {
    ProfessorDisciplinasAlunosDTO professorDisciplinasAlunosDTO = professorService
        .GetProfessorDisciplinasAlunos(professorId);
    return ResponseEntity.ok(professorDisciplinasAlunosDTO);
  }

  @PostMapping("/register")
  public ResponseEntity<ProfessorDto> postProfessor(@Valid @RequestBody ProfessorModel professorModel,
      BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    ProfessorDto professorDto = professorService.PostProfessor(professorModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(professorDto);
  }

  @PutMapping("update/{matricula}")
  public ResponseEntity<ProfessorDto> putProfessor(@PathVariable String matricula,
      @Valid @RequestBody ProfessorModel professorModel,
      BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    ProfessorDto professorDto = professorService.UpdateProfessor(professorModel, matricula);
    return ResponseEntity.ok(professorDto);
  }

  @DeleteMapping("delete/{matricula}")
  public ResponseEntity<Void> delete(@PathVariable String matricula) {
    professorService.DeleteByMatricula(matricula);
    return ResponseEntity.noContent().build();
  }
}
