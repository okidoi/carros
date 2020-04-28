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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.CarrosApplication;
import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosAPITest {

	@Autowired
	private TestRestTemplate rest;  //Responsável por fazer as requisições HTTP.
	
	private ResponseEntity<CarroDTO> getCarro(String url){
		
		//O retorno virá no tipo CarroDTO
		return rest.getForEntity(url, CarroDTO.class);
	}
	
	private ResponseEntity<List<CarroDTO>> getCarros(String url){
		return rest.exchange(url,
							HttpMethod.GET,
							null,
							new ParameterizedTypeReference<List<CarroDTO>>() {
							});
	}
	
	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Porshe");
		carro.setTipo("esportivos");
		
		//Insert
		ResponseEntity response = rest.postForEntity("/api/v1/carros", carro, null);  // O objeto carro passado será convertido para Json pela API e faz o post
		System.out.println(response);
		
		//Verifica se criou
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		//Buscar o objeto
		String location = response.getHeaders().get("location").get(0);
		CarroDTO c = getCarro(location).getBody();
		
		assertNotNull(c);
		assertEquals("Porshe", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		//Deletar o objeto
		rest.delete(location);
		assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
		
	}
	
	
	@Test
	public void testLista() {
		
		List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
		assertNotNull(carros);
		assertEquals(30, carros.size());
		
	}
	
	
	@Test
	public void testListaPorTipo() {
		
		assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
		assertEquals(10, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
		assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());
		
		assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/umTipoQueNaoExiste").getStatusCode());
	}
	
	
	@Test
	public void testGetOk() {
		
		ResponseEntity<CarroDTO> response = getCarro("/api/v1/carros/11");  //No banco H2 o id 11 é da Ferrari FF
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		CarroDTO c = response.getBody();
		assertEquals("Ferrari FF", c.getNome());
		
	}
	
	@Test
	public void testGetNoFound() {
		
		ResponseEntity response = getCarro("/api/v1/carros/1100"); //Código que não existe id.
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		
	}
	


}