package com.jrpbjr.agendamentofinanceiro.payload.service.impl;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
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
        } else if (Tipo.C.equals(operacaoModel.getTipo())){
            taxa = calcularTaxacaoTipoC(operacaoModel);
        } else if (Tipo.D.equals(operacaoModel.getTipo())){
            taxa = calcularTaxacaoTipoD(operacaoModel);
        }
        return taxa;
    }

    public void validarTaxa(OperacaoModel operacaoMode) throws NegocioException{
        Double taxaReal = this.calcularTaxa(operacaoMode);

        if (taxaReal.doubleValue() != operacaoMode.getTaxa()){
            throw new NegocioException("Valor da taxa esta incorreta.");
        }

    }




    private Double calcularDiferencaDeDias(Date dataMenor, Date dataMaior) {
        return new Double((dataMaior.getTime() - dataMenor.getTime()) / 86400000L);
    }

    private Double calcularTaxacaoTipoA(OperacaoModel operacaoModel){
        return (2 + (operacaoModel.getValorTransferencia() * 0.03)) ;
    }

    private Double calcularTaxacaoTipoB(OperacaoModel operacaoModel){

        Double diferencaDeDias = this.calcularDiferencaDeDias(Calendar.getInstance().getTime(),operacaoModel.getDataAgendamento());

        Double taxa = null;

        if (diferencaDeDias <= 30) {
            taxa = (operacaoModel.getValorTransferencia() * 0.1);
        } else {
            taxa = (operacaoModel.getValorTransferencia() * 0.08);
        }
        return taxa;
    }

    private Double calcularTaxacaoTipoC(OperacaoModel operacaoModel) {
        Double diferencaDeDias = this.calcularDiferencaDeDias(Calendar.getInstance().getTime(), operacaoModel.getDataAgendamento());
        Double taxa = null;

        if (diferencaDeDias > 30) {
            taxa = (operacaoModel.getValorTransferencia() * 0.012);
        } else if ((diferencaDeDias > 25) && ( diferencaDeDias <= 30)){
            taxa = (operacaoModel.getValorTransferencia() * 0.021);
        } else if ((diferencaDeDias > 20) && (diferencaDeDias <= 25)){
            taxa = (operacaoModel.getValorTransferencia() * 0.043);
        } else if ((diferencaDeDias > 15) && (diferencaDeDias <= 20)){
            taxa = (operacaoModel.getValorTransferencia() * 0.054);
        } else if ((diferencaDeDias > 10) && (diferencaDeDias <= 15)){
            taxa = (operacaoModel.getValorTransferencia() * 0.067);
        } else if ((diferencaDeDias > 5) && (diferencaDeDias <= 10)){
            taxa = (operacaoModel.getValorTransferencia() * 0.074);
        } else if ((diferencaDeDias > 0) && (diferencaDeDias <= 5)){
            taxa = (operacaoModel.getValorTransferencia() * 0.083);
        }
        return taxa;
    }

    private Double calcularTaxacaoTipoD(OperacaoModel operacaoModel){
        Double taxa = null;
        Double valorTransferencia = operacaoModel.getValorTransferencia();

        if (valorTransferencia.doubleValue() <= 25000.00){
            taxa = this.calcularTaxacaoTipoA(operacaoModel);
        } else if((valorTransferencia.doubleValue() > 25000.00) && (valorTransferencia.doubleValue() <= 120000.00)){
            taxa = this.calcularTaxacaoTipoB(operacaoModel);
        } else if (valorTransferencia.doubleValue() > 120000.00) {
            taxa = this.calcularTaxacaoTipoC(operacaoModel);
        }
        return taxa;
    }


}
