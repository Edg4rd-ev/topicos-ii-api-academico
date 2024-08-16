package br.com.academico.api.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academico.api.dtos.AlunoDto;
import br.com.academico.api.exceptions.ConstraintException;
import br.com.academico.api.models.AlunoModel;
import br.com.academico.api.services.AlunoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "/aluno")
@Controller
public class AlunoController {

  @Autowired
  private AlunoService alunoService;

  @GetMapping("/list")
  public ResponseEntity<List<AlunoDto>> getAlunos() {
    List<AlunoDto> dtoAlunoList = alunoService.GetAlunos();
    return ResponseEntity.ok(dtoAlunoList);
  }

  @GetMapping("/{matricula}")
  public ResponseEntity<AlunoDto> getAlunoByMatricula(@PathVariable String matricula) {
    AlunoDto alunoDto = alunoService.GetAlunoByMatricula(matricula);
    return ResponseEntity.ok(alunoDto);
  }

  @PostMapping("/register")
  public ResponseEntity<AlunoDto> postAluno(@Valid @RequestBody AlunoModel alunoModel, BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    AlunoDto alunoDto = alunoService.PostAluno(alunoModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(alunoDto);
  }

  @PutMapping("update/{matricula}")
  public ResponseEntity<AlunoDto> putAluno(@PathVariable String matricula, @Valid @RequestBody AlunoModel alunoModel,
      BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    AlunoDto alunoDto = alunoService.UpdateAluno(alunoModel, matricula);
    return ResponseEntity.ok(alunoDto);
  }

  @DeleteMapping("delete/{matricula}")
  public ResponseEntity<Void> delete(@PathVariable String matricula) {
    alunoService.DeleteByMatricula(matricula);
    return ResponseEntity.noContent().build();
  }

}
