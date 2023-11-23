package com.jrpbjr.agendamentofinanceiro.payload.service.impl;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;

import java.util.Calendar;
import java.util.Date;

public class OperacaoServiceImpl {

    public Double calcularTaxa(OperacaoModel operacaoModel){
        Double taxa = null;
        if(Tipo.A.equals(operacaoModel.getTipo())){
            taxa = calcularTaxacaoTipoA(operacaoModel);
        } else if (Tipo.B.equals(operacaoModel.getTipo())){
            taxa = calcularTaxacaoTipoB(operacaoModel);
        }
        return taxa;
    }

    private Double calcularDiferencaDeDias(Date dataMenor, Date dataMaior) {
        return new Double((dataMaior.getTime() - dataMenor.getTime()) / 86400000L);
    }

    private Double calcularTaxacaoTipoA(OperacaoModel operacaoModel){
        return (2 + (operacaoModel.getValorTransferencia() * 0.03)) ;
    }

    private Double calcularTaxacaoTipoB(OperacaoModel operacaoModel){

        Double diferencaDeDias = this.calcularDiferencaDeDias(Calendar.getInstance().getTime(),Calendar.getInstance().getTime());

        Double taxa = null;

        if (diferencaDeDias <= 30) {
            taxa = (operacaoModel.getValorTransferencia() * 0.1);
        } else {
            taxa = (operacaoModel.getValorTransferencia() * 0.08);
        }
        return taxa;
    }

}
