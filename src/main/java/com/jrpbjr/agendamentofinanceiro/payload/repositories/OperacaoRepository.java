package com.jrpbjr.agendamentofinanceiro.payload.repositories;

import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacaoRepository extends JpaRepository<OperacaoModel, Integer> {


}
