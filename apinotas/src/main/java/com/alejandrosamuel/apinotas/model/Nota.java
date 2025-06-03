package com.alejandrosamuel.apinotas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "notas")
public class Nota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    @Column(nullable = false)
    private String titulo;
    
    @NotBlank(message = "El contenido no puede estar vacío")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
    
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
    
    @ManyToOne(fetch = FetchType.EAGER) // Cambiar a EAGER para evitar LazyInitializationException
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // Evitar referencia circular en JSON
    private Usuario usuario;
    
    // Campo adicional para mostrar solo el ID del usuario en el JSON
    @Transient
    private Long usuarioId;
    
    // Constructores
    public Nota() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Nota(String titulo, String contenido, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuario = usuario;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    // Getter para el ID del usuario en JSON
    public Long getUsuarioId() {
        return usuario != null ? usuario.getId() : usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}