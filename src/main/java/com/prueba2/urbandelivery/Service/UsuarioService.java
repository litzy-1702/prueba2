package com.prueba2.urbandelivery.Service;

import com.prueba2.urbandelivery.Model.Usuario;
import com.prueba2.urbandelivery.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }

    public List<Usuario> findByActivo(Boolean activo) {
        return usuarioRepository.findByActivo(activo);
    }

    public List<Usuario> findByNombreCompletoContaining(String nombreCompleto) {
        return usuarioRepository.findByNombreCompletoContaining(nombreCompleto);
    }

    public Usuario save(Usuario usuario) {
        if (usuarioRepository.findByRut(usuario.getRut()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el RUT: " + usuario.getRut());
        }

        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setRut(usuarioDetails.getRut());
                    usuario.setNombreCompleto(usuarioDetails.getNombreCompleto());
                    usuario.setEmail(usuarioDetails.getEmail());
                    usuario.setTelefono(usuarioDetails.getTelefono());
                    usuario.setDireccion(usuarioDetails.getDireccion());
                    usuario.setActivo(usuarioDetails.getActivo());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public void deleteByRut(String rut) {
        Usuario usuario = usuarioRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con RUT: " + rut));
        usuarioRepository.delete(usuario);
    }

    public Long countActiveUsers() {
        return usuarioRepository.countActiveUsers();
    }
}
