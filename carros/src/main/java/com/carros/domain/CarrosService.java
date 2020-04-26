package com.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.carros.domain.dto.CarroDTO;

//Retornará a lista de Carros do BD.

@Service
public class CarrosService {
	
	@Autowired
	private CarrosRepository rep;
	
	
	/*public List<Carro> getCarrosFake(){
		List<Carro> carros = new ArrayList<>();
		carros.add(new Carro(1L, "Fusca"));
		carros.add(new Carro(2L, "Brasilia"));
		carros.add(new Carro(3L, "Chevette"));
		return carros;
	}*/
	
	public List<CarroDTO> getCarros(){
		
		//Manualmente (sem Lambda)
		
		//	List<CarroDTO> list = new ArrayList<>();
		//	for (Carro c : carros) {
		//		list.add(new CarroDTO(c));
		//	}  
		
		//Converte para uma list de CarroDTO
		
		//O findAll retorna uma lista de carros.
		//Chamamos a o método stream pra mapear essa lista. 
		//Estamos percorrendo carro por carro (em CarroDTO:new) e criando um CarroDTO
		//Depois geramos uma nova lista de CarroDTO
		
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

		
	}

	public Optional<CarroDTO> getCarroById(Long id) {
		return rep.findById(id).map(CarroDTO::create); //Usando Lambda
		//Opção sem Lambda
		//Optional<Carro> carro = rep.findById(id);
		//return carro.map(c -> Optional.of(new CarroDTO(c))).orElse(null);
		
	}	
	

	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());

	}

	public CarroDTO insert(Carro carro) {
		
		Assert.notNull(carro.getId(), "Não foi possível inserir o registro");
		
		return CarroDTO.create(rep.save(carro));
		
	}

	
	/*
	public CarroDTO update(Carro carro, Long id) {
		
		Assert.notNull(id, "Não foi possível atualizar o registro");
		
		//Busca o carro no banco de dados
//		
//		  Optional< Carro> optional = getCarroById(id); //abaixo uma linha que seria a
//		  outra opção if(optional.isPresent()) { Carro db = optional.get();
//		  db.setNome(carro.getNome()); db.setTipo(carro.getTipo());
//		  System.out.println("Carro id " + db.getId());
//		  
//		  //Atualiza o carro rep.save(db);
//		  
//		 	return db;
//		  
//		  }else {
//		  
//		  throw new RuntimeException("Não foi possível atualiza o registro"); }
//		 
		
		return getCarroById(id).map(db->{

			//copiar as propriedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			
			//Atualiza o carro
			rep.save(db);
			return db;
			
			
		}).orElseThrow(()->new RuntimeException("Não foi possível atualizar o registro"));
		
		
		
	}*/
	
	public Carro save(Carro carro) {
		return rep.save(carro);
	}

	public void delete(Long id) {
		
		if(getCarroById(id).isPresent()) {
			rep.deleteById(id);
		}
		
	}


}
