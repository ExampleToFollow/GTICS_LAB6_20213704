package com.example.lab6gtics.Repository;

import com.example.lab6gtics.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {
    @Query(nativeQuery = true, value = "select * from usuario where correo = ?1")
    Usuario findByEmail(String correo);
}
