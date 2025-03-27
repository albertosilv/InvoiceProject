
# 📄 InvoiceProject - Backend

![GitHub repo size](https://img.shields.io/github/repo-size/albertosilv/InvoiceProject-back)
![GitHub](https://img.shields.io/github/license/albertosilv/InvoiceProject-back)

O **InvoiceProject** é um sistema robusto para gerenciamento de notas fiscais, desenvolvido utilizando **Java** com **Quarkus** para um backend leve e eficiente.

## ✨ Funcionalidades Principais

- ✅ Cadastro e gestão de notas fiscais
- ✅ Cadastro e gestão de produtos
- ✅ Cadastro e gestão de fornecedores
- ✅ API RESTful segura e eficiente
- ✅ Integração com banco de dados relacional
- ✅ Documentação da API com OpenAPI/Swagger

## 🛠️ Tecnologias Utilizadas

- **Backend**:
  - Java 21
  - Quarkus
  - Hibernate ORM com Panache
  - PostgreSQL
  - Flyway (controle de versão do banco de dados)
  - RESTEasy Jackson
  - OpenAPI/Swagger

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 21+
- Maven 3.8+
- PostgreSQL 15+

### Passo a Passo

1. **Clone o repositório**
   ```bash
   git clone https://github.com/albertosilv/InvoiceProject-back.git
   cd InvoiceProject-back
   ```

2. **Configure o banco de dados**
   - Crie um banco de dados PostgreSQL manualmente chamado `invoice_db` e configure os acessos no arquivo `src/main/resources/application.properties`:
     ```properties
      quarkus.http.port=4000
      quarkus.http.port-range=10
      quarkus.grpc.server.port=9000
      quarkus.grpc.server.port-range=10
      
      quarkus.http.cors=true
      quarkus.http.cors.origins=*
      quarkus.http.cors.methods=GET,POST,PUT,DELETE,OPTIONS
      quarkus.http.cors.headers=accept,content-type,origin,authorization
      
      quarkus.datasource.devservices.enabled=false
      quarkus.datasource.db-kind=postgresql
      quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/invoice
      quarkus.datasource.username=postgres
      quarkus.datasource.password=postgres
      quarkus.hibernate-orm.database.generation=update
      quarkus.hibernate-orm.log.sql=true
      
      spring.jackson.date-format=dd/MM/yyyy
      spring.jackson.time-zone=America/Sao_Paulo

     ```

3. **Compile e execute a aplicação**
   ```bash
   mvn compile quarkus:dev
   ```

4. **Acesse a API no navegador ou via Postman**
   ```
   http://localhost:4000/api
   ```
   - Documentação da API disponível em:
     ```
     http://localhost:4000/q/swagger-ui/
     ```

## 🗂️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/invoiceproject/
│   │   ├── controller/      # Controladores (Endpoints da API)
│   │   ├── dto/             # Data Transfer Objects (DTOs)
│   │   ├── exception/       # Exceções personalizadas
│   │   ├── model/           # Modelos de entidade (Banco de Dados)
│   │   ├── repository/      # Repositórios (Acesso ao banco de dados)
│   │   ├── service/         # Lógica de negócios
│   │   ├── config/          # Configurações do sistema
│   ├── resources/
│   │   ├── application.properties # Configurações do Quarkus
│   │   ├── db/migration/    # Scripts de migração Flyway
└── test/                   # Testes automatizados
```

### Detalhes das Pastas:

- **controller**: Contém os controladores da API que gerenciam os endpoints e as requisições HTTP.
- **dto**: Contém os Data Transfer Objects (DTOs) para transferir dados entre a camada de controle e a camada de serviço.
- **exception**: Contém as exceções personalizadas para o sistema.
- **model**: Contém as entidades que representam o banco de dados (modelos de dados).
- **repository**: Contém os repositórios responsáveis pela interação com o banco de dados.
- **service**: Contém a lógica de negócios e a implementação dos casos de uso do sistema.
- **config**: Contém as configurações globais, como segurança, banco de dados, etc.

## 📦 Scripts Úteis

| Comando                   | Descrição                                     |
|---------------------------|-----------------------------------------------|
| `mvn quarkus:dev`          | Inicia a aplicação em modo de desenvolvimento |
| `mvn package`              | Gera o JAR para produção                     |

## 🤝 Como Contribuir

1. Faça um fork do projeto
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas alterações (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a MIT License - veja o arquivo [LICENSE](LICENSE) para detalhes.

## ✉️ Contato

Albert Silva - [GitHub](https://github.com/albertosilv) - albertosilv@email.com

---

**Nota**: Certifique-se de ter o banco de dados PostgreSQL configurado antes de iniciar a API para garantir o funcionamento correto do sistema.
