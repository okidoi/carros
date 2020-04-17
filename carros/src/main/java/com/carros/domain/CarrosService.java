package com.carros.domain;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
	
	public Iterable<Carro> getCarros(){
		return rep.findAll();
	}

	public Optional<Carro> getCarroById(Long id) {
		return rep.findById(id);
	}	
	

	public Iterable<Carro> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo);
	}

	public Carro insert(Carro carro) {
		return rep.save(carro);
		
	}

	public Carro update(Carro carro, Long id) {
		
		Assert.notNull(id, "Não foi possível atualizar o registro");
		
		//Busca o carro no banco de dados
		/*
		 * Optional< Carro> optional = getCarroById(id); //abaixo uma linha que seria a
		 * outra opção if(optional.isPresent()) { Carro db = optional.get();
		 * db.setNome(carro.getNome()); db.setTipo(carro.getTipo());
		 * System.out.println("Carro id " + db.getId());
		 * 
		 * //Atualiza o carro rep.save(db);
		 * 
		 * return db;
		 * 
		 * }else {
		 * 
		 * throw new RuntimeException("Não foi possível atualiza o registro"); }
		 */
		
		return getCarroById(id).map(db->{

			//copiar as propriedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			
			//Atualiza o carro
			rep.save(db);
			return db;
			
			
		}).orElseThrow(()->new RuntimeException("Não foi possível atualizar o registro"));
		
		
		
	}


}
