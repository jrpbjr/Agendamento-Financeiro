package com.jrpbjr.agendamentofinanceiro.payload.service.impl;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import com.jrpbjr.agendamentofinanceiro.payload.repositories.OperacaoRepository;
import com.jrpbjr.agendamentofinanceiro.payload.service.OperacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class OperacaoServiceImpl implements OperacaoService {


    @Autowired
    private OperacaoRepository operacaoRepository;

    public BigDecimal calcularTaxa(OperacaoModel operacaoModel){
        BigDecimal taxa = null;

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

    public void validarTaxa(OperacaoModel operacaoModel) throws NegocioException{
        BigDecimal taxaReal = this.calcularTaxa(operacaoModel);

        if(!taxaReal.equals(operacaoModel.getTaxa())){
            throw new NegocioException("Valor da taxa esta incorreta.");
        }
    }

    private void validar(OperacaoModel operacaoModel) throws NegocioException{
        this.validarTaxa(operacaoModel);
    }


    private Double calcularDiferencaDeDias(LocalDate dataMenor, LocalDate   dataMaior) {
        Period periodo = Period.between(dataMenor, dataMaior);
        Double diff = (double) (periodo.getDays() + (periodo.getMonths() * 30) + (periodo.getYears() * 365));
        return diff;
    }


    private BigDecimal calcularTaxacaoTipoA(OperacaoModel operacaoModel){
        BigDecimal TaxacaoTipoA = new BigDecimal("0.03");
        return (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoA).add(new BigDecimal("2")).setScale(1));
    }

    private BigDecimal calcularTaxacaoTipoB(OperacaoModel operacaoModel){

        Double diferencaDeDias = this.calcularDiferencaDeDias(LocalDate.now(),operacaoModel.getDataAgendamento());

        BigDecimal taxa = null;
        BigDecimal TaxacaoTipoBMenorTrinta = new BigDecimal("0.1");
        BigDecimal TaxacaoTipoBMaiorTrinta = new BigDecimal("0.08");

        if (diferencaDeDias <= 30) {
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoBMenorTrinta));
        } else {
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoBMaiorTrinta));
        }
        return taxa.setScale(1);
    }

    private BigDecimal calcularTaxacaoTipoC(OperacaoModel operacaoModel) {
        Double diferencaDeDias = this.calcularDiferencaDeDias(LocalDate.now(), operacaoModel.getDataAgendamento());
        BigDecimal taxa = null;

        BigDecimal TaxacaoTipoCEntreZeroECinco = new BigDecimal("0.083");
        BigDecimal TaxacaoTipoCEntreCincoEDez = new BigDecimal("0.074");
        BigDecimal TaxacaoTipoCEntreDezEQuinze = new BigDecimal("0.067");
        BigDecimal TaxacaoTipoCEntreQuinzeEVinte = new BigDecimal("0.054");
        BigDecimal TaxacaoTipoCEntreVinteEVinteECinco = new BigDecimal("0.043");
        BigDecimal TaxacaoTipoCEntreVinteECincoETrinta = new BigDecimal("0.021");
        BigDecimal TaxacaoTipoCMaiorQueTrinta = new BigDecimal("0.012");


        if (diferencaDeDias > 30) {
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoCMaiorQueTrinta));
        } else if ((diferencaDeDias > 25) && ( diferencaDeDias <= 30)){
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoCEntreVinteECincoETrinta));
        } else if ((diferencaDeDias > 20) && (diferencaDeDias <= 25)){
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoCEntreVinteEVinteECinco));
        } else if ((diferencaDeDias > 15) && (diferencaDeDias <= 20)){
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoCEntreQuinzeEVinte));
        } else if ((diferencaDeDias > 10) && (diferencaDeDias <= 15)){
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoCEntreDezEQuinze));
        } else if ((diferencaDeDias > 5) && (diferencaDeDias <= 10)){
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoCEntreCincoEDez));
        } else if ((diferencaDeDias >= 0) && (diferencaDeDias <= 5)){
            taxa = (operacaoModel.getValorTransferencia().multiply(TaxacaoTipoCEntreZeroECinco));
        }
        return taxa.setScale(1);
    }

    private BigDecimal calcularTaxacaoTipoD(OperacaoModel operacaoModel){
        BigDecimal taxa = null;
        BigDecimal valorTransferencia = operacaoModel.getValorTransferencia();

        if (valorTransferencia.doubleValue() <= 25000.00){
            taxa = this.calcularTaxacaoTipoA(operacaoModel);
        } else if((valorTransferencia.doubleValue() > 25000.00) && (valorTransferencia.doubleValue() <= 120000.00)){
            taxa = this.calcularTaxacaoTipoB(operacaoModel);
        } else if (valorTransferencia.doubleValue() > 120000.00) {
            taxa = this.calcularTaxacaoTipoC(operacaoModel);
        }
        return taxa.setScale(1);
    }

    public OperacaoModel salvarOperacao(OperacaoModel operacaoModel)throws NegocioException {
        this.validar(operacaoModel);
        operacaoModel.setId(null);
        return operacaoRepository.save(operacaoModel);
    }


}
