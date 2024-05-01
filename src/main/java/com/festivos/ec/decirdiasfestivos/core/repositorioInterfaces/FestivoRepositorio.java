package com.festivos.ec.decirdiasfestivos.core.repositorioInterfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festivos.ec.decirdiasfestivos.entidades.Festivos;

@Repository
public interface FestivoRepositorio extends JpaRepository<Festivos, Integer> {
}
