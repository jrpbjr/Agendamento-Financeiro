package com.jrpbjr.agendamentofinanceiro.payload.service;

import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;

public interface  OperacaoService {

    public Double calcularTaxa(OperacaoModel operacaoModel);

    public void validarTaxa(OperacaoModel operacaoModel)
        throws NegocioException;

}
