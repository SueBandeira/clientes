package com.manger.clientes.controller;

import com.manger.clientes.DTO.ClienteDTOOutput;
import com.manger.clientes.DTO.DTOCriaCliente;
import com.manger.clientes.model.Cliente;
import com.manger.clientes.repository.ClienteRepository;
import com.manger.clientes.repository.UsuarioRepository;
import com.manger.clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios/{idUsuario}/clientes")
public class ClienteController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private ClienteService clienteService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Cliente> criarCliente (@PathVariable Long idUsuario,
                                               @RequestBody DTOCriaCliente cliente) {
    Cliente clienteCriado = clienteService.criaCliente(idUsuario, cliente);

    if (clienteCriado == null)
      return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok(clienteCriado);

  }

  @GetMapping("/{idCliente}")
  public ResponseEntity<Cliente> buscarCliente(@PathVariable Long idUsuario,
                                               @PathVariable Long idCliente) {
    Cliente cliente = clienteService.retornaClientePorId(idCliente, idUsuario);

    if (cliente == null) 
      return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok(cliente);
  }


  @GetMapping
  public ResponseEntity<List<ClienteDTOOutput>> listarClientes(@PathVariable Long idUsuario) {
    List<ClienteDTOOutput> clientes = clienteService.listarClientes(idUsuario);
    if (clientes == null)
      return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok(clientes);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletarCliente (@PathVariable Long id) {
    clienteService.deletaCliente(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public DTOCriaCliente atualizarCliente (@PathVariable Long id, @RequestBody DTOCriaCliente cliente) {
    clienteService.atualizaCliente(id, cliente);
    return cliente;
  }

}
