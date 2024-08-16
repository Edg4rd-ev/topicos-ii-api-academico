package br.com.academico.api.services;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import br.com.academico.api.dtos.AlunoDto;
import br.com.academico.api.models.AlunoModel;
import br.com.academico.api.repositories.AlunoRepository;
import br.com.academico.api.utils.GerarMatricula;
import br.com.academico.api.utils.TipoUsuario;
import br.com.academico.api.exceptions.DataIntegrityException;
import br.com.academico.api.exceptions.ObjetctNotFoundException;

@Service
public class AlunoService {

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private ModelMapper modelMapper;

  public AlunoDto GetAlunoByMatricula(String matricula) {
    Optional<AlunoModel> alunoOptional = alunoRepository.findByMatricula(matricula);
    AlunoModel alunoModel = alunoOptional
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Matrícula não encontrada! Matrícula: " + matricula));

    return modelMapper.map(alunoModel, AlunoDto.class);
  }

  public List<AlunoDto> GetAlunos() {
    List<AlunoModel> alunoList = alunoRepository.findAll();
    return alunoList.stream()
        .map(aluno -> modelMapper.map(aluno, AlunoDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public AlunoDto PostAluno(AlunoModel alunoModel) {
    try {
      GerarMatricula gerar = new GerarMatricula();
      alunoModel.setMatricula(gerar.gerarMatricula(TipoUsuario.ALUNO));
      alunoRepository.save(alunoModel);
      return modelMapper.map(alunoModel, AlunoDto.class);
    } catch (Exception e) {
      throw new DataIntegrityException("ERRO: Aluno já cadastrado!");
    }
  }

  @Transactional
  public AlunoDto UpdateAluno(AlunoModel alunoModel, String matricula) {
    Optional<AlunoModel> existingAluno = alunoRepository.findByMatricula(matricula);
    AlunoModel updatedAluno = existingAluno
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Matrícula não encontrada! Matrícula: " + matricula));

    updatedAluno.setNome(alunoModel.getNome());
    updatedAluno.setEmail(alunoModel.getEmail());
    updatedAluno.setCelular(alunoModel.getCelular());
    updatedAluno.setApelido(updatedAluno.getApelido());
    alunoRepository.save(updatedAluno);

    return modelMapper.map(updatedAluno, AlunoDto.class);
  }

  @Transactional
  public void DeleteByMatricula(String matricula) {
    if (alunoRepository.existsByMatricula(matricula)) {
      alunoRepository.deleteByMatricula(matricula);
    } else {
      throw new DataIntegrityException("ERRO: Matrícula não econtrada! Matrícula:" + matricula);
    }
  }

}
