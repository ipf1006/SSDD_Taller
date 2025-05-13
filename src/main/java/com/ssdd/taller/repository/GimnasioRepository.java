package com.ssdd.taller.repository;

import com.ssdd.taller.model.Gimnasio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GimnasioRepository extends JpaRepository<Gimnasio, Long> {

}