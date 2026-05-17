package com.prueba2.urbandelivery.Repository;

import com.prueba2.urbandelivery.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByRut(String rut);

    List<Usuario> findByActivo(Boolean activo);

    @Query("SELECT u FROM Usuario u WHERE u.nombreCompleto LIKE %:nombre%")
    List<Usuario> findByNombreCompletoContaining(@Param("nombre") String nombre);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.activo = true")
    Long countActiveUsers();
}