package com.manger.clientes.controller;

import com.manger.clientes.DTO.ClienteDTOOutput;
import com.manger.clientes.DTO.DTOCriaCliente;
import com.manger.clientes.model.Cliente;
import com.manger.clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
    Cliente cliente = clienteService.retornaClientePorId(id);
    return ResponseEntity.ok(cliente);
  }


  @GetMapping
  public ResponseEntity<List<ClienteDTOOutput>> listarClientes() {

    return ResponseEntity.ok(clienteService.listarCliente());
  }

/*//  @GetMapping("/2")
//  public ResponseEntity<Cliente> buscarCliente2(@RequestParam Long id) {
//    Cliente cliente = new Cliente();
//
//    cliente.setId(id);
//
//    return ResponseEntity.ok(cliente);
//  }

//  @GetMapping("/clientes")
//  public  ResponseEntity<List<Cliente>> listaClientes(@RequestParam Long id) {
//
//    return ResponseEntity.ok(Cliente);
//  }


//  @PostMapping("/cadastro")
//  public ResponseEntity cadastrarCliente() {
//    var userNovo = repository.save(new Cliente(d));
//
//    return ResponseEntity.ok(userNovo);
*/

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Cliente> adicionaCliente (@RequestBody DTOCriaCliente cliente) {
    Cliente clientes = this.clienteService.criaCliente(cliente);
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
