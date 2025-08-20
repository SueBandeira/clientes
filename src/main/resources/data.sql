-- Limpar tabelas na ordem correta por causa das FK
TRUNCATE TABLE public.cliente RESTART IDENTITY CASCADE;
TRUNCATE TABLE public.endereco RESTART IDENTITY CASCADE;
TRUNCATE TABLE public.usuario RESTART IDENTITY CASCADE;

-- Inserir usuários
INSERT INTO public.usuario (nome, senha) VALUES
('Maria Silva', 'senha123'),
('João Pereira', 'senha123'),
('Ana Costa', 'senha123'),
('Carlos Souza', 'senha123');

-- Inserir endereços
INSERT INTO public.endereco (bairro, cep, cidade, complemento, estado, logradouro, uf) VALUES
('Centro', '01001-000', 'São Paulo', 'Apto 101', 'São Paulo', 'Av. Paulista', 'SP'),
('Copacabana', '22070-002', 'Rio de Janeiro', 'Casa 12', 'Rio de Janeiro', 'Avenida Atlântica', 'RJ'),
('Savassi', '30140-110', 'Belo Horizonte', 'Apto 301', 'Minas Gerais', 'Praça Sete de Setembro', 'MG'),
('Barra', '40020-000', 'Salvador', 'Casa 4', 'Bahia', 'Av. Oceânica', 'BA'),
('Moema', '04077-020', 'São Paulo', 'Apto 201', 'São Paulo', 'Rua Normandia', 'SP'),
('Ipanema', '22410-003', 'Rio de Janeiro', 'Cobertura 502', 'Rio de Janeiro', 'Rua Visconde de Pirajá', 'RJ'),
('Lourdes', '30150-160', 'Belo Horizonte', 'Casa 8', 'Minas Gerais', 'Rua da Bahia', 'MG'),
('Rio Vermelho', '41940-000', 'Salvador', 'Apto 45', 'Bahia', 'Rua Fonte do Boi', 'BA'),
('Pinheiros', '05422-000', 'São Paulo', 'Casa 20', 'São Paulo', 'Rua dos Pinheiros', 'SP'),
('Botafogo', '22250-040', 'Rio de Janeiro', 'Apto 602', 'Rio de Janeiro', 'Rua Voluntários da Pátria', 'RJ'),
('Pampulha', '31365-450', 'Belo Horizonte', 'Casa 15', 'Minas Gerais', 'Avenida Fleming', 'MG'),
('Itapuã', '41610-020', 'Salvador', 'Casa 7', 'Bahia', 'Avenida Dorival Caymmi', 'BA'),
('Vila Mariana', '04112-000', 'São Paulo', 'Apto 305', 'São Paulo', 'Rua Domingos de Morais', 'SP'),
('Leblon', '22430-060', 'Rio de Janeiro', 'Cobertura 701', 'Rio de Janeiro', 'Avenida Delfim Moreira', 'RJ'),
('Funcionários', '30120-010', 'Belo Horizonte', 'Casa 19', 'Minas Gerais', 'Rua Gonçalves Dias', 'MG'),
('Stella Maris', '41600-020', 'Salvador', 'Casa 22', 'Bahia', 'Rua Professor Souza Brito', 'BA');



-- Inserir clientes
INSERT INTO public.cliente (email, nome, telefone, endereco_id, usuario_id) VALUES
('fernanda.lima@email.com', 'Fernanda Lima', '11999990001', 1, 1),
('paulo.santos@email.com', 'Paulo Santos', '11999990002', 2, 1),
('juliana.rocha@email.com', 'Juliana Rocha', '11999990003', 3, 1),
('marcos.almeida@email.com', 'Marcos Almeida', '11999990004', 4, 1),
('beatriz.ferreira@email.com', 'Beatriz Ferreira', '21999990001', 5, 2),
('lucas.oliveira@email.com', 'Lucas Oliveira', '21999990002', 6, 2),
('camila.mendes@email.com', 'Camila Mendes', '21999990003', 7, 2),
('rafael.cardoso@email.com', 'Rafael Cardoso', '21999990004', 8, 2),
('patricia.ribeiro@email.com', 'Patrícia Ribeiro', '31999990001', 9, 3),
('andre.nascimento@email.com', 'André Nascimento', '31999990002', 10, 3),
('sabrina.martins@email.com', 'Sabrina Martins', '31999990003', 11, 3),
('eduardo.azevedo@email.com', 'Eduardo Azevedo', '31999990004', 12, 3),
('tatiane.goncalves@email.com', 'Tatiane Gonçalves', '71999990001', 13, 4),
('fabio.lima@email.com', 'Fábio Lima', '71999990002', 14, 4),
('larissa.souza@email.com', 'Larissa Souza', '71999990003', 15, 4),
('gustavo.fernandes@email.com', 'Gustavo Fernandes', '71999990004', 16, 4);





