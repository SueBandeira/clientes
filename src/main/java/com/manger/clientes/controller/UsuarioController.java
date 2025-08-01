package com.manger.clientes.controller;

import com.manger.clientes.DTO.DTOCriaUsuario;
import com.manger.clientes.model.Usuario;
import com.manger.clientes.repository.UsuarioRepository;
import com.manger.clientes.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;
  
  @Autowired
  private UsuarioService usuarioService;

  // Criar novo usuário
  @PostMapping
  public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody DTOCriaUsuario usuario) {
    Usuario salvo = usuarioService.criaUsuario(usuario);
    return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
  }

  // Atualizar usuário por ID
  @PutMapping("/{id}")
  public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long idUsuario,
                                                  @Valid @RequestBody DTOCriaUsuario usuario) {
    Usuario usuarioAtualizado = usuarioService.atualizaUsuario(idUsuario, usuario);
    if (usuarioAtualizado == null)
      return ResponseEntity.notFound().build();
    return ResponseEntity.ok(usuarioAtualizado);
  }

  // Deletar usuário por ID
//  @DeleteMapping("/{id}")
//  public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
//    if (!usuarioRepository.existsById(id)) {
//      return ResponseEntity.notFound().build();
//    }
//    usuarioRepository.deleteById(id);
//    return ResponseEntity.noContent().build();
//  }
}
