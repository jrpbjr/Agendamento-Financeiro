package com.jrpbjr.agendamentofinanceiro.payload.model.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


public class OperacaoDto implements Serializable {

    private static final long serialVersionUID = -8518778038388910646L;

    protected Integer id;
    @NotNull(message = "Conta de origem deve ser informada.")
    protected String contaOrigem;
    @NotNull(message = "Conta de destino deve ser informada.")
    protected String contaDestino;
    @NotNull(message = "Valor da transferência deve ser informado.")
    protected BigDecimal valorTransferencia;
    @NotNull(message = "Taxa deve ser informada.")
    protected BigDecimal taxa;
    @NotNull(message = "Data de agendamento deve ser informada.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    protected LocalDate dataAgendamento;
    @NotNull(message = "Tipo deve ser informado.")
    private Tipo tipo;

    public OperacaoDto() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.valorTransferencia = valorTransferencia;
    }

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

}
