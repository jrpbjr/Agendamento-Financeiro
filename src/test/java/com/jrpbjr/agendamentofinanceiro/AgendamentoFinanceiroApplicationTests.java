package com.jrpbjr.agendamentofinanceiro;

import com.jrpbjr.agendamentofinanceiro.payload.enums.Tipo;
import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.jrpbjr.agendamentofinanceiro.payload.service.OperacaoService;

import java.math.BigDecimal;
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
		BigDecimal TaxacaoTipoA = new BigDecimal("0.03");
		OperacaoModel operacaoModel = this.criarAgendamento(LocalDate.now(), Tipo.A, new BigDecimal("100.00"));
		operacaoModel.setTaxa(this.operacaoService.calcularTaxa(operacaoModel));
		this.operacaoService.validarTaxa(operacaoModel);

		//Assertions.assertEquals((operacaoModel.getValorTransferencia().multiply(TaxacaoTipoA).add(new BigDecimal("2")).setScale(1)),operacaoModel.getTaxa().setScale(1));
		Assertions.assertEquals(
				(operacaoModel.getValorTransferencia().multiply(TaxacaoTipoA).add(new BigDecimal("2"))).setScale(1),
				operacaoModel.getTaxa().setScale(1),
				"Os valores da taxa não são iguais"
		);

	}

	@Test
	public void taxaTipoBTest() throws NegocioException {
		LocalDate dataAte30Dias = this.adicionarDia( LocalDate.now() , 15);
		LocalDate dataDemais = this.adicionarDia( LocalDate.now() , 35);

		BigDecimal TaxacaoTipoBdataAte30Dias = new BigDecimal("0.1");
		BigDecimal TaxacaoTipoBdataDemais = new BigDecimal("0.08");

		OperacaoModel agendamentoAte30Dias = this.criarAgendamento(dataAte30Dias, Tipo.B, new BigDecimal("100.00"));
		OperacaoModel agendamentoDemais = this.criarAgendamento(dataDemais, Tipo.B, new BigDecimal("100.00"));

		agendamentoAte30Dias.setTaxa( this.operacaoService.calcularTaxa(agendamentoAte30Dias) );
		agendamentoDemais.setTaxa( this.operacaoService.calcularTaxa(agendamentoDemais) );

		this.operacaoService.validarTaxa(agendamentoAte30Dias);
		this.operacaoService.validarTaxa(agendamentoDemais);

		//Assertions.assertEquals(agendamentoAte30Dias.getValorTransferencia().multiply(TaxacaoTipoBdataAte30Dias), agendamentoAte30Dias.getTaxa());
		//Assertions.assertEquals(agendamentoDemais.getValorTransferencia().multiply(TaxacaoTipoBdataDemais), agendamentoDemais.getTaxa());

		// Definindo a escala correta
		BigDecimal taxaEsperadaAte30Dias = agendamentoAte30Dias.getValorTransferencia().multiply(TaxacaoTipoBdataAte30Dias).setScale(1, BigDecimal.ROUND_HALF_UP);
		BigDecimal taxaEsperadaDemais = agendamentoDemais.getValorTransferencia().multiply(TaxacaoTipoBdataDemais).setScale(1, BigDecimal.ROUND_HALF_UP);

		Assertions.assertEquals(taxaEsperadaAte30Dias, agendamentoAte30Dias.getTaxa());
		Assertions.assertEquals(taxaEsperadaDemais, agendamentoDemais.getTaxa());
	}


	@Test
	public void taxaTipoCTest() throws NegocioException {

		BigDecimal taxaTipoCMaior30Dias = new BigDecimal("0.012");
		BigDecimal taxaTipoCAte30Dias = new BigDecimal("0.021");
		BigDecimal taxaTipoCAte25Dias = new BigDecimal("0.043");
		BigDecimal taxaTipoCAte20Dias = new BigDecimal("0.054");
		BigDecimal taxaTipoCAte15Dias = new BigDecimal("0.067");
		BigDecimal taxaTipoCAte10Dias = new BigDecimal("0.074");
		BigDecimal taxaTipoCAte5Dias = new BigDecimal("0.083");

		OperacaoModel agendamentoMaior30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 35) , Tipo.C, new BigDecimal("100.00"));
		OperacaoModel agendamentoAte30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 30) , Tipo.C, new BigDecimal("100.00"));
		OperacaoModel agendamentoAte25Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 22) , Tipo.C, new BigDecimal("100.00"));
		OperacaoModel agendamentoAte20Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 19) , Tipo.C, new BigDecimal("100.00"));
		OperacaoModel agendamentoAte15Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 13) , Tipo.C, new BigDecimal("100.00"));
		OperacaoModel agendamentoAte10Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 8) , Tipo.C, new BigDecimal("100.00"));
		OperacaoModel agendamentoAte5Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 3) , Tipo.C, new BigDecimal("100.00"));

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

		// Definindo a escala correta
		//BigDecimal escala1 = new BigDecimal("1");
		Assertions.assertEquals(agendamentoMaior30Dias.getValorTransferencia().multiply(taxaTipoCMaior30Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior30Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte30Dias.getValorTransferencia().multiply(taxaTipoCAte30Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte30Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte25Dias.getValorTransferencia().multiply(taxaTipoCAte25Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte25Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte20Dias.getValorTransferencia().multiply(taxaTipoCAte20Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte20Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte15Dias.getValorTransferencia().multiply(taxaTipoCAte15Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte15Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte10Dias.getValorTransferencia().multiply(taxaTipoCAte10Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte10Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte5Dias.getValorTransferencia().multiply(taxaTipoCAte5Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte5Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
	}

	@Test
	public void taxaTipoDTest() throws NegocioException {

		// ATE 25000
		BigDecimal taxaTipoDAte25000 = new BigDecimal("0.03");
		OperacaoModel agendamentoAte25000 = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 0) , Tipo.D, new BigDecimal("20000.00"));
		// ATE 120000
		BigDecimal	taxaTipoDAte10000030Dias = new BigDecimal("0.1");
		OperacaoModel agendamentoAte10000030Dias = this.criarAgendamento(this.adicionarDia( LocalDate.now() , 15), Tipo.D, new BigDecimal("100000.00"));
		BigDecimal	taxaTipoDAte100000Demais = new BigDecimal("0.08");
		OperacaoModel agendamentoAte100000Demais = this.criarAgendamento(this.adicionarDia( LocalDate.now(), 35), Tipo.D, new BigDecimal("100000.00"));
		// MAIOR 120000
		BigDecimal	taxaTipoDMaior120000Maior30Dias = new BigDecimal("0.012");
		OperacaoModel agendamentoMaior120000Maior30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 35) , Tipo.D, new BigDecimal("150000.00"));

		BigDecimal	taxaTipoDMaior120000Ate30Dias = new BigDecimal("0.021");
		OperacaoModel agendamentoMaior120000Ate30Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 30) , Tipo.D, new BigDecimal("150000.00"));

		BigDecimal	taxaTipoDMaior120000Ate25Dias = new BigDecimal("0.043");
		OperacaoModel agendamentoMaior120000Ate25Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 22) , Tipo.D, new BigDecimal("150000.00"));

		BigDecimal	taxaTipoDMaior120000Ate20Dias = new BigDecimal("0.054");
		OperacaoModel agendamentoMaior120000Ate20Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 19) , Tipo.D, new BigDecimal("150000.00"));

		BigDecimal	taxaTipoDMaior120000Ate15Dias = new BigDecimal("0.067");
		OperacaoModel agendamentoMaior120000Ate15Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 13) , Tipo.D, new BigDecimal("150000.00"));

		BigDecimal	taxaTipoDMaior120000Ate10Dias = new BigDecimal("0.074");
		OperacaoModel agendamentoMaior120000Ate10Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now(), 8) , Tipo.D, new BigDecimal("150000.00"));

		BigDecimal	taxaTipoDMaior120000Ate5Dias = new BigDecimal("0.083");
		OperacaoModel agendamentoMaior120000Ate5Dias = this.criarAgendamento( this.adicionarDia( LocalDate.now() , 3) , Tipo.D, new BigDecimal("150000.00"));

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
		// Definindo a escala correta

		BigDecimal escala1 = new BigDecimal("1");
		Assertions.assertEquals(agendamentoAte25000.getValorTransferencia().multiply(taxaTipoDAte25000).add(new BigDecimal("2")).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte25000.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte10000030Dias.getValorTransferencia().multiply(taxaTipoDAte10000030Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte10000030Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoAte100000Demais.getValorTransferencia().multiply(taxaTipoDAte100000Demais).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoAte100000Demais.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoMaior120000Maior30Dias.getValorTransferencia().multiply(taxaTipoDMaior120000Maior30Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior120000Maior30Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoMaior120000Ate30Dias.getValorTransferencia().multiply(taxaTipoDMaior120000Ate30Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior120000Ate30Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoMaior120000Ate25Dias.getValorTransferencia().multiply(taxaTipoDMaior120000Ate25Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior120000Ate25Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoMaior120000Ate20Dias.getValorTransferencia().multiply(taxaTipoDMaior120000Ate20Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior120000Ate20Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoMaior120000Ate15Dias.getValorTransferencia().multiply(taxaTipoDMaior120000Ate15Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior120000Ate15Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoMaior120000Ate10Dias.getValorTransferencia().multiply(taxaTipoDMaior120000Ate10Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior120000Ate10Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
		Assertions.assertEquals(agendamentoMaior120000Ate5Dias.getValorTransferencia().multiply(taxaTipoDMaior120000Ate5Dias).setScale(1, BigDecimal.ROUND_HALF_UP), agendamentoMaior120000Ate5Dias.getTaxa().setScale(1, BigDecimal.ROUND_HALF_UP));
	}


	private LocalDate adicionarDia(LocalDate date, int qtdDias) {
		return date.plusDays(qtdDias);
	}

	private OperacaoModel criarAgendamento(LocalDate dataAgendamento, Tipo tipo, BigDecimal valorTransferencia) {
		OperacaoModel operacaoModel = new OperacaoModel();
		operacaoModel.setContaDestino("000000");
		operacaoModel.setValorTransferencia(valorTransferencia);
		operacaoModel.setDataAgendamento(dataAgendamento);
		operacaoModel.setTipo(tipo);
		return operacaoModel;
	}

}
