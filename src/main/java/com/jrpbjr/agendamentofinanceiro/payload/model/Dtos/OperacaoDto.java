package com.jrpbjr.agendamentofinanceiro.payload.model.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


public class OperacaoDto implements Serializable {

    private static final long serialVersionUID = -8518778038388910646L;

    protected Integer id;
    @NotNull(message = "Conta de origem deve ser informada.")
    protected String contaOrigem;
    @NotNull(message = "Conta de destino deve ser informada.")
    protected String contaDestino;
    @NotNull(message = "Valor da transferência deve ser informado.")
    protected Double valorTransferencia;
    @NotNull(message = "Taxa deve ser informada.")
    protected Double taxa;
    @NotNull(message = "Data de agendamento deve ser informada.")
    @JsonFormat(pattern="yyyy-MM-dd")
    protected Date dataAgendamento;
    @NotNull(message = "Tipo deve ser informado.")
    private Tipo tipo;

    public OperacaoDto() {
        super();
    }

    public OperacaoDto(OperacaoModel operacaoModel) {
        super();
        this.id = operacaoModel.getId();
        this.contaOrigem = operacaoModel.getContaOrigem();
        this.contaDestino = operacaoModel.getContaDestino();
        this.valorTransferencia = operacaoModel.getValorTransferencia();
        this.taxa = operacaoModel.getTaxa();
        this.dataAgendamento = operacaoModel.getDataAgendamento();
        this.tipo = operacaoModel.getTipo();
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

    public Double getValorTransferencia() {
        return valorTransferencia;
    }

    public void setValorTransferencia(Double valorTransferencia) {
        this.valorTransferencia = valorTransferencia;
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

}
