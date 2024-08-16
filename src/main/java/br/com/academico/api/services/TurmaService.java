package br.com.academico.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academico.api.dtos.TurmaDto;
import br.com.academico.api.exceptions.DataIntegrityException;
import br.com.academico.api.exceptions.ObjetctNotFoundException;
import br.com.academico.api.models.TurmaModel;
import br.com.academico.api.repositories.TurmaRepository;
import jakarta.transaction.Transactional;

@Service
public class TurmaService {
  @Autowired
  private TurmaRepository turmaRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<TurmaDto> GetTurmas() {
    List<TurmaModel> turmaList = turmaRepository.findAll();
    return turmaList.stream()
        .map(turma -> modelMapper.map(turmaList, TurmaDto.class))
        .collect(Collectors.toList());
  }

  public TurmaDto GetTurmaById(int id) {
    Optional<TurmaModel> turmaOptional = turmaRepository.findById(id);
    TurmaModel turmaModel = turmaOptional
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Turma não encontrada! idTurma:" + id));
    return modelMapper.map(turmaModel, TurmaDto.class);
  }

  @Transactional
  public TurmaDto PostTurma(TurmaModel turmaModel) {
    try {
      turmaRepository.save(turmaModel);
      return modelMapper.map(turmaModel, TurmaDto.class);
    } catch (Exception e) {
      throw new DataIntegrityException("ERRO: " + e);
    }
  }

  @Transactional
  public TurmaDto UpdateTurma(TurmaModel turmaModel, int id) {
    Optional<TurmaModel> existingTurma = turmaRepository.findById(id);
    TurmaModel updatedTurma = existingTurma
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Matrícula não encontrada! Matrícula: " + id));

    updatedTurma.setDataFim(turmaModel.getDataFim());
    updatedTurma.setDataInicio(turmaModel.getDataInicio());
    turmaRepository.save(updatedTurma);

    return modelMapper.map(updatedTurma, TurmaDto.class);
  }

  @Transactional
  public void DeleteById(int id) {
    if (turmaRepository.existsById(id)) {
      turmaRepository.deleteById(id);
    } else {
      throw new DataIntegrityException("ERRO: Turma não econtrada! Turma:" + id);
    }
  }
}
