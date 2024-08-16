package br.com.academico.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academico.api.dtos.AlunoDto;
import br.com.academico.api.dtos.DisciplinaComAlunosDTO;
import br.com.academico.api.dtos.DisciplinaDto;
import br.com.academico.api.dtos.ProfessorDisciplinasAlunosDTO;
import br.com.academico.api.dtos.ProfessorDisciplinasDTO;
import br.com.academico.api.dtos.ProfessorDto;
import br.com.academico.api.exceptions.DataIntegrityException;
import br.com.academico.api.exceptions.ObjetctNotFoundException;
import br.com.academico.api.models.ProfessorModel;
import br.com.academico.api.models.TurmaModel;
import br.com.academico.api.repositories.ProfessorRepository;
import br.com.academico.api.utils.GerarMatricula;
import br.com.academico.api.utils.TipoUsuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProfessorService {
  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private ModelMapper modelMapper;

  public ProfessorDto GetProfessorByMatricula(String matricula) {
    Optional<ProfessorModel> professorOptional = professorRepository.findByMatricula(matricula);
    ProfessorModel professorModel = professorOptional
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Matrícula não encontrada! Matrícula: " + matricula));

    return modelMapper.map(professorModel, ProfessorDto.class);
  }

  public List<ProfessorDto> GetProfessores() {
    List<ProfessorModel> professorList = professorRepository.findAll();
    return professorList.stream()
        .map(professor -> modelMapper.map(professor, ProfessorDto.class))
        .collect(Collectors.toList());
  }

  public ProfessorDisciplinasDTO GetProfessorDisciplinas(int idProfessor) {

    Optional<ProfessorModel> professorOptional = professorRepository.findById(idProfessor);
    if (!professorOptional.isPresent()) {
      throw new EntityNotFoundException("Professor não encontrado");
    }

    ProfessorModel professorModel = professorOptional
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Matrícula não encontrada! Matrícula: " + idProfessor));
    ProfessorDto professorDto = modelMapper.map(professorModel, ProfessorDto.class);

    List<TurmaModel> turmas = professorRepository.findTurmasByProfessorId(idProfessor);

    List<DisciplinaDto> disciplinasDto = turmas.stream()
        .map(turma -> modelMapper.map(turma.getDisciplina(), DisciplinaDto.class))
        .collect(Collectors.toList());

    return new ProfessorDisciplinasDTO(professorDto, disciplinasDto);
  }

  public ProfessorDisciplinasAlunosDTO GetProfessorDisciplinasAlunos(int professorId) {
    ProfessorModel professorModel = professorRepository.findById(professorId)
        .orElseThrow(() -> new EntityNotFoundException("ERRO: Professor não encontrado com o ID: " + professorId));
    ProfessorDto professorDto = modelMapper.map(professorModel, ProfessorDto.class);

    List<TurmaModel> turmas = professorRepository.findTurmasByProfessorId(professorId);

    List<DisciplinaComAlunosDTO> disciplinasComAlunos = turmas.stream()
        .map(turma -> {
          DisciplinaDto disciplinaDto = modelMapper.map(turma.getDisciplina(), DisciplinaDto.class);

          List<AlunoDto> alunosDto = turma.getMatriculas().stream()
              .map(matricula -> modelMapper.map(matricula.getAluno(), AlunoDto.class))
              .collect(Collectors.toList());

          return new DisciplinaComAlunosDTO(disciplinaDto, alunosDto);
        })
        .collect(Collectors.toList());

    return new ProfessorDisciplinasAlunosDTO(professorDto, disciplinasComAlunos);
  }

  @Transactional
  public ProfessorDto PostProfessor(ProfessorModel professorModel) {
    try {
      GerarMatricula gerar = new GerarMatricula();
      professorModel.setMatricula(gerar.gerarMatricula(TipoUsuario.PROFESSOR));
      professorRepository.save(professorModel);
      return modelMapper.map(professorModel, ProfessorDto.class);
    } catch (Exception e) {
      throw new DataIntegrityException("ERRO: Professor já cadastrado!");
    }
  }

  @Transactional
  public ProfessorDto UpdateProfessor(ProfessorModel professorModel, String matricula) {
    Optional<ProfessorModel> existingProfessor = professorRepository.findByMatricula(matricula);
    ProfessorModel updatedProfessor = existingProfessor
        .orElseThrow(() -> new ObjetctNotFoundException("ERRO: Matrícula não encontrada! Matrícula: " + matricula));

    updatedProfessor.setNome(professorModel.getNome());
    updatedProfessor.setEmail(professorModel.getEmail());
    updatedProfessor.setCelular(professorModel.getCelular());
    professorRepository.save(updatedProfessor);

    return modelMapper.map(updatedProfessor, ProfessorDto.class);
  }

  @Transactional
  public void DeleteByMatricula(String matricula) {
    if (professorRepository.existsByMatricula(matricula)) {
      professorRepository.deleteByMatricula(matricula);
    } else {
      throw new DataIntegrityException("ERRO: Matrícula não econtrada! Matrícula:" + matricula);
    }
  }
}
