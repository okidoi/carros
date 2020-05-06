package com.example.carros;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.CarrosApplication;
import com.carros.api.carros.Carro;
import com.carros.api.carros.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosAPITest extends BaseAPITest {


	@Autowired
	private TestRestTemplate rest;  //Responsável por fazer as requisições HTTP.
	
	private ResponseEntity<CarroDTO> getCarro(String url){
		
		return get(url, CarroDTO.class);
		//Comentado para usarmos agora o JWT
		//O retorno virá no tipo CarroDTO
		/*return rest
				.withBasicAuth("admin", "123")
				.getForEntity(url, CarroDTO.class);*/
	}
	
	private ResponseEntity<List<CarroDTO>> getCarros(String url){
		
        HttpHeaders headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<CarroDTO>>() {
                });		
		
		/*
		 * return rest .withBasicAuth("admin", "123") .exchange(url, HttpMethod.GET,
		 * null, new ParameterizedTypeReference<List<CarroDTO>>() { });
		 */
	}
	
	@Test
	public void testSave() {
	
		
        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        // Insert
        ResponseEntity response = post("/api/v1/carros", carro, null);// O objeto carro passado será convertido para Json pela API e faz o post
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        CarroDTO c = getCarro(location).getBody();

        assertNotNull(c);
        assertEquals("Porshe", c.getNome());
        assertEquals("esportivos", c.getTipo());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
		
	}
	
	
	@Test
	public void testLista() {
		
		List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
		assertNotNull(carros);
		assertEquals(10, carros.size());
		
		
		carros = getCarros("/api/v1/carros?page=0&size=30").getBody();
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
        assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());
		
	}
	
	@Test
	public void testGetNoFound() {
		
		ResponseEntity response = getCarro("/api/v1/carros/1100"); //Código que não existe id.
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		
	}
	


}
