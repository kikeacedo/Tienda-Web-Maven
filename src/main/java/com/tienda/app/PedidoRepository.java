package com.tienda.app;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

	Pedido findById(int id);

	List<Pedido> findByUser(int user);
	
	List<Pedido> findByEstado(Estado estado);
	
	
	

	@Query(value = "SELECT * FROM PEDIDO", nativeQuery = true)
	List<Pedido> selectAll();

	@Query(value = "SELECT * FROM PEDIDO WHERE ESTADO = ENTREGADO", nativeQuery = true)
	List<Pedido> selectFinalizados();
}
