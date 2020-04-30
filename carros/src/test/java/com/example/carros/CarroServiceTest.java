package com.example.carros;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.carros.domain.exception.ObjectNotFoundException;

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
		c = service.getCarroById(id);
		assertNotNull(c);
		
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivo", c.getTipo());
		
		//Deletar o objeto
		service.delete(id);
		
		//Verifcar se deletou
		try {
			assertNull(service.getCarroById(id));
			fail("O carro não foi excluído");
			
		}catch(ObjectNotFoundException e) {
			//Ok. Se cair aqui está certo. 
		}
		
	}
	
	@Test
	public void testaLista() {
		
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30, carros.size());
	}
	
	
	@Test
	public void testaGet() {
		
		CarroDTO c = service.getCarroById(11L);
		assertNotNull(c);
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
