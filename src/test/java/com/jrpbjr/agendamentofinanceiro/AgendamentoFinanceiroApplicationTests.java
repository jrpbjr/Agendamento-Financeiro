package com.jrpbjr.agendamentofinanceiro;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.jrpbjr.agendamentofinanceiro.payload.service.OperacaoService;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class AgendamentoFinanceiroApplicationTests {

	@Autowired
	private OperacaoService operacaoService;

	@Test
	void contextLoads() {
	}

	@Test
	public void taxaTipoATest() throws NegocioException{
		OperacaoModel operacaoModel = this.criarAgendamento(new Date(), Tipo.A, 100.00);
		operacaoModel.setTaxa(this.operacaoService.calcularTaxa(operacaoModel));
		this.operacaoService.validarTaxa(operacaoModel);
		Assertions.assertEquals(new Double(2 + (operacaoModel.getValorTransferencia() * 0.03)),operacaoModel.getTaxa());


	}

	private Date adicionarDia(Date date, int qtdDias) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		calendario.add(Calendar.DAY_OF_MONTH, qtdDias);
		return calendario.getTime();
	}

	private OperacaoModel criarAgendamento(Date dataAgendamento, Tipo tipo, Double valorTransferencia) {
		OperacaoModel operacaoModel = new OperacaoModel();
		operacaoModel.setContaDestino("000000");
		operacaoModel.setValorTransferencia(valorTransferencia);
		operacaoModel.setDataAgendamento(dataAgendamento);
		operacaoModel.setTipo(tipo);
		return operacaoModel;
	}

}
