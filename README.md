# Controle de Escolares
## Plataforma de Gestão escolar

<hr/>

Aplicação construída para entrega do TCC na Faculdade Universidade Nove de Julho.

### Tecnologias utilizadas

- Java 8
- Spring Boot
- Spring Security
- Spring Cache
- Spring Data JPA
- Postgres
- Frontend
  - Thymeleaf
  - HTML
  - CSS
  - Javascript

## Utilização

Aplicação publicada na plataforma [Heroku](https://heroku.com/). 
Para utilizar acesse o endereço abaixo:

https://goo.gl/dcEaXh

<b>Credenciais</b>:<br/>
User: admin@futuresystem.com.br <br/>
pass: 123

Como esta aplicação está utilizando um serviço gratuito do Heroku, pode ser que sua primeira request
irá inicializar o servidor gerando maior tempo de resposta, aguarde 1 minuto e tente novamente.

### Executando localmente

Para rodar localmente você deverá utilizar:

- Java 8
- Docker
- Docker Compose

Execute o container do banco de dados localmente executando este comando  na pasta raiz:
```
docker-compose up
```
Na IDE de sua preferência inclua este parametro em VM options:

```
-Dspring.profiles.active=dev
```
Com esta variável, você estará utilizando as variáveis de ambiente de dev
localizadas no arquivo `application-dev.properties`.

Ao executar a classe main do projeto, `Application.java`, ela invocará a classe
`ConstroiDadosDoSistema.java` que criará todos os dados randomicamente para o funcionamento
da aplicação.

## Aviso

Este projeto não está seguindo todas as normas, convenções e padrões
da plataforma Java, portanto, naturalmente, ao analisar o código você poderá
se deparar com algum anti pattern. Esta aplicação teve objetivo totalmente acadêmico
e não deverá ser utilizado para aprendizado ou replicação.