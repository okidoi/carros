package com.carros.api.carros;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data //Lombok
public class CarroDTO {
	
	private Long id;
    private String nome;
    private String tipo;
    private String descricao;
    private String urlFoto;
    private String urlVideo;
    private String latitude;
    private String longitude;
	
	public static CarroDTO create(Carro c) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(c,  CarroDTO.class);
	}

	
	
}
