package com.jrpbjr.agendamentofinanceiro.payload.service.impl;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.model.EstruturaModel;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;

public class OperacaoServiceImpl {

    public Double calcularTaxa(OperacaoModel operacaoModel){
        Double taxa = null;
        if(Tipo.A.equals(operacaoModel.getTipo())){
            taxa = calcularTaxacaoTipoA(operacaoModel);
        }

        return taxa;
    }

    private Double calcularTaxacaoTipoA(OperacaoModel operacaoModel){
        return (2 + (operacaoModel.getValorTransferencia() * 0.03)) ;
    }

}
