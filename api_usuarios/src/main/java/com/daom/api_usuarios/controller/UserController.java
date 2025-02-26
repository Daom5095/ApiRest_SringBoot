package com.daom.api_usuarios.controller;

import com.daom.api_usuarios.dto.UserDto;
import com.daom.api_usuarios.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Obtener todos los usuarios
    @GetMapping("/todos")
    public ResponseEntity<List<UserDto>> obtenerUsuarios() {
        List<UserDto> usuarios = userService.mostrarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> obtenerUsuarioPorId(@PathVariable Long id) {
        UserDto usuario = userService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // Crear usuario
    @PostMapping("/crear")
    public ResponseEntity<UserDto> guardarUsuario(@Valid @RequestBody UserDto usuario) {
        UserDto usuarioNuevo = userService.crearUsuario(usuario);
        return ResponseEntity.ok(usuarioNuevo);
    }

    // Actualizar usuario
    @PutMapping("/editar/{id}")
    public ResponseEntity<UserDto> editarUsuario(@PathVariable Long id, @Valid @RequestBody UserDto usuario) {
        UserDto usuarioActualizado = userService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        userService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
