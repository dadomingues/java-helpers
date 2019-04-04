[![pipeline status](https://git.serpro/29988427808/autoriza/badges/master/pipeline.svg)](https://git.serpro/29988427808/autoriza/commits/master)
[![coverage report](https://git.serpro/29988427808/autoriza/badges/master/coverage.svg)](https://git.serpro/29988427808/autoriza/commits/master)


# Autoriza
Aplicativos e portal implementam interface para solicitar uma autorização ou incluir uma autorização prévia ao evento (como na versão inicial estagnada na homologação)
Ao realizar a operação que necessita de autorização, o usuário informa um PIN de validação
PIN é um conjunto de 6 números gerado aleatoriamente para a autorização.
o autorizador deve passar esse PIN ao executor da operação extra sistema
Uma autorização tem validade predeterminada (24h)
Possui apenas interface de apoio interna ao Serpro.
Lei de Acesso à Informação

   ![](docs/fluxo.png)

# Documentação
 - [Swgger da API](https://autorizades.denatransso.estaleiro.serpro.gov.br/autoriza/swagger-ui.html)
 - [Histórias de usuário](https://29988427808.gitpages.serpro/autoriza/) 

# Ambientes

- [Desenvolvimento](https://autorizades.denatransso.estaleiro.serpro.gov.br/autoriza/)
- [Homologação](https://autorizahom.denatransso.estaleiro.serpro.gov.br/autoriza/)
- [Produção](https://autoriza.denatransso.estaleiro.serpro.gov.br/autoriza/)

# Banco de Desenvolvimento

[Temporariamente]

- *Usuário* : postgres
- *Senha* : ysgDByb8DbWsagKpGwQkaxhgekjddGjAExJm
- *Database* : autorizadb 
- *schema* : public

# MER
  ![](docs/mer.png)
  
  - Tabela Autorização: Tabela onde ficarão as autorizações
  - Tabela Termo: Guarda termos de uso/responsábilidade para serem apresentados antes de autorizador autorizar
  - Tabela Sistema: Guarda os sistemas que usarão o autoriza
  - Tabela Token: Um sistema poderá ter vários tokens que serão validados a cada requisição
  - Tabela Transação: Uma transação é o motivo pelo qual o sistema chama o autoriza
  
#Links Úteis
 - [Cucumber-html-reporter](https://github.com/gkushang/cucumber-html-reporter)
 - [Imagem Docker com Maven e Node](https://hub.docker.com/r/jimador/docker-jdk-8-maven-node/) 
  
      