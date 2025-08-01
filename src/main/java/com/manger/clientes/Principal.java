package com.manger.clientes;

import com.manger.clientes.DTO.DTOCriaCliente;
import com.manger.clientes.model.Cliente;
import com.manger.clientes.DTO.DadosEndereco;
import com.manger.clientes.DTO.Endereco;
import com.manger.clientes.model.Usuario;
import com.manger.clientes.repository.ClienteRepository;
import com.manger.clientes.repository.UsuarioRepository;
import com.manger.clientes.service.ClienteService;
import com.manger.clientes.service.ConsumoApi;
import com.manger.clientes.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
  private Scanner leitura = new Scanner(System.in);

  private ConverteDados conversor = new ConverteDados();
  private ConsumoApi consumoAPI = new ConsumoApi();

  private List<DadosEndereco> dadosEndereco = new ArrayList<>();
  private List<Cliente> clientes;
  private List<Usuario> usuarios;

  private ClienteRepository repositorio;
  private UsuarioRepository usuarioRepository;

  private ClienteService clienteService;

  public Principal(UsuarioRepository usuarioRepository, ClienteRepository repositorio, ClienteService clienteService) {
    this.repositorio = repositorio;
    this.usuarioRepository = usuarioRepository;
    this.clientes = new ArrayList<>();
    this.clienteService = clienteService;
  }

  public void exibeMenu() {
    var opcao = -1;
    while (opcao != 0) {
      var menu = """
          -----------------------------------------------
          [        Bem-vindo ao Cliente manager         ]
          ===============================================
                  Escolha o número de sua opção:
          ===============================================
          [1] Fazer Login
          [2] Buscar CEP
          [3] Cadastrar cliente
          [4] Listar clientes
          [5] Listar cliente por id
          [6] Deletar cliente
          [7] Atualizar cliente
          
          [0]  Sair
          \n""";

      System.out.println(menu);
      opcao = leitura.nextInt();
      leitura.nextLine();

      switch (opcao) {
//        case 1:
//
//          break;
        case 2:
         // getDadosEndereco();
          break;
        case 3:
          cadastraCliente();
          break;
//        case 4:
//          listasClientesRegistrados();
//          break;
        case 5:
          listasClientePorId();
          break;
//        case 6:
//          deletarCliente();
//          break;
        case 7:
          atualizarCliente();
          break;
        case 0:
          System.out.println("Saindo...");
          break;
        default:
          System.out.println("Opção inválida");
      }
    }
  }

  private void atualizarCliente() {
    System.out.println("Digite o ID do Usuário: ");
    var idUsuario = leitura.nextLong();
    leitura.nextLine();

    System.out.println("Digite o ID do cliente: ");
    var idCliente = leitura.nextLong();
    leitura.nextLine();

    Cliente clienteBanco = clienteService.retornaClientePorId(idUsuario, idCliente);
    System.out.println(clienteBanco);

    if (clienteBanco == null) {
      System.out.println("Cliente não encontrado.");
      return;
    }

    boolean flag = false;

    System.out.println("Preencha os campos a seguir com as informações do cliente.\nDigite o nome: ");
    String input = leitura.nextLine().trim();
    var nome = clienteBanco.getNome();

    if (!input.isBlank()) {
      nome = input;
      flag = true;
    }

    System.out.println("\nDigite o telefone: ");
    input = leitura.nextLine().trim();
    var telefone = clienteBanco.getTelefone();

    if (!input.isBlank()) {
      telefone = input;
      flag = true;
    }

    System.out.println("\nDigite o e-mail: ");
    input = leitura.nextLine().trim();
    var email = clienteBanco.getEmail();

    if (!input.isBlank()) {
      email = input;
      flag = true;
    }

    System.out.println("\nDigite o CEP: ");
    input = leitura.nextLine().trim();
    String cep = null;

    if (!input.isBlank()) {
      //Endereco endereco = new Endereco(getDadosEndereco(cep));
      cep = input;
      flag = true;
    }

    Endereco endereco = clienteBanco.getEndereco();
    if (flag) {
      DTOCriaCliente cliente = new DTOCriaCliente(nome, telefone, email, cep);
      clienteBanco =  clienteService.atualizaCliente(idCliente, cliente);
      endereco = clienteBanco.getEndereco();
    }
    clienteBanco.setEndereco(endereco);
    clienteService.criaCliente(clienteBanco);
  }

  private void listasClientePorId() {
    System.out.println("Digite o ID do Usuário: ");
    var idUsuario = leitura.nextLong();
    leitura.nextLine();

    System.out.println("Digite o ID do cliente: ");
    var idCliente = leitura.nextLong();
    leitura.nextLine();

    Cliente clienteRetornado = clienteService.retornaClientePorId(idUsuario, idCliente);
    if (clienteRetornado == null) {
      System.out.println("Cliente não encontrado.");
    } else {
      System.out.println("\n" + clienteRetornado);
    }

  }

  private void deletarCliente() {
    System.out.println("Digite o ID do cliente: ");
    var nome = leitura.nextLong();
    leitura.nextLine();

    //clienteService.deletaCliente(nome);
  }

//  private void listasClientesRegistrados() {
//    System.out.println(clienteService.listarClientes());
//  }

  public DadosEndereco getDadosEndereco(String cep) {
    String ENDERECO = "https://viacep.com.br/ws/";

    var json = consumoAPI.obterDados(ENDERECO + cep + "/json/");

    DadosEndereco dadosResultado = conversor.obterDados(json, DadosEndereco.class);

    System.out.println(dadosResultado + "\n\nUf: " + dadosResultado.uf());

    return dadosResultado;
  }

  private void cadastraCliente() {
    System.out.println("Preencha os campos a seguir com as informações do cliente.\nDigite o nome: ");
    var nome = leitura.nextLine();

    System.out.println("\nDigite o telefone: ");
    var telefone = leitura.nextLine();

    System.out.println("\nDigite o e-mail: ");
    var email = leitura.nextLine();

    System.out.println("\nDigite o CEP: ");
    var cep = leitura.nextLine();

    Endereco endereco = new Endereco(getDadosEndereco(cep));

    Cliente cliente = new Cliente(nome, telefone, email, endereco);
    clienteService.criaCliente(cliente);
  }

}
