package com.daom.api_usuarios.service;

import com.daom.api_usuarios.dto.UserDto;
import com.daom.api_usuarios.exceptions.ResourceNotFoundException;
import com.daom.api_usuarios.models.User;
import com.daom.api_usuarios.respository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // Inyección de dependencias por constructor (recomendado para pruebas)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Obtener todos los usuarios con DTOs
    public List<UserDto> mostrarTodosUsuarios() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getNombre(), user.getApellido(), user.getTelefono(), user.getEmail()))
                .collect(Collectors.toList());
    }

    // Obtener usuario por ID
    public UserDto obtenerPorId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));
        return new UserDto(user.getNombre(), user.getApellido(), user.getTelefono(), user.getEmail());
    }

    // Crear un nuevo usuario con validación
    public UserDto crearUsuario(UserDto userDto) {
        validarUsuario(userDto);

        User user = new User(userDto.getNombre(), userDto.getApellido(), userDto.getTelefono(), userDto.getEmail());
        user = userRepository.save(user);

        return new UserDto(user.getNombre(), user.getApellido(), user.getTelefono(), user.getEmail());
    }

    // Actualizar usuario con validación
    public UserDto actualizarUsuario(Long id, UserDto userDTO) {
        validarUsuario(userDTO);

        User userExistente = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));

        userExistente.setNombre(userDTO.getNombre());
        userExistente.setApellido(userDTO.getApellido());
        userExistente.setTelefono(userDTO.getTelefono());
        userExistente.setEmail(userDTO.getEmail());

        User usuarioActualizado = userRepository.save(userExistente);

        return new UserDto(usuarioActualizado.getNombre(), usuarioActualizado.getApellido(),
                usuarioActualizado.getTelefono(), usuarioActualizado.getEmail());
    }

    // Eliminar usuario con verificación
    public void eliminarUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado");
        }
        userRepository.deleteById(id);
    }

    // Validar datos del usuario antes de guardarlo o actualizarlo
    private void validarUsuario(UserDto userDTO) {
        if (userDTO.getNombre() == null || userDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (userDTO.getEmail() == null || !userDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("El email no es válido");
        }
    }
}
