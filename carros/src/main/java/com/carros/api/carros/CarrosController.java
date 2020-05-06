package com.carros.api.carros;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//Evitar tratamento de exceção nesta classe. Quanto mais limpa for a classe melhor.
//O tratamento de Exceção fica na ExceptionConfig e a exceção do tipo IllegalArgumentException será convertida em 
//BadRequest

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
	
	@Autowired
	private CarroService  service;

	@GetMapping()
	public ResponseEntity<List<CarroDTO>> get(@RequestParam(value ="page", defaultValue = "0") Integer page, 
 											  @RequestParam(value ="size", defaultValue = "10") Integer size ) {
		
		return ResponseEntity.ok(service.getCarros(PageRequest.of(page, size))); //PageRequest cria um Pageable
		
		//return new ResponseEntity<>(service.getCarros(), HttpStatus.OK); //mesmo que a linha de cima, porém mais verboso
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable("id") Long id){

		CarroDTO carro = service.getCarroById(id);
		return ResponseEntity.ok(carro);
		
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo,
										  @RequestParam(value ="page", defaultValue = "0") Integer page, 
										  @RequestParam(value ="size", defaultValue = "10") Integer size ){
		
		List<CarroDTO> carros = service.getCarrosByTipo(tipo,PageRequest.of(page, size));
		
		return carros.isEmpty()?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(carros);
				
	}
	
	
	@GetMapping("/search")
	public ResponseEntity search(@RequestParam("query") String query) {
	    List<CarroDTO> carros = service.search(query);
	    return carros.isEmpty() ?
	            ResponseEntity.noContent().build() :
	            ResponseEntity.ok(carros);
	}
	

 	@PostMapping
 	@Secured({"ROLE_ADMIN"})  //Somente usuário com o perfil de Admin poderá inserir carro neste caso
	public ResponseEntity post(@RequestBody Carro carro) {
		
			CarroDTO c = service.insert(carro);
			URI location = getUri(c.getId());
			return ResponseEntity.created(location).build();//Caso sucesso retorna um Status 201 (Created)  
	}
	
 	
 	//Monta a URL até o caminho /id
    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }	
	
		
	@PutMapping("/{id}")
	public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		
		carro.setId(id);
		CarroDTO c = service.update(carro, id);
		
		return c != null?
				ResponseEntity.ok(c) :
				ResponseEntity.notFound().build();
		
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		
		service.delete(id);		
		return ResponseEntity.ok().build();
				
		
	}
		
}
