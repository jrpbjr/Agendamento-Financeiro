package com.jrpbjr.agendamentofinanceiro.payload.model;

import javax.persistence.*;
import java.io.Serializable;
@MappedSuperclass
public class EstruturaModel implements Serializable {
    private static final long serialVersionUID = 5390409768131104604L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected  Integer id;

    public EstruturaModel() {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
