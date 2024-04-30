package com.festivos.ec.decirdiasfestivos.dominio.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "festivo")
public class Festivos {

    @Id
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nombre")
    private String Nombre;

    @Column(name = "Dia")
    private Integer Dia;

    @Column(name = "Mes")
    private Integer Mes;

    @Column(name = "DiasPascua")
    private Integer DiasPascua;

    @Column(name = "IdTipo")
    private Integer IdTipo;

}