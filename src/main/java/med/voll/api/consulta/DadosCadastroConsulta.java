package med.voll.api.consulta;

import java.time.LocalDateTime;

public record DadosCadastroConsulta(Long paciente_id,
                                    Long medico_id,
                                    LocalDateTime dataEHora) {
}