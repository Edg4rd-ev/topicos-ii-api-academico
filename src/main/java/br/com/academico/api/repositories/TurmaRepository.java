package br.com.academico.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academico.api.models.TurmaModel;

public interface TurmaRepository extends JpaRepository<TurmaModel, Integer> {
}
