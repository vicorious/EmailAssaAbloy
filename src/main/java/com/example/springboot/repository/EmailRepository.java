package com.example.springboot.repository;

import com.example.springboot.entity.Email;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface EmailRepository extends CrudRepository<Email, Long>{
	
	@Query(value = "SELECT * FROM Notificaciones where n_Origen LIKE ?1 ", 
			countQuery = ("SELECT * FROM Notificaciones where n_Origen LIKE ?1 "), 
			nativeQuery = true)
	Page<Email> listarNotificaciones(String origen, Pageable pageable);
	
}
