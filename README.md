# gerenciamentoDePessoas
API REST que permite realizar operações CRUD sobre a entidade Pessoa e de leitura e criação sobre Endereço.

# A aplicação
Trata-se de programa criado em Java 11 usando Spring 2.7.7 que, através de requisições HTTP, realiza operações sobre as entidades Pessoa e Endereco, devolvendo Json e HTTPStatus.

# O uso
Para utilizar a aplicação, pode-se empregar qualquer IDE do mercado. Ela está pré-configurada no arquivo application.properties para conectar-se ao DB H2, mas pode ser reconfigurada para interagir com qualquer outro DB relacional. Para realizar as requisições HTTP e consumir a API, pode-se usar de programas como Postman, uma aplicação de Front-end ou até mesmo um app android, uma vez que a aplicação segue o padrão REST.

# As entidades
A aplicação é composta por duas entidades, Pessoa e Endereco. Pessoa possui 4 atributos: matricula (PK do tipo Long gerada automaticamente no DB), nome. dataNascimento (LocalDate) e uma lista das instâncias Endereco que possui. Endereco possui 7 atributos: id (PK do tipo Long gerada automaticamente no DB), logradouro, cep, numero, cidade, principal e a instância de Pessoa que a possui. O atributo boolean "principal" representa se o endereço é o principal da Pessoa (valor = "true") ou não (valor = "false").
Assim, as duas entidades possui um relacionamento oneToMany, no qual uma instância de Pessoa pode ter de Zero a várias instâncias de Endereco.

# O Mapeamento
## PessoaController
### Método POST, http://{enderecoDoHost}/pessoas
Função: Cria uma instância de Pessoa e persiste no DB. Recebe um objeto PessoaDto composto por uma string nome e outra data, sendo que nome será aceito se contiver apenas letras e a data seguir o padrão dd/MM/yyyy. Ambos devem ser preenchidos. Devolve a instância criada em formato Json com matricula atribuída pelo DB e HTTPStatus 201. Exemplo de corpo que recebe:
{
    "nome": "Geralt",
    "data": "30/06/1995"
}

### Método PUT, http://{enderecoDoHost}/pessoas/{matricula}
Função: Atualiza a instância de Pessoa especificada e persiste no DB. A especificação ocorre através do número de matricula passado como PathVariable. Recebe um objeto PessoaDto composto por uma string nome e outra data, sendo que nome será aceito se contiver apenas letras e a data seguir o padrão dd/MM/yyyy. Ambos devem ser preenchidos. Devolve a instância atualizada em formato Json e HTTPStatus 200. Recebe o mesmo formato de corpo acima.

### Método GET, http://{enderecoDoHost}/pessoas/{matricula}
Função: Busca a instância de Pessoa especificada. A especificação ocorre através do número de matricula passado como PathVariable. Devolve a instância buscada em formato Json e HTTPStatus 200.

### Método GET, http://{enderecoDoHost}/pessoas/
Função: Busca todas as instâncias de Pessoa. Devolve todas as instâncias de Pessoa em formato Json e HTTPStatus 200.

### Método DELETE, http://{enderecoDoHost}/pessoas/{matricula}
Função: Deleta a instância de Pessoa especificada. A especificação ocorre através do número de matricula passado como PathVariable. Tem como efeito cascada deletar também as instâncias de Endereco que Pessoa possuia. Devolve a mensagem "Pessoa deletada!" e HTTPStatus 200.

## EnderecoController
### Método POST, http://{enderecoDoHost}/enderecos/{matricula}
Função: Recebe uma instância de Endereco, liga ela à instância de Pessoa especificada e persiste no DB. A especificação ocorre através do número de matricula passado como PathVariable. Recebe um objeto Endereco, sendo que cep e numero apenas serão aceitos se contiverem apenas numerais. Todos devem ser preenchidos com exceção do principal que, caso vazio, será registrado como "false". Caso já haja uma instância de Endereco marcada como principal para uma Pessoa e crie-se uma nova, esta substituirá a antiga. Devolve a instância criada em formato Json com id atribuído pelo DB e HTTPStatus 201. Exemplo de corpo que recebe:
{
    "logradouro": "Rua 50",
    "cep": "788",
    "numero": "8779",
    "cidade": "Recife",
    "principal": true
}
### Método GET, http://{enderecoDoHost}/enderecos/{matricula}
Função: Busca todas as instâncias de Endereco possuidas pela instância de Pessoa especificada. A especificação ocorre através do número de matricula passado como PathVariable. Devolve a lista de todas as instâncias da Endereco referente à Pessoa em formato Json e HTTPStatus 200.

### Método GET, http://{enderecoDoHost}/enderecos/principal/{matricula}
Função: Busca a instância de Endereco marcada como principal relativamente à Pessoa especificada. A especificação ocorre através do número de matricula passado como PathVariable. Devolve a instância de Endereco marcada como principal relativamente à Pessoa especificada em formato Json e HTTPStatus 200.

# Validação
## Valores devem ser preenchidos
Todos os inputs, com exceção de "principal", estão marcados com a anotação NotBlank e irão retornar mensagem caso não sejam preenchidos. Caso "principal" não seja preenchido, será registrado como false.

## Restrições de padrão
Os atributos nome, data, cep e numero estão marcados com a anotação @Pattern e irão retornar mensagem caso o formato não seja respeitado. Nome deverá conter apenas letras, data deve ser informada no formato dd/MM/yyyy, cep e numero devem conter apenas numerais.

## Tratamento de exceções
Os controladores possuem exceptionHandlers para as seguintes exceções: NoSuchElementException, HttpMessageNotReadableException, IllegalArgumentException e, no caso do PessoaController, DateTimeParseException.
