package com.alejandrosamuel.apinotas.service;

import com.alejandrosamuel.apinotas.model.Nota;
import com.alejandrosamuel.apinotas.model.Usuario;
import com.alejandrosamuel.apinotas.repository.NotaRepository;
import com.alejandrosamuel.apinotas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class NotaServiceImpl extends AbstractCrudService<Nota, Long> implements NotaService {
    
    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public NotaServiceImpl(NotaRepository notaRepository, UsuarioRepository usuarioRepository) {
        super(notaRepository);
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public List<Nota> getNotasByUsuarioId(Long usuarioId, Sort sort) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return notaRepository.findByUsuarioId(usuarioId, sort);
    }
    
    @Override
    public Nota createNotaForUsuario(Long usuarioId, Nota nota) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        
        nota.setUsuario(usuario.get());
        return save(nota);
    }
    
    @Override
    public Nota update(Long id, Nota nota) {
        Optional<Nota> existingNota = getById(id);
        if (existingNota.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota no encontrada");
        }
        
        nota.setId(id);
        nota.setUsuario(existingNota.get().getUsuario());
        nota.setFechaCreacion(existingNota.get().getFechaCreacion());
        return save(nota);
    }
}