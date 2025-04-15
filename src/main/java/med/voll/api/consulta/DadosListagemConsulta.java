package med.voll.api.consulta;

import java.time.LocalDateTime;

public record DadosListagemConsulta(Long pacienteId,
                                    Long medicoId,
                                    LocalDateTime dataEHora) {
    public DadosListagemConsulta(Consulta c) {
        this(c.getPaciente().getId(), c.getMedico().getId(), c.getData_e_hora());
    }
}
