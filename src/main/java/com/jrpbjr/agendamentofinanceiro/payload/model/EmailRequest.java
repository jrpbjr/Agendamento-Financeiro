package com.jrpbjr.agendamentofinanceiro.payload.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Setter
@Getter
public class EmailRequest {
    @Email
    @NotNull
    private String email;

    @NotEmpty
    private String suject;

    @NotEmpty
    private String body;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private ZoneId timeZone;


}
