package com.alejandrosamuel.apinotas.service;

import com.alejandrosamuel.apinotas.model.Usuario;
import com.alejandrosamuel.apinotas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UsuarioServiceImpl extends AbstractCrudService<Usuario, Long> implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    @Override
    public Usuario save(Usuario usuario) {
        if (existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un usuario con este email");
        }
        // Hash de la contraseña antes de guardar
        usuario.setPasswordHash(hashPassword(usuario.getPasswordHash()));
        return super.save(usuario);
    }
    
    @Override
    public Usuario update(Long id, Usuario usuario) {
        Optional<Usuario> existingUser = usuarioRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        
        Usuario existing = existingUser.get();
        
        // Verificar si el email ya existe en otro usuario
        Optional<Usuario> userWithEmail = findByEmail(usuario.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un usuario con este email");
        }
        
        // Actualizar campos
        existing.setNombre(usuario.getNombre());
        existing.setEmail(usuario.getEmail());
        
        // Solo actualizar contraseña si se proporciona una nueva
        if (usuario.getPasswordHash() != null && !usuario.getPasswordHash().trim().isEmpty()) {
            // Verificar si la contraseña ya está hasheada (longitud típica de SHA-256 es 64 caracteres)
            if (usuario.getPasswordHash().length() != 64) {
                existing.setPasswordHash(hashPassword(usuario.getPasswordHash()));
            } else {
                existing.setPasswordHash(usuario.getPasswordHash());
            }
        }
        
        return super.save(existing);
    }
    
    @Override
    public Usuario signIn(String email, String password) {
        String hashedPassword = hashPassword(password);
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        
        if (usuario.isEmpty() || !usuario.get().getPasswordHash().equals(hashedPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
        
        return usuario.get();
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}