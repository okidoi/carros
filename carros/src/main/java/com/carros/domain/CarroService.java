package com.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.carros.domain.dto.CarroDTO;
import com.carros.domain.exception.ObjectNotFoundException;

//Retornará a lista de Carros do BD.

@Service
public class CarroService {
	
	@Autowired
	private CarrosRepository rep;
	
	public List<CarroDTO> getCarros(){
	
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
		
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
		
	}

	public CarroDTO getCarroById(Long id) {
		
		return rep.findById(id).map(CarroDTO::create).orElseThrow(()-> new ObjectNotFoundException("Carro não encontrado")); //Usando Lambda

		//Opção sem Lambda
		//Optional<Carro> carro = rep.findById(id);
		//return carro.map(c -> Optional.of(new CarroDTO(c))).orElse(null);
		
	}	
	

	public List<CarroDTO> getCarrosByTipo(String tipo) {
		
		return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO insert(Carro carro) {
		
		Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
		CarroDTO create = CarroDTO.create(rep.save(carro));
		return create;		
	}	
	
    public CarroDTO update(Carro carro, Long id) {
     
    	Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            // Atualiza o carro
            rep.save(db);

            return CarroDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    
    }
	
	public Carro save(Carro carro) {
		
		return rep.save(carro);
	
	}

	public void delete(Long id) {
		
		rep.deleteById(id);
	
	}

}
