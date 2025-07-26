package com.manger.clientes.service;

import com.manger.clientes.DTO.ClienteDTOOutput;
import com.manger.clientes.DTO.DTOCriaCliente;
import com.manger.clientes.DTO.DadosEndereco;
import com.manger.clientes.DTO.Endereco;
import com.manger.clientes.model.Cliente;
import com.manger.clientes.repository.ClienteRepository;
import com.manger.clientes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

  private final ClienteRepository clienteRepository;

  private UsuarioRepository usuarioRepository;

  //pode substituir o autowired
  public ClienteService (ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  public Cliente retornaClientePorId (Long id) {
    Optional<Cliente> cliente = clienteRepository.findById(id);

    if (cliente.isEmpty()) {
      return null;
    }
    return cliente.get();
  }

  public Endereco obtemEndereco (String cep) {
    ConsumoApi consumoAPI = new ConsumoApi();
    ConverteDados conversor = new ConverteDados();

    String ENDERECO = "https://viacep.com.br/ws/";

    var json = consumoAPI.obterDados(ENDERECO + cep + "/json/");

    DadosEndereco dadosResultado = conversor.obterDados(json, DadosEndereco.class);

    Endereco endereco = new Endereco(dadosResultado);

    return endereco;
  }

  public Cliente criaCliente (DTOCriaCliente cliente) {
    String cep = cliente.cep();
    var endereco = obtemEndereco(cep);
    Cliente clientes = new Cliente(cliente.nome(), cliente.telefone(), cliente.email(), endereco);
    System.out.println(clientes);

    var clienteSalvo = clienteRepository.save(clientes);
    System.out.println(clienteSalvo);

    return clienteSalvo;
  }

  public Cliente criaCliente (Cliente cliente) {
    var clienteSalvo = clienteRepository.save(cliente);
    System.out.println(clienteSalvo);

    return clienteSalvo;
  }

  public void deletaCliente (Long id) {
    clienteRepository.deleteById(id);
  }

  public Cliente atualizaCliente (Long id, DTOCriaCliente cliente) {

    Optional<Cliente> cliente1 = clienteRepository.findById(id);
    if (cliente1.isEmpty()) {
      return null;
    }
    Cliente cliente2 = cliente1.get();

    if (cliente.nome() != null && !cliente.nome().isBlank()) {
      cliente2.setNome(cliente.nome());
    } if (cliente.email() != null && !cliente.email().isBlank()) {
      cliente2.setEmail(cliente.email());
    } if (cliente.telefone() != null && !cliente.telefone().isBlank()) {
      cliente2.setTelefone(cliente.telefone());
    } if (cliente.cep() != null && !cliente.cep().isBlank()) {
      var endereco = obtemEndereco(cliente.cep());//neste método obtemEndereço pode fazer a lógica de bater no banco primeiro
      cliente2.setEndereco(endereco);
    }
    clienteRepository.save(cliente2);//olhar a notação transactional
    return cliente2;
  }

  public List<ClienteDTOOutput> listarCliente() {
    List<Cliente> all = clienteRepository.findAll();
    List<ClienteDTOOutput> clientes = all.stream().map(ClienteDTOOutput::new).toList();

    return clientes;
  }
}
