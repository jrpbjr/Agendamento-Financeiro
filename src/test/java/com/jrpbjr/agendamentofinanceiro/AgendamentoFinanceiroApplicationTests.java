package com.jrpbjr.agendamentofinanceiro;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.jrpbjr.agendamentofinanceiro.payload.service.OperacaoService;

import java.time.LocalDate;
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
		OperacaoModel operacaoModel = this.criarAgendamento(LocalDate.now(), Tipo.A, 100.00);
		operacaoModel.setTaxa(this.operacaoService.calcularTaxa(operacaoModel));
		this.operacaoService.validarTaxa(operacaoModel);
		Assertions.assertEquals(2 + (operacaoModel.getValorTransferencia() * 0.03),operacaoModel.getTaxa());


	}

	@Test
	public void taxaTipoBTest() throws NegocioException {
		LocalDate dataAte30Dias = this.adicionarDia( LocalDate.now() , 15);
		LocalDate dataDemais = this.adicionarDia( LocalDate.now() , 35);

		OperacaoModel agendamentoAte30Dias = this.criarAgendamento(dataAte30Dias, Tipo.B, 100.00);
		OperacaoModel agendamentoDemais = this.criarAgendamento(dataDemais, Tipo.B, 100.00);

		agendamentoAte30Dias.setTaxa( this.operacaoService.calcularTaxa(agendamentoAte30Dias) );
		agendamentoDemais.setTaxa( this.operacaoService.calcularTaxa(agendamentoDemais) );

		this.operacaoService.validarTaxa(agendamentoAte30Dias);
		this.operacaoService.validarTaxa(agendamentoDemais);

		Assertions.assertEquals(agendamentoAte30Dias.getValorTransferencia() * 0.1, agendamentoAte30Dias.getTaxa());
		Assertions.assertEquals(agendamentoDemais.getValorTransferencia() * 0.08, agendamentoDemais.getTaxa());

	}


	@Test
	public void taxaTipoCTest() throws NegocioException {
		OperacaoModel agendamentoMaior30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 35) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 30) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte25Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 22) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte20Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 19) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte15Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 13) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte10Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 8) , Tipo.C, 100.00);
		OperacaoModel agendamentoAte5Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 3) , Tipo.C, 100.00);

		List<OperacaoModel> listaAgendamentos = new ArrayList<>();
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

		Assertions.assertEquals(agendamentoMaior30Dias.getValorTransferencia() * 0.012, agendamentoMaior30Dias.getTaxa());
		Assertions.assertEquals(agendamentoAte30Dias.getValorTransferencia() * 0.021, agendamentoAte30Dias.getTaxa());
		Assertions.assertEquals(agendamentoAte25Dias.getValorTransferencia() * 0.043, agendamentoAte25Dias.getTaxa());
		Assertions.assertEquals(agendamentoAte20Dias.getValorTransferencia() * 0.054, agendamentoAte20Dias.getTaxa());
		Assertions.assertEquals(agendamentoAte15Dias.getValorTransferencia() * 0.067, agendamentoAte15Dias.getTaxa());
		Assertions.assertEquals(agendamentoAte10Dias.getValorTransferencia() * 0.074, agendamentoAte10Dias.getTaxa());
		Assertions.assertEquals(agendamentoAte5Dias.getValorTransferencia() * 0.083, agendamentoAte5Dias.getTaxa());

	}

	@Test
	public void taxaTipoDTest() throws NegocioException {
		// ATE 25000
		OperacaoModel agendamentoAte25000 = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 0) , Tipo.D, 20000.00);
		// ATE 120000
		OperacaoModel agendamentoAte10000030Dias = this.criarAgendamento(this.adicionarDia( LocalDate.now() , 15), Tipo.D, 100000.00);
		OperacaoModel agendamentoAte100000Demais = this.criarAgendamento(this.adicionarDia( LocalDate.now(), 35), Tipo.D, 100000.00);
		// MAIOR 120000
		OperacaoModel agendamentoMaior120000Maior30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 35) , Tipo.D, 150000.00);
		OperacaoModel agendamentoMaior120000Ate30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 30) , Tipo.D, 150000.00);
		OperacaoModel agendamentoMaior120000Ate25Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 22) , Tipo.D, 150000.00);
		OperacaoModel agendamentoMaior120000Ate20Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 19) , Tipo.D, 150000.00);
		OperacaoModel agendamentoMaior120000Ate15Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 13) , Tipo.D, 150000.00);
		OperacaoModel agendamentoMaior120000Ate10Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 8) , Tipo.D, 150000.00);
		OperacaoModel agendamentoMaior120000Ate5Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 3) , Tipo.D, 150000.00);

		List<OperacaoModel> listaAgendamentos = new ArrayList<OperacaoModel>();
		listaAgendamentos.add( agendamentoAte25000 );
		listaAgendamentos.add( agendamentoAte10000030Dias );
		listaAgendamentos.add( agendamentoAte100000Demais );
		listaAgendamentos.add( agendamentoMaior120000Maior30Dias );
		listaAgendamentos.add( agendamentoMaior120000Ate30Dias );
		listaAgendamentos.add( agendamentoMaior120000Ate25Dias );
		listaAgendamentos.add( agendamentoMaior120000Ate20Dias );
		listaAgendamentos.add( agendamentoMaior120000Ate15Dias );
		listaAgendamentos.add( agendamentoMaior120000Ate10Dias );
		listaAgendamentos.add( agendamentoMaior120000Ate5Dias );

		for (OperacaoModel OperacaoModel : listaAgendamentos) {
			OperacaoModel.setTaxa( this.operacaoService.calcularTaxa(OperacaoModel) );
			this.operacaoService.validarTaxa(OperacaoModel);
		}

		Assertions.assertEquals(2 + (agendamentoAte25000.getValorTransferencia() * 0.03), agendamentoAte25000.getTaxa());
		Assertions.assertEquals(agendamentoAte10000030Dias.getValorTransferencia() * 0.1, agendamentoAte10000030Dias.getTaxa());
		Assertions.assertEquals(agendamentoAte100000Demais.getValorTransferencia() * 0.08, agendamentoAte100000Demais.getTaxa());
		Assertions.assertEquals(agendamentoMaior120000Maior30Dias.getValorTransferencia() * 0.012, agendamentoMaior120000Maior30Dias.getTaxa());
		Assertions.assertEquals(agendamentoMaior120000Ate30Dias.getValorTransferencia() * 0.021, agendamentoMaior120000Ate30Dias.getTaxa());
		Assertions.assertEquals(agendamentoMaior120000Ate25Dias.getValorTransferencia() * 0.043, agendamentoMaior120000Ate25Dias.getTaxa());
		Assertions.assertEquals(agendamentoMaior120000Ate20Dias.getValorTransferencia() * 0.054, agendamentoMaior120000Ate20Dias.getTaxa());
		Assertions.assertEquals(agendamentoMaior120000Ate15Dias.getValorTransferencia() * 0.067, agendamentoMaior120000Ate15Dias.getTaxa());
		Assertions.assertEquals(agendamentoMaior120000Ate10Dias.getValorTransferencia() * 0.074, agendamentoMaior120000Ate10Dias.getTaxa());
		Assertions.assertEquals(agendamentoMaior120000Ate5Dias.getValorTransferencia() * 0.083, agendamentoMaior120000Ate5Dias.getTaxa());

	}


	private LocalDate adicionarDia(LocalDate date, int qtdDias) {
		return date.plusDays(qtdDias);
	}

	private OperacaoModel criarAgendamento(LocalDate dataAgendamento, Tipo tipo, Double valorTransferencia) {
		OperacaoModel operacaoModel = new OperacaoModel();
		operacaoModel.setContaDestino("000000");
		operacaoModel.setValorTransferencia(valorTransferencia);
		operacaoModel.setDataAgendamento(dataAgendamento);
		operacaoModel.setTipo(tipo);
		return operacaoModel;
	}

}
