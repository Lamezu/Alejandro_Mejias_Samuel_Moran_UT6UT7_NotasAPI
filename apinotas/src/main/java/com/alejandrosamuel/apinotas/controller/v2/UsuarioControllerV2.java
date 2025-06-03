package com.alejandrosamuel.apinotas.controller.v2;

import com.alejandrosamuel.apinotas.model.Usuario;
import com.alejandrosamuel.apinotas.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v2/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioControllerV2 {
    
    private final UsuarioService usuarioService;
    
    @Autowired
    public UsuarioControllerV2(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> signIn(@Valid @RequestBody SignInRequest request) {
        Usuario usuario = usuarioService.signIn(request.getEmail(), request.getPassword());
        
        Map<String, Object> response = Map.of(
            "message", "Login exitoso",
            "usuario", Map.of(
                "id", usuario.getId(),
                "nombre", usuario.getNombre(),
                "email", usuario.getEmail()
            )
        );
        
        return ResponseEntity.ok(response);
    }
    
    // Clase interna para el request de sign-in
    public static class SignInRequest {
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "Debe ser un email válido")
        private String email;
        
        @NotBlank(message = "La contraseña no puede estar vacía")
        private String password;
        
        // Constructores
        public SignInRequest() {}
        
        public SignInRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
        
        // Getters y Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}