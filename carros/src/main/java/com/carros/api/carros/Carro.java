package com.carros.api.carros;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private String descricao;
    private String urlFoto; //Nome do campo no BD deve ser este também e não url_foto
    private String urlVideo;//Nome do campo no BD deve ser este também e não url_video
    private String latitude;
    private String longitude;
    



}

