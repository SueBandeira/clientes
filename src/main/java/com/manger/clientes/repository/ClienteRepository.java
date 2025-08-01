package com.manger.clientes.repository;

import com.manger.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  Optional<Cliente> findByIdAndUsuarioId(Long idCliente, Long idUsuario);

  List<Cliente> findByUsuarioId(Long idUsuario);

  @Query("""
    SELECT c FROM Cliente c
    WHERE c.usuario.id = :usuarioId
    AND (:uf IS NULL OR c.endereco.uf = :uf)
    AND (:cidade IS NULL OR c.endereco.cidade = :cidade)
    """)
  List<Cliente> buscarPorFiltros(
      @Param("usuarioId") Long usuarioId,
      @Param("uf") String uf,
      @Param("cidade") String cidade
  );
}
