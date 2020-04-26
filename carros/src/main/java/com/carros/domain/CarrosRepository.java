package com.carros.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

//public interface CarrosRepository extends CrudRepository<Carro, Long>{
public interface CarrosRepository extends JpaRepository<Carro, Long>{

	List<Carro> findByTipo(String tipo);
}
