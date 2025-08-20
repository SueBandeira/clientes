package com.manger.clientes.service;

import com.manger.clientes.DTO.ClienteDTOOutput;
import com.manger.clientes.DTO.DTOCriaCliente;
import com.manger.clientes.DTO.DadosEndereco;
import com.manger.clientes.DTO.Endereco;
import com.manger.clientes.model.Cliente;
import com.manger.clientes.model.Usuario;
import com.manger.clientes.repository.ClienteRepository;
import com.manger.clientes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

  private final ClienteRepository clienteRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  //pode substituir o autowired
  public ClienteService(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  public Cliente retornaClientePorId(Long idUsuario, Long idCliente) {
    Optional<Cliente> cliente = clienteRepository.findByIdAndUsuarioId(idCliente, idUsuario);

    if (cliente.isEmpty()) {
      return null;
    }
    return cliente.get();
  }

  public Endereco obtemEndereco(String cep) {
    ConsumoApi consumoAPI = new ConsumoApi();
    ConverteDados conversor = new ConverteDados();

    String ENDERECO = "https://viacep.com.br/ws/";

    var json = consumoAPI.obterDados(ENDERECO + cep + "/json/");

    DadosEndereco dadosResultado = conversor.obterDados(json, DadosEndereco.class);

    Endereco endereco = new Endereco(dadosResultado);

    return endereco;
  }

  public Cliente criaCliente(Long idUsuario, DTOCriaCliente cliente) {
    Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
    if (usuario.isEmpty()) {
      return null;
    }

    Endereco endereco = obtemEndereco(cliente.cep());

    Cliente clienteEntidade = new Cliente(cliente.nome(), cliente.telefone(), cliente.email(), endereco);

    clienteEntidade.setUsuario(usuario.get());
    Cliente salvo = clienteRepository.save(clienteEntidade);

    return clienteEntidade;
  }

  public Cliente criaCliente(Cliente cliente) {
    var clienteSalvo = clienteRepository.save(cliente);
    System.out.println(clienteSalvo);

    return clienteSalvo;
  }

//  public void deletaCliente (Long idCliente) {
//    clienteRepository.deleteById(idCliente);
//  }

  public Cliente getAndDeleteById(Long idUsuario, Long idCliente) {
    Optional<Cliente> cliente = clienteRepository.findByIdAndUsuarioId(idCliente, idUsuario);


    if (cliente.isPresent()) {
      clienteRepository.deleteById(idCliente);
      return cliente.get();
    } else {
      return null;
    }
  }

  public Cliente atualizaCliente(Long id, DTOCriaCliente cliente) {

    Optional<Cliente> cliente1 = clienteRepository.findById(id);
    if (cliente1.isEmpty()) {
      return null;
    }
    Cliente cliente2 = cliente1.get();

    if (cliente.nome() != null && !cliente.nome().isBlank()) {
      cliente2.setNome(cliente.nome());
    }
    if (cliente.email() != null && !cliente.email().isBlank()) {
      cliente2.setEmail(cliente.email());
    }
    if (cliente.telefone() != null && !cliente.telefone().isBlank()) {
      cliente2.setTelefone(cliente.telefone());
    }
    if (cliente.cep() != null && !cliente.cep().isBlank()) {
      var endereco = obtemEndereco(cliente.cep());//neste método obtemEndereço pode fazer a lógica de bater no banco primeiro
      cliente2.setEndereco(endereco);
    }
    clienteRepository.save(cliente2);//olhar a notação transactional
    return cliente2;
  }

  public List<ClienteDTOOutput> listarClientes(Long idUsuario) {
    if (!usuarioRepository.existsById(idUsuario)) {
      return null;
    }
    List<Cliente> all = clienteRepository.findByUsuarioId(idUsuario);
    List<ClienteDTOOutput> clientes = all.stream().map(ClienteDTOOutput::new).toList();

    return clientes;
  }

  public List<Cliente> buscarPorFiltros(Long idUsuario, String uf, String cidade) {
    uf = uf.toUpperCase();
    cidade = cidade.toUpperCase();

    List<Cliente> clientes = clienteRepository.buscarPorFiltros(idUsuario, uf, cidade);

    return clientes;
  }

  public List<String> sugestoes(String palavra) {
    // region ----- Lista de Estados e Cidades ------

    List<String> palavras = List.of(
        "Acre",
        "Rio Branco", "Cruzeiro do Sul", "Sena Madureira", "Tarauacá", "Feijó",
        "Brasiléia", "Xapuri", "Plácido de Castro", "Mâncio Lima", "Rodrigues Alves",
        "Marechal Thaumaturgo", "Porto Acre", "Capixaba", "Assis Brasil", "Bujari",
        "Epitaciolândia", "Jordão", "Porto Walter", "Santa Rosa do Purus", "Manoel Urbano",

        "Alagoas",
        "Maceió", "Arapiraca", "Palmeira dos Índios", "Rio Largo", "Penedo",
        "União dos Palmares", "São Miguel dos Campos", "Campo Alegre", "Delmiro Gouveia", "Coruripe",
        "Marechal Deodoro", "Santana do Ipanema", "Atalaia", "Teotônio Vilela", "Girau do Ponciano",
        "Porto Calvo", "São Luiz do Quitunde", "Murici", "Pilar", "Maragogi",

        "Amapá",
        "Macapá", "Santana", "Laranjal do Jari", "Oiapoque", "Mazagão",
        "Tartarugalzinho", "Pedra Branca do Amapari", "Porto Grande", "Calçoene", "Ferreira Gomes",
        "Serra do Navio", "Cutias", "Itaubal", "Pracuúba", "Amapá",
        "Vitória do Jari", "Campina Grande", "Novo Horizonte", "Bailique", "Carmo do Macapá",


        "Amazonas",
        "Manaus", "Parintins", "Itacoatiara", "Manacapuru", "Coari",
        "Tabatinga", "Tefé", "Humaitá", "São Gabriel da Cachoeira", "Iranduba",
        "Maués", "Borba", "Autazes", "Careiro", "Benjamin Constant",
        "Eirunepé", "Fonte Boa", "Manicoré", "Novo Aripuanã", "Presidente Figueiredo",


        "Bahia",
        "Salvador", "Feira de Santana", "Vitória da Conquista", "Camaçari", "Itabuna",
        "Juazeiro", "Ilhéus", "Lauro de Freitas", "Jequié", "Teixeira de Freitas",
        "Barreiras", "Alagoinhas", "Porto Seguro", "Simões Filho", "Paulo Afonso",
        "Eunápolis", "Santo Antônio de Jesus", "Valença", "Luís Eduardo Magalhães", "Irecê",

        "Ceará",
        "Fortaleza", "Caucaia", "Juazeiro do Norte", "Maracanaú", "Sobral",
        "Crato", "Itapipoca", "Maranguape", "Iguatu", "Quixadá",
        "Quixeramobim", "Russas", "Canindé", "Pacatuba", "Tianguá",
        "Acaraú", "Icó", "Morada Nova", "Tauá", "Horizonte",

        "Distrito Federal",
        "Brasília", "Ceilândia", "Taguatinga", "Samambaia", "Planaltina",
        "Sobradinho", "Gama", "Guará", "Recanto das Emas", "Santa Maria",
        "São Sebastião", "Riacho Fundo", "Lago Sul", "Lago Norte", "Paranoá",
        "Núcleo Bandeirante", "Candangolândia", "Cruzeiro", "Park Way", "Sudoeste",

        "Espírito Santo",
        "Vitória", "Vila Velha", "Serra", "Cariacica", "Cachoeiro de Itapemirim",
        "Linhares", "São Mateus", "Guarapari", "Colatina", "Aracruz",
        "Viana", "Nova Venécia", "Barra de São Francisco", "Santa Teresa", "Domingos Martins",
        "Anchieta", "Afonso Cláudio", "Fundão", "Mimoso do Sul", "Baixo Guandu",

        "Goiás",
        "Goiânia", "Aparecida de Goiânia", "Anápolis", "Rio Verde", "Luziânia",
        "Águas Lindas de Goiás", "Valparaíso de Goiás", "Trindade", "Formosa", "Novo Gama",
        "Catalão", "Itumbiara", "Senador Canedo", "Jataí", "Planaltina",
        "Caldas Novas", "Cristalina", "Goianésia", "Mineiros", "Quirinópolis",

        "Maranhão",
        "São Luís", "Imperatriz", "São José de Ribamar", "Timon", "Caxias",
        "Codó", "Paço do Lumiar", "Açailândia", "Bacabal", "Balsas",
        "Barra do Corda", "Pinheiro", "Santa Inês", "Chapadinha", "Grajaú",
        "Zé Doca", "Itapecuru Mirim", "Coelho Neto", "Pedreiras", "Coroatá",

        "Mato Grosso",
        "Cuiabá", "Várzea Grande", "Rondonópolis", "Sinop", "Tangará da Serra",
        "Cáceres", "Sorriso", "Lucas do Rio Verde", "Primavera do Leste", "Barra do Garças",
        "Alta Floresta", "Pontes e Lacerda", "Nova Mutum", "Campo Verde", "Juína",
        "Colniza", "Guarantã do Norte", "Peixoto de Azevedo", "Campo Novo do Parecis", "Paranatinga",

        "Mato Grosso do Sul",
        "Campo Grande", "Dourados", "Três Lagoas", "Corumbá", "Ponta Porã",
        "Naviraí", "Nova Andradina", "Aquidauana", "Sidrolândia", "Paranaíba",
        "Maracaju", "Amambai", "Coxim", "Rio Brilhante", "Jardim",
        "Caarapó", "Chapadão do Sul", "São Gabriel do Oeste", "Ivinhema", "Bataguassu",

        "Minas Gerais",
        "Belo Horizonte", "Uberlândia", "Contagem", "Juiz de Fora", "Betim",
        "Montes Claros", "Ribeirão das Neves", "Uberaba", "Governador Valadares", "Ipatinga",
        "Sete Lagoas", "Divinópolis", "Santa Luzia", "Ibirité", "Poços de Caldas",
        "Patos de Minas", "Pouso Alegre", "Teófilo Otoni", "Barbacena", "Sabará",

        "Pará",
        "Belém", "Ananindeua", "Santarém", "Marabá", "Parauapebas",
        "Castanhal", "Abaetetuba", "Cametá", "Bragança", "Marituba",
        "Altamira", "Barcarena", "São Félix do Xingu", "Itaituba", "Redenção",
        "Tailândia", "Paragominas", "Breves", "Moju", "Capanema",

        "Paraíba",
        "João Pessoa", "Campina Grande", "Santa Rita", "Patos", "Bayeux",
        "Sousa", "Cajazeiras", "Cabedelo", "Guarabira", "Sapé",
        "Mamanguape", "Queimadas", "Esperança", "Pombal", "Monteiro",
        "Catolé do Rocha", "Alagoa Grande", "Itabaiana", "Solânea", "Pedras de Fogo",

        "Paraná",
        "Curitiba", "Londrina", "Maringá", "Ponta Grossa", "Cascavel",
        "São José dos Pinhais", "Foz do Iguaçu", "Colombo", "Guarapuava", "Paranaguá",
        "Apucarana", "Toledo", "Araucária", "Campo Largo", "Pinhais",
        "Umuarama", "Cambé", "Paranavaí", "Telêmaco Borba", "Castro",

        "Pernambuco",
        "Recife", "Jaboatão dos Guararapes", "Olinda", "Caruaru", "Petrolina",
        "Paulista", "Cabo de Santo Agostinho", "Camaragibe", "Garanhuns", "Vitória de Santo Antão",
        "Igarassu", "São Lourenço da Mata", "Abreu e Lima", "Santa Cruz do Capibaribe", "Goiana",
        "Serra Talhada", "Arcoverde", "Ouricuri", "Gravatá", "Belo Jardim",

        "Piauí",
        "Teresina", "Parnaíba", "Picos", "Piripiri", "Floriano",
        "Campo Maior", "Barras", "União", "Altos", "Esperantina",
        "José de Freitas", "Oeiras", "São Raimundo Nonato", "Pedro II", "Miguel Alves",
        "Luís Correia", "Valença do Piauí", "Piracuruca", "Cocal", "Bom Jesus",

        "Rio de Janeiro",
        "Rio de Janeiro", "São Gonçalo", "Duque de Caxias", "Nova Iguaçu", "Niterói",
        "Belford Roxo", "Campos dos Goytacazes", "São João de Meriti", "Petrópolis", "Volta Redonda",
        "Macaé", "Magé", "Itaboraí", "Cabo Frio", "Angra dos Reis",
        "Nova Friburgo", "Barra Mansa", "Teresópolis", "Mesquita", "Nilópolis",

        "Rio Grande do Norte",
        "Natal", "Mossoró", "Parnamirim", "São Gonçalo do Amarante", "Macaíba",
        "Ceará-Mirim", "Caicó", "Assu", "Currais Novos", "São José de Mipibu",
        "Santa Cruz", "Nova Cruz", "João Câmara", "Touros", "Extremoz",
        "Goianinha", "Apodi", "Pau dos Ferros", "Canguaretama", "Macau",

        "Rio Grande do Sul",
        "Porto Alegre", "Caxias do Sul", "Pelotas", "Canoas", "Santa Maria",
        "Gravataí", "Viamão", "Novo Hamburgo", "São Leopoldo", "Rio Grande",
        "Alvorada", "Passo Fundo", "Sapucaia do Sul", "Uruguaiana", "Santa Cruz do Sul",
        "Bagé", "Bento Gonçalves", "Erechim", "Guaíba", "Esteio",

        "Rondônia",
        "Porto Velho", "Ji-Paraná", "Ariquemes", "Vilhena", "Cacoal",
        "Rolim de Moura", "Jaru", "Guajará-Mirim", "Machadinho d'Oeste", "Pimenta Bueno",
        "Buritis", "Ouro Preto do Oeste", "Espigão d'Oeste", "Candeias do Jamari", "Nova Mamoré",
        "São Miguel do Guaporé", "Alto Paraíso", "Presidente Médici", "Costa Marques", "Cujubim",

        "Roraima",
        "Boa Vista", "Rorainópolis", "Caracaraí", "Mucajaí", "Alto Alegre",
        "Cantá", "Pacaraima", "Amajari", "Normandia", "Iracema",
        "Bonfim", "Uiramutã", "São Luiz", "Caroebe", "São João da Baliza",
        "Alto Alegre II", "Apiaú", "Tepequém", "Catrimani", "Nova Colina",

        "Santa Catarina",
        "Florianópolis", "Joinville", "Blumenau", "São José", "Chapecó",
        "Itajaí", "Criciúma", "Jaraguá do Sul", "Lages", "Balneário Camboriú",
        "Brusque", "Tubarão", "Palhoça", "Camboriú", "Concórdia",
        "Navegantes", "Caçador", "Rio do Sul", "Indaial", "Biguaçu",

        "São Paulo",
        "São Paulo", "Guarulhos", "Campinas", "São Bernardo do Campo", "Santo André",
        "Osasco", "São José dos Campos", "Ribeirão Preto", "Sorocaba", "Mauá",
        "São José do Rio Preto", "Mogi das Cruzes", "Santos", "Diadema", "Jundiaí",
        "Piracicaba", "Carapicuíba", "Bauru", "Itaquaquecetuba", "São Vicente",

        "Sergipe",
        "Aracaju", "Nossa Senhora do Socorro", "Lagarto", "Itabaiana", "São Cristóvão",
        "Estância", "Tobias Barreto", "Itabaianinha", "Simão Dias", "Nossa Senhora da Glória",
        "Propriá", "Boquim", "Capela", "Barra dos Coqueiros", "Poço Redondo",
        "Umbaúba", "Laranjeiras", "Porto da Folha", "Neópolis", "Japaratuba",

        "Tocantins",
        "Palmas", "Araguaína", "Gurupi", "Porto Nacional", "Paraíso do Tocantins",
        "Colinas do Tocantins", "Guaraí", "Dianópolis", "Tocantinópolis", "Miracema do Tocantins",
        "Formoso do Araguaia", "Augustinópolis", "Pedro Afonso", "Lagoa da Confusão", "Taguatinga",
        "Palmeirópolis", "Arraias", "Xambioá", "Nova Olinda", "Itacajá"
    );
    // endregion
    palavra = palavra.toLowerCase();

    String finalPalavra = palavra;
    return palavras.stream()
        .filter(cidade -> cidade.toLowerCase().contains(finalPalavra))
        .limit(10) // limitar quantidade de resultados
        .collect(Collectors.toList());

  }
}
