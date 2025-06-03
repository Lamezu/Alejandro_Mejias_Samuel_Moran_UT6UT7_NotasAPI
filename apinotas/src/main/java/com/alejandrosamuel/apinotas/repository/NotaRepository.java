package com.alejandrosamuel.apinotas.repository;

import com.alejandrosamuel.apinotas.model.Nota;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByUsuarioId(Long usuarioId, Sort sort);
}