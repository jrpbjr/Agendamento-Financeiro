package com.jrpbjr.agendamentofinanceiro;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.jrpbjr.agendamentofinanceiro.payload.service.OperacaoService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


	@Test
	public void taxaTipoCTest() throws NegocioException {
		OperacaoModel agendamentoMaior30Dias = this.criarAgendamento( this.adicionarDia( new Date() , 35) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte30Dias = this.criarAgendamento( this.adicionarDia( new Date() , 30) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte25Dias = this.criarAgendamento( this.adicionarDia( new Date() , 22) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte20Dias = this.criarAgendamento( this.adicionarDia( new Date() , 19) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte15Dias = this.criarAgendamento( this.adicionarDia( new Date() , 13) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte10Dias = this.criarAgendamento( this.adicionarDia( new Date() , 8) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte5Dias = this.criarAgendamento( this.adicionarDia( new Date() , 3) , Tipo.C, 100.00);

		List<OperacaoModel> listaAgendamentos = new ArrayList<OperacaoModel>();
		listaAgendamentos.add( agendamentoMaior30Dias );
		listaAgendamentos.add( agendamentoAte30Dias );
		listaAgendamentos.add( agendamentoAte25Dias );
		listaAgendamentos.add( agendamentoAte20Dias );
		listaAgendamentos.add( agendamentoAte15Dias );
		listaAgendamentos.add( agendamentoAte10Dias );
		listaAgendamentos.add( agendamentoAte5Dias );

		for (OperacaoModel OperacaoModel : listaAgendamentos) {
			OperacaoModel.setTaxa( this.operacaoService.calcularTaxa(OperacaoModel) );
			this.operacaoService.validarTaxa(OperacaoModel);
		}

		Assertions.assertEquals(new Double(agendamentoMaior30Dias.getValorTransferencia() * 0.012), agendamentoMaior30Dias.getTaxa());
		Assertions.assertEquals(new Double(agendamentoAte30Dias.getValorTransferencia() * 0.021), agendamentoAte30Dias.getTaxa());
		Assertions.assertEquals(new Double(agendamentoAte25Dias.getValorTransferencia() * 0.043), agendamentoAte25Dias.getTaxa());
		Assertions.assertEquals(new Double(agendamentoAte20Dias.getValorTransferencia() * 0.054), agendamentoAte20Dias.getTaxa());
		Assertions.assertEquals(new Double(agendamentoAte15Dias.getValorTransferencia() * 0.067), agendamentoAte15Dias.getTaxa());
		Assertions.assertEquals(new Double(agendamentoAte10Dias.getValorTransferencia() * 0.074), agendamentoAte10Dias.getTaxa());
		Assertions.assertEquals(new Double(agendamentoAte5Dias.getValorTransferencia() * 0.083), agendamentoAte5Dias.getTaxa());

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
