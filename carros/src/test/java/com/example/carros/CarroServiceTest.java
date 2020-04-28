package com.example.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.CarrosApplication;
import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarroServiceTest {

	@Autowired
	private CarroService service;
	
	@Test
	public void testeInsercaoCarro() {
		
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivo");
		
		
		CarroDTO c = service.insert(carro);		
		Long id = c.getId();
		
		assertNotNull(c);
		
		//Buscar o objeto
		Optional<CarroDTO> op = service.getCarroById(id);
		assertTrue(op.isPresent());
		
		c = op.get();
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivo", c.getTipo());
		
		//Deletar o objeto
		service.delete(id);
		
		//Verifcar se deletou
		assertFalse(service.getCarroById(id).isPresent());
		
	}
	
	@Test
	public void testaLista() {
		
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30, carros.size());
	}
	
	
	@Test
	public void testaGetById() {
		
		Optional<CarroDTO> op = service.getCarroById(11L);
		assertTrue(op.isPresent());
		
		CarroDTO c = op.get();		
		assertEquals("Ferrari FF", c.getNome());		
		
	}	
	
	
	@Test
	public void testaListaPorTipo() {
		
		
		
		assertEquals(10, service.getCarrosByTipo("classicos").size());	
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());

		assertEquals(0, service.getCarrosByTipo("tipoQueNaoExiste").size());
		
	}		

}