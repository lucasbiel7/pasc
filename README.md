# PASC

Compilador da linguagem PasC.

## Colaboradores

- *11725810*  Cleiton Ribeiro
- *11724814*  Lucas Gabriel de Souza Dutra
- *11722919*  Higor Barbosa

## Pré-requisitos

- JDK 16
- Maven 3.6

## Dúvidas

- Qual a necessidade de contar tabulação? Conta mais na coluna
- Não case sensitive e apenas para identificadores? Identificadores palavras reservadas
- Verdadeiro e falso não serão representados?
- **_Aspas não são tokes_** Significa que não devemos contabilizar-las nos tokens?
- A tabela de símbolos irá armazenar local da declaração? O que colocar para palavras reservadas?
- String vazia é permitido?


## Como compilar

`mvn clean install`

## Para executar

Ao compilar irá gerar o uma arquivo .jar na pasta target pasta utilizar `java -jar target/cpasc-1.0-SNAPSHOT-shaded.jar 'arquivo.pasc'`

## Autômato Determinístico

<p align="center"> 
    <a href="https://drive.google.com/file/d/1BXv7sQsgFsQNLskwITVXn2o4siJoxnmr/view?usp=sharing">
    <img src="media/automato.png" title="Autômato finito determinístico">
    </a>    
</p>


