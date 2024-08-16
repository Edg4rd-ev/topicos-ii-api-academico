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

import br.com.academico.api.dtos.MatriculaDto;
import br.com.academico.api.exceptions.ConstraintException;
import br.com.academico.api.models.MatriculaModel;
import br.com.academico.api.services.MatriculaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/matricula")
@Controller
public class MatriculaController {
  @Autowired
  private MatriculaService matriculaService;

  @GetMapping("/{matriculaId}")
  public ResponseEntity<MatriculaDto> getMatriculaById(@PathVariable int matriculaId) {
    MatriculaDto matriculaDto = matriculaService.GetMatriculaById(matriculaId);
    return ResponseEntity.ok(matriculaDto);
  }

  @GetMapping("/list")
  public ResponseEntity<List<MatriculaDto>> getMatriculas() {
    List<MatriculaDto> matriculaDtoList = matriculaService.GetMatriculas();
    return ResponseEntity.ok(matriculaDtoList);
  }

  @PostMapping("/register")
  public ResponseEntity<MatriculaDto> postMatricula(@Valid @RequestBody MatriculaModel matriculaModel,
      BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }
    MatriculaDto matriculaDto = matriculaService.PostMatricula(matriculaModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(matriculaDto);
  }

  @PutMapping("/update/{matriculaId}")
  public ResponseEntity<MatriculaDto> updateMatricula(@Valid @RequestBody MatriculaModel matriculaModel,
      @PathVariable int matriculaId, BindingResult br) {
    if (br.hasErrors()) {
      throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
    }

    MatriculaDto matrículaDto = matriculaService.PutMatricula(matriculaModel, matriculaId);
    return ResponseEntity.ok(matrículaDto);
  }

  @DeleteMapping("delete/{matriculaId}")
  public ResponseEntity<Void> deleteMatricula(@PathVariable int matriculaId) {
    matriculaService.DeleteById(matriculaId);
    return ResponseEntity.noContent().build();
  }
}
