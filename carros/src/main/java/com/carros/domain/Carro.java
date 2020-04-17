package com.carros.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Carro {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO) //O AUTO muitas vezes cria uma sequence que não queremos, e o IDENTITY cria a chave primária com a própria notação de "auto_increment" do MySQL.
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //mais apropriado para MySQL
	private Long id;
	
	private String nome;
	
	private String tipo;
	
	
	
	public Carro() {
		super();
	}
	public Carro(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
