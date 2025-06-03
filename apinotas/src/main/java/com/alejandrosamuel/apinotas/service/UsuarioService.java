package com.alejandrosamuel.apinotas.service;

import com.alejandrosamuel.apinotas.model.Usuario;

import java.util.Optional;

public interface UsuarioService extends CrudService<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Usuario signIn(String email, String password);
}