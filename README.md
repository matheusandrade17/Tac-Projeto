Sistema de Gestão Acadêmica - Tac-Projeto
Este repositório contém o microserviço aluno-service, responsável pelo gerenciamento de dados estudantis. O projeto foi desenvolvido com Spring Boot 3 e foca na separação de responsabilidades entre as camadas de domínio, serviços e interfaces de controle.

Stack Tecnológica
Linguagem: Java 17

Framework: Spring Boot 3

Persistência: Spring Data JPA

Produtividade: Lombok

Testes: JUnit 5, Mockito e AssertJ

Cobertura de Código: JaCoCo Maven Plugin

Estrutura de Qualidade e QA
A garantia de qualidade foi implementada através de testes unitários e de integração, utilizando mocks para isolamento de dependências e asserções fluentes para validação de regras de negócio.

Execução de Testes e Cobertura
Para validar as métricas de cobertura de código, execute o comando abaixo na pasta raiz do serviço:

PowerShell
cd services/aluno-service
.\mvnw.cmd clean test
Após a execução, o relatório detalhado em formato HTML será gerado no seguinte diretório:
target/site/jacoco/index.html

Organização do Projeto
services/aluno-service: Código fonte, configurações Maven e testes.

COBERTURA.md: Documentação técnica sobre as métricas de teste alcançadas.

.gitignore: Configuração de exclusão de artefatos de build, arquivos de IDE e binários.

Responsabilidades Técnicas
Desenvolvimento e Arquitetura: Implementação do núcleo do serviço e rotas da API.

QA e Documentação: Configuração da infraestrutura de testes, implementação do plugin de cobertura JaCoCo e padronização da documentação técnica
