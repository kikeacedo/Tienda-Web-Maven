package com.tienda.app;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ImagenRepository extends CrudRepository<Imagen, Long> {

	List<Imagen> findByProducto(int producto);


	@Query(value = "SELECT * FROM IMAGEN", nativeQuery = true)
	List<Imagen> selectAll();
}
