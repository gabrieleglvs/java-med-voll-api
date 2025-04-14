package med.voll.api.validador;

import med.voll.api.consulta.DadosCadastroConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class ConsultaValidador {
    public boolean validarHorarioComercial(DadosCadastroConsulta dados) {
        // 1. Verifica se está no futuro
        if (dados.dataEHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data deve estar no futuro.");
        }

        // 2. Verifica horário comercial (08:00 às 18:00)
        LocalTime hora = dados.dataEHora().toLocalTime();
        if (hora.isBefore(LocalTime.of(7, 0)) || hora.isAfter(LocalTime.of(19, 0))) {
            throw new IllegalArgumentException("A consulta deve estar entre 07:00 e 19:00.");
        }

        // 3. Verifica se é dia útil
        DayOfWeek dia = dados.dataEHora().getDayOfWeek();
        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Consultas só podem ser marcadas em dias úteis.");
        }

        return true;
    }
}
