package com.jrpbjr.agendamentofinanceiro.payload.model;


import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.model.Dtos.OperacaoDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class OperacaoModel extends EstruturaModel{

    private static final long serialVersionUID = 1449321933124430700L;
    @Column
    private String contaOrigem;
    @Column
    private String contaDestino;
    @Column
    private BigDecimal valorTransferencia;
    @Column
    private BigDecimal taxa;
    @Column
    private LocalDate dataAgendamento;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public OperacaoModel(Integer id) {
        super();
    }

    public OperacaoModel() {
        super();
    }

    public OperacaoModel(Integer id, String contaOrigem, String contaDestino, BigDecimal valorTransferencia, BigDecimal taxa, LocalDate dataAgendamento, Tipo tipo) {
        super();
        this.id = id;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valorTransferencia = valorTransferencia;
        this.taxa = taxa;
        this.dataAgendamento = dataAgendamento;
        this.tipo = tipo;
    }

    public OperacaoModel(OperacaoDto operacaoDto) {
        super();
        this.id = operacaoDto.getId();
        this.contaOrigem = operacaoDto.getContaOrigem();
        this.contaDestino = operacaoDto.getContaDestino();
        this.valorTransferencia = operacaoDto.getValorTransferencia();
        this.taxa = operacaoDto.getTaxa();
        this.dataAgendamento = operacaoDto.getDataAgendamento();
        this.tipo = operacaoDto.getTipo();
    }

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

    public BigDecimal getValorTransferencia() {
        return valorTransferencia;
    }

    public void setValorTransferencia(BigDecimal valorTransferencia) {
        this.valorTransferencia = valorTransferencia;}

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
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
        return Objects.equals(contaOrigem, that.contaOrigem) && Objects.equals(contaDestino, that.contaDestino) && Objects.equals(valorTransferencia, that.valorTransferencia) && Objects.equals(taxa, that.taxa) && Objects.equals(dataAgendamento, that.dataAgendamento) && tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contaOrigem, contaDestino, valorTransferencia, taxa, dataAgendamento, tipo);
    }
}
