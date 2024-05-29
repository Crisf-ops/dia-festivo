package com.festivos.ec.decirdiasfestivos.entidades;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo")
public class Tipo {

    @Id
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Tipo")
    private String Tipo;
}
