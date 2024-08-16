package br.com.academico.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academico.api.dtos.MatriculaDto;
import br.com.academico.api.exceptions.DataIntegrityException;
import br.com.academico.api.exceptions.ObjetctNotFoundException;
import br.com.academico.api.models.AlunoModel;
import br.com.academico.api.models.MatriculaModel;
import br.com.academico.api.models.TurmaModel;
import br.com.academico.api.repositories.AlunoRepository;
import br.com.academico.api.repositories.MatriculaRepository;

import br.com.academico.api.repositories.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MatriculaService {
  @Autowired
  private MatriculaRepository matriculaRepository;

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private TurmaRepository turmaRepository;

  @Autowired
  private ModelMapper modelMapper;

  public MatriculaDto GetMatriculaById(int id) {
    Optional<MatriculaModel> matriculaOptional = matriculaRepository.findById(id);
    MatriculaModel matriculaModel = matriculaOptional
        .orElseThrow(() -> new ObjetctNotFoundException("Id não encontrado! Id: " + id));

    return modelMapper.map(matriculaModel, MatriculaDto.class);
  }

  public List<MatriculaDto> GetMatriculas() {
    List<MatriculaModel> matriculaList = matriculaRepository.findAll();
    return matriculaList.stream()
        .map(matricula -> modelMapper.map(matricula, MatriculaDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public MatriculaDto PostMatricula(MatriculaModel matriculaModel) {
    alunoRepository.findById(matriculaModel.getAluno().getId())
        .orElseThrow(
            () -> new EntityNotFoundException("Aluno não encontrado com o ID: " + matriculaModel.getAluno().getId()));

    turmaRepository.findById(matriculaModel.getTurma().getId())
        .orElseThrow(
            () -> new EntityNotFoundException("Turma não encontrada com o ID: " + matriculaModel.getTurma().getId()));

    boolean matriculaExistente = matriculaRepository.existsByAlunoAndTurma(matriculaModel.getAluno(),
        matriculaModel.getTurma());
    if (matriculaExistente) {
      throw new IllegalStateException("O aluno já está matriculado nesta turma.");
    }
    MatriculaModel saveMatriculaModel = matriculaRepository.save(matriculaModel);
    return modelMapper.map(saveMatriculaModel, MatriculaDto.class);
  }

  @Transactional
  public MatriculaDto PutMatricula(MatriculaModel matriculaModel, int id) {
    AlunoModel alunoAtualizado = alunoRepository.findById(matriculaModel.getAluno().getId())
        .orElseThrow(
            () -> new EntityNotFoundException("Aluno não encontrado com o ID: " + matriculaModel.getAluno().getId()));

    TurmaModel turmaAtualizada = turmaRepository.findById(matriculaModel.getTurma().getId())
        .orElseThrow(
            () -> new EntityNotFoundException("Turma não encontrada com o ID: " + matriculaModel.getTurma().getId()));
    Optional<MatriculaModel> matriculaExistente = matriculaRepository.findById(id);
    MatriculaModel matriculaAtualizada = matriculaExistente
        .orElseThrow(() -> new ObjetctNotFoundException("Id não encontrado! Id: " + id));

    matriculaAtualizada.setTurma(turmaAtualizada);
    matriculaAtualizada.setAluno(alunoAtualizado);
    matriculaRepository.save(matriculaAtualizada);

    return modelMapper.map(matriculaAtualizada, MatriculaDto.class);
  }

  @Transactional
  public void DeleteById(int id) {
    if (matriculaRepository.existsById(id)) {
      matriculaRepository.deleteById(id);
    } else {
      throw new DataIntegrityException("Id não encontrado! Id: " + id);
    }
  }

}
