package br.com.academico.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academico.api.dtos.TurmaDto;
import br.com.academico.api.exceptions.ConstraintException;
import br.com.academico.api.models.TurmaModel;
import br.com.academico.api.services.TurmaService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/turma")
@Controller
public class TurmaController {
  @Autowired
  private TurmaService turmaService;

  @GetMapping("/list")
  public ResponseEntity<List<TurmaDto>> listTurmas() {
    List<TurmaDto> turmas = turmaService.GetTurmas();
    return ResponseEntity.ok(turmas);
  }

  @GetMapping("/{turmaId}")
  public ResponseEntity<TurmaDto> getTurmaById(@PathVariable int turmaId) {
    TurmaDto turmaDto = turmaService.GetTurmaById(turmaId);
    return ResponseEntity.ok(turmaDto);
  }

  @PostMapping("/register")
  public ResponseEntity<TurmaDto> postTurma(@Valid @RequestBody TurmaModel turmaModel, BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    TurmaDto turmaDto = turmaService.PostTurma(turmaModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(turmaDto);
  }

  @PutMapping("update/{turmaId}")
  public ResponseEntity<TurmaDto> putTurma(@PathVariable int turmaId, @Valid @RequestBody TurmaModel turmaModel,
      BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    TurmaDto turmaDto = turmaService.UpdateTurma(turmaModel, turmaId);
    return ResponseEntity.ok(turmaDto);
  }

  @DeleteMapping("delete/{turmaId}")
  public ResponseEntity<Void> delete(@PathVariable int turmaId) {
    turmaService.DeleteById(turmaId);
    return ResponseEntity.noContent().build();
  }

}
