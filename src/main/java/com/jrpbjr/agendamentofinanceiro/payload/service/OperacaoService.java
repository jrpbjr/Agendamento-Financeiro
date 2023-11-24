package com.jrpbjr.agendamentofinanceiro.payload.service;

import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import org.springframework.stereotype.Service;


public interface  OperacaoService {

    public Double calcularTaxa(OperacaoModel operacaoModel);

    public void validarTaxa(OperacaoModel operacaoModel)
        throws NegocioException;

}
