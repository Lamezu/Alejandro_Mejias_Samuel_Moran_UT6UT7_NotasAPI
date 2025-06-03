package com.alejandrosamuel.apinotas.service;

import com.alejandrosamuel.apinotas.model.Nota;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NotaService extends CrudService<Nota, Long> {
    List<Nota> getNotasByUsuarioId(Long usuarioId, Sort sort);
    Nota createNotaForUsuario(Long usuarioId, Nota nota);
}
