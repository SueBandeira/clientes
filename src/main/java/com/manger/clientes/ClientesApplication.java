package com.manger.clientes;

import com.manger.clientes.repository.ClienteRepository;
import com.manger.clientes.repository.UsuarioRepository;
import com.manger.clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientesApplication implements CommandLineRunner {

	@Autowired
	private ClienteRepository repositorio;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClienteService clienteService;

	public static void main(String[] args) {
		SpringApplication.run(ClientesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(usuarioRepository, repositorio, clienteService);
		principal.exibeMenu();
	}


}
