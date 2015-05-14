package com.tienda.app;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

	Producto findById(int id);

	List<Producto> findByNameContaining(String name);
	
	List<Producto> findByCategoria(String categoria);

	@Query(value = "SELECT * FROM PRODUCTO", nativeQuery = true)
	List<Producto> selectAll();
	
	@Query(value = "SELECT * FROM PRODUCTO WHERE OFERTA > 0", nativeQuery = true)
	List<Producto> selectOfertas();
	
	@Query(value = "SELECT CATEGORIA FROM PRODUCTO GROUP BY CATEGORIA", nativeQuery = true)
	List<String> selectCategorias();
	

	

}
