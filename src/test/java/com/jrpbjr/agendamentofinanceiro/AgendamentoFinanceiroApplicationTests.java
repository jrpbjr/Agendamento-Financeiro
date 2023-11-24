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

	@Test
	public void taxaTipoBTest() throws NegocioException {
		Date dataAte30Dias = this.adicionarDia( new Date() , 15);
		Date dataDemais = this.adicionarDia( new Date() , 35);

		OperacaoModel agendamentoAte30Dias = this.criarAgendamento(dataAte30Dias, Tipo.B, 100.00);
		OperacaoModel agendamentoDemais = this.criarAgendamento(dataDemais, Tipo.B, 100.00);

		agendamentoAte30Dias.setTaxa( this.operacaoService.calcularTaxa(agendamentoAte30Dias) );
		agendamentoDemais.setTaxa( this.operacaoService.calcularTaxa(agendamentoDemais) );

		this.operacaoService.validarTaxa(agendamentoAte30Dias);
		this.operacaoService.validarTaxa(agendamentoDemais);

		Assertions.assertEquals(new Double(agendamentoAte30Dias.getValorTransferencia() * 0.1), agendamentoAte30Dias.getTaxa());
		Assertions.assertEquals(new Double(agendamentoDemais.getValorTransferencia() * 0.08), agendamentoDemais.getTaxa());

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
