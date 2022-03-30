package com.example.springboot.repository;

import com.example.springboot.entity.Token;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TokenRepository extends CrudRepository<Token, Long>{

    @Query(value = "SELECT e.mt_Token FROM Mails_Token e WHERE e.mt_Token =:token AND e.mt_Fecha = DATEADD(mi, -30, Current_TimeStamp)", 
           nativeQuery = true)
    public String getToken(String token) throws Exception;
}