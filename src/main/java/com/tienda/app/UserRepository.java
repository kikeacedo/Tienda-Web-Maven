package com.tienda.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String name);
	List<User> findByNameContaining(String name);

}
