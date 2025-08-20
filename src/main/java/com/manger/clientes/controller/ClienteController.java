package com.manger.clientes.controller;

import com.manger.clientes.DTO.ClienteDTOOutput;
import com.manger.clientes.DTO.DTOCriaCliente;
import com.manger.clientes.model.Cliente;
import com.manger.clientes.repository.ClienteRepository;
import com.manger.clientes.repository.UsuarioRepository;
import com.manger.clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios/{idUsuario}/clientes")
public class ClienteController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private ClienteService clienteService;

  @GetMapping("/filtrar")
  public ResponseEntity<List<Cliente>> buscarClientesPorFiltros(
      @PathVariable Long idUsuario,
      @RequestParam(required = false) String uf,
      @RequestParam(required = false) String cidade
    //  @RequestParam(required = false) Integer minHabitantes
  ) {

    List<Cliente> clientesFiltrados = clienteService.buscarPorFiltros(idUsuario, uf, cidade);

    return ResponseEntity.ok(clientesFiltrados);
  }

  @GetMapping("/sugestao")
  public ResponseEntity<List<String>> sugestaoPesquisa (@PathVariable Long idUsuario,
                                                        @RequestParam String palavra) {

    List<String> sugestoes = clienteService.sugestoes(palavra);

    return ResponseEntity.ok(sugestoes);
  }

  // region ------- CRUD -------
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Cliente> criarCliente (@PathVariable Long idUsuario,
                                               @Valid @RequestBody DTOCriaCliente cliente) {
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

//  //Chamada do
//  @DeleteMapping("/{idCliente}")
//  @ResponseStatus(HttpStatus.NO_CONTENT)
//  public void deletarCliente (@PathVariable Long idCliente) {
//    clienteService.deletaCliente(idCliente);
//  }

  @DeleteMapping("/{idCliente}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Cliente> deletarCliente (@PathVariable Long idUsuario, @PathVariable Long idCliente) {
    return ResponseEntity.ok(clienteService.getAndDeleteById(idUsuario, idCliente));
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public DTOCriaCliente atualizarCliente (@PathVariable Long id,@Valid @RequestBody DTOCriaCliente cliente) {
    clienteService.atualizaCliente(id, cliente);
    return cliente;
  }

  // endregion

}
