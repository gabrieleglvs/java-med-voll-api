package med.voll.api.consulta;

import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.paciente.DadosCadastroPaciente;

import java.time.LocalDateTime;

public record DadosCadastroConsulta(Long paciente_id,
                                    Long medico_id,
                                    LocalDateTime dataEHora) {
}