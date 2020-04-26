package com.carros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrosApplication.class, args);
	}

}





/**

=========================================================================================== 
OBS: INICIAR O BANCO DE DADOS MySQL carros.

===========================================================================================
 Para excluir um carro utilize o POSTMAN
 IMPORTANTE: TIPO DEL
 A URL é:
 http://localhost:8080/api/v1/carros/32
 Onde 32 é o id do Carro a ser excluído.
 Utilizar um Header com chave: Content-Type e valor: application/json
=========================================================================================== 
 
=========================================================================================== 
  Para incluir um carro utilize o POSTMAN
 IMPORTANTE: TIPO POST
 A URL é:
 http://localhost:8080/api/v1/carros
 Utilizar um Header com chave: Content-Type e valor: application/json 
 Em Body->raw inserir a seguinte informação 
 {
    "nome": "Nome do Carro a ser inserido",
    "tipo": "classicos"
 },
===========================================================================================
     
=========================================================================================== 
  Para atualizar um carro utilize o POSTMAN
 IMPORTANTE: TIPO PUT
 A URL é:
 http://localhost:8080/api/v1/carros/32 
 Onde 32 é o id do carro a ser atualizado
 Utilizar um Header com chave: Content-Type e valor: application/json 
 Em Body->raw inserir a seguinte informação 
 {
    "nome": "Carro Alterado!!!",
    "tipo": "luxo"
 },
===========================================================================================
   
=========================================================================================== 
  Para listar todos os carros cadastrados no banco utilize o POSTMAN
 IMPORTANTE: TIPO GET
 A URL é:
 http://localhost:8080/api/v1/carros
 
 ===========================================================================================
    
  
 */

