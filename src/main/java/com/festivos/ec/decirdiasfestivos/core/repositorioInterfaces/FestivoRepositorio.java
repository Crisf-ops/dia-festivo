package com.festivos.ec.decirdiasfestivos.core.repositorioInterfaces;

import com.festivos.ec.decirdiasfestivos.dominio.entidades.Festivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivoRepositorio extends JpaRepository<Festivos, Integer> {
}
