package com.jrpbjr.agendamentofinanceiro.payload.exception;

public class NegocioException extends Exception{
    private static final long serialVersionUID = -7480698887546845587L;
    private Object[]    parametros;

    public NegocioException(final String pMessage){
        super(pMessage);
    }

    public NegocioException(final String pMessage, final Throwable pThrowable){
        super(pMessage, pThrowable);
    }

    public NegocioException(final String pMessage, final Throwable pThrowable, final Object[] params){
        super(pMessage,pThrowable);

        this.parametros = params;
    }

    public void setParametros(final Object[] parametros){
        this.parametros = parametros;
    }

}
