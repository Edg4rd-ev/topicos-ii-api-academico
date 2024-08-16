package br.com.academico.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academico.api.exceptions.DataIntegrityException;
import br.com.academico.api.exceptions.ObjetctNotFoundException;
import br.com.academico.api.models.AlunoModel;
import br.com.academico.api.models.DisciplinaModel;
import br.com.academico.api.repositories.DisciplinaRepository;
import br.com.academico.api.dtos.AlunoDto;
import br.com.academico.api.dtos.DisciplinaComAlunosDTO;
import br.com.academico.api.dtos.DisciplinaDto;

@Service
public class DisciplinaService {
  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @Autowired
  private ModelMapper modelMapper;

  public DisciplinaDto getDisciplinaByName(String nome) {
    Optional<DisciplinaModel> disciplinaOptional = disciplinaRepository.findByNome(nome);
    DisciplinaModel disciplinaModel = disciplinaOptional
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Disciplina não encontrada! Disciplina:" + nome));
    return modelMapper.map(disciplinaModel, DisciplinaDto.class);
  }

  public List<DisciplinaDto> GetDisciplinas() {
    List<DisciplinaModel> disciplinaList = disciplinaRepository.findAll();
    return disciplinaList.stream()
        .map(disciplina -> modelMapper.map(disciplina, DisciplinaDto.class))
        .collect(Collectors.toList());
  }

  public DisciplinaComAlunosDTO getDisciplinaComAlunos(int disciplinaId) {
    Optional<DisciplinaModel> disciplinaModel = disciplinaRepository.findById(disciplinaId);
    DisciplinaDto disciplinaDto = modelMapper.map(disciplinaModel, DisciplinaDto.class);

    List<AlunoModel> alunos = disciplinaRepository.findAlunosByDisciplinaId(disciplinaId);

    List<AlunoDto> alunosDto = alunos.stream()
        .map(aluno -> modelMapper.map(aluno, AlunoDto.class))
        .collect(Collectors.toList());

    return new DisciplinaComAlunosDTO(disciplinaDto, alunosDto);
  }

  @Transactional
  public DisciplinaDto PostDisciplina(DisciplinaModel disciplinaModel) {
    try {
      disciplinaRepository.save(disciplinaModel);
      return modelMapper.map(disciplinaModel, DisciplinaDto.class);
    } catch (Exception e) {
      throw new DataIntegrityException("ERRO: Disciplina já cadastrado!");
    }
  }

  @Transactional
  public DisciplinaDto UpdateDisciplina(DisciplinaModel disciplinaModel, int id) {
    Optional<DisciplinaModel> existingDisciplina = disciplinaRepository.findById(id);
    DisciplinaModel updatedDisciplina = existingDisciplina
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Disciplina não encontrada! Id: " + id));

    updatedDisciplina.setNome(disciplinaModel.getNome());
    updatedDisciplina.setCreditos(disciplinaModel.getCreditos());

    disciplinaRepository.save(updatedDisciplina);

    return modelMapper.map(updatedDisciplina, DisciplinaDto.class);
  }

  @Transactional
  public void DeleteById(int id) {
    if (disciplinaRepository.existsById(id)) {
      disciplinaRepository.deleteById(id);
    } else {
      throw new DataIntegrityException("ERRO: Disciplina não econtrada! Disciplina:" + id);
    }
  }

}
