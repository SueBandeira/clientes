package com.manger.clientes.repository;

import com.manger.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  Optional<Cliente> findByIdAndUsuarioId(Long idCliente, Long idUsuario);

  List<Cliente> findByUsuarioId(Long idUsuario);
}
