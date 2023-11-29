package com.jrpbjr.agendamentofinanceiro.payload.service;
import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;

import java.math.BigDecimal;


public interface  OperacaoService {

    public BigDecimal calcularTaxa(OperacaoModel operacaoModel);

    public void validarTaxa(OperacaoModel operacaoModel)
        throws NegocioException;

    public OperacaoModel salvarOperacao(OperacaoModel operacaoModel)throws NegocioException;
}
