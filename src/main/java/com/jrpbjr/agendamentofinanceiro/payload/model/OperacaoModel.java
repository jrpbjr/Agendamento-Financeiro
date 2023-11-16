package com.jrpbjr.agendamentofinanceiro.payload.model;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;

import java.util.Date;
import java.util.Objects;


public class OperacaoModel extends EstruturaModel{

    private static final long serialVersionUID = 1449321933124430700L;

    private String contaOrigem;
    private String contaDestino;
    private Double ValorTransferencia;
    private Double taxa;
    private Date dataAgendamento;
    private Tipo tipo;

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public Double getValorTransferencia() {
        return ValorTransferencia;
    }

    public void setValorTransferencia(Double valorTransferencia) {
        ValorTransferencia = valorTransferencia;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperacaoModel that = (OperacaoModel) o;
        return Objects.equals(contaOrigem, that.contaOrigem) && Objects.equals(contaDestino, that.contaDestino) && Objects.equals(ValorTransferencia, that.ValorTransferencia) && Objects.equals(taxa, that.taxa) && Objects.equals(dataAgendamento, that.dataAgendamento) && tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contaOrigem, contaDestino, ValorTransferencia, taxa, dataAgendamento, tipo);
    }
}
