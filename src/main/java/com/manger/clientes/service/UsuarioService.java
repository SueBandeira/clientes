package com.manger.clientes.service;

import com.manger.clientes.DTO.DTOCriaUsuario;
import com.manger.clientes.model.Usuario;
import com.manger.clientes.repository.ClienteRepository;
import com.manger.clientes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  UsuarioRepository usuarioRepository;

  ClienteRepository clienteRepository;

  public Usuario criaUsuario (DTOCriaUsuario usuarioDto) {
    Usuario usuario = new Usuario(usuarioDto.nome(), usuarioDto.senha());
    var usuarioSalvo = usuarioRepository.save(usuario);
    return usuarioSalvo;
  }

  public Usuario atualizaUsuario(Long idUsuario, DTOCriaUsuario usuarioDto) {
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
    if (usuarioOptional.isEmpty()) {
      return null;
    }

    Usuario usuario = usuarioOptional.get();
    if (usuarioDto.nome() != null && !usuarioDto.nome().isBlank())
      usuario.setNome(usuarioDto.nome());
    if (usuarioDto.senha() != null && !usuarioDto.senha().isBlank())
      usuario.setSenha(usuarioDto.senha());

    var usuarioAtualizado = usuarioRepository.save(usuario);
    return usuarioAtualizado;
  }

}
