package br.com.academico.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academico.api.dtos.DisciplinaComAlunosDTO;
import br.com.academico.api.dtos.DisciplinaDto;
import br.com.academico.api.exceptions.ConstraintException;
import br.com.academico.api.models.DisciplinaModel;
import br.com.academico.api.services.DisciplinaService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/disciplina")
@Controller
public class DisciplinaController {

  @Autowired
  private DisciplinaService disciplinaService;

  @GetMapping("/list")
  public ResponseEntity<List<DisciplinaDto>> getDisciplinas() {
    List<DisciplinaDto> dtoDisciplinaList = disciplinaService.GetDisciplinas();
    return ResponseEntity.ok(dtoDisciplinaList);
  }

  @GetMapping("/{nome}")
  public ResponseEntity<DisciplinaDto> getDisciplinaById(@PathVariable String nome) {
    DisciplinaDto disciplinaDto = disciplinaService.getDisciplinaByName(nome);
    return ResponseEntity.ok(disciplinaDto);
  }

  @GetMapping("/{disciplinaId}/alunos")
  public ResponseEntity<DisciplinaComAlunosDTO> getDisciplinaComAlunos(@PathVariable int disciplinaId) {
    DisciplinaComAlunosDTO disciplinaComAlunosDTO = disciplinaService.getDisciplinaComAlunos(disciplinaId);
    return ResponseEntity.ok(disciplinaComAlunosDTO);
  }

  @PostMapping("/register")
  public ResponseEntity<DisciplinaDto> postDisciplina(@Valid @RequestBody DisciplinaModel disciplinaModel,
      BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    DisciplinaDto disciplinaDto = disciplinaService.PostDisciplina(disciplinaModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaDto);
  }

  @PutMapping("update/{disciplinaId}")
  public ResponseEntity<DisciplinaDto> putDisciplina(@PathVariable int disciplinaId,
      @Valid @RequestBody DisciplinaModel disciplinaModel,
      BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    DisciplinaDto disciplinaDto = disciplinaService.UpdateDisciplina(disciplinaModel, disciplinaId);
    return ResponseEntity.ok(disciplinaDto);
  }

  @DeleteMapping("delete/{disciplinaId}")
  public ResponseEntity<Void> delete(@PathVariable int disciplinaId) {
    disciplinaService.DeleteById(disciplinaId);
    return ResponseEntity.noContent().build();
  }

}
