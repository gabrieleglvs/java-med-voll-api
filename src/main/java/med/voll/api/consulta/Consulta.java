package med.voll.api.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.medico.Medico;
import med.voll.api.paciente.Paciente;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "consultas")
@Entity(name = "Consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    private LocalDateTime data_e_hora;

    private boolean ativo;
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivo_cancelamento;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataEHora) {
        this.paciente = paciente;
        this.medico = medico;
        this.data_e_hora = dataEHora;
        this.ativo = true;
        this.motivo_cancelamento = MotivoCancelamento.VAZIO;
    }

    public void cancelarConsulta(DadosCancelarConsulta dados) {
        this.ativo = false;
        this.motivo_cancelamento = MotivoCancelamento.fromDescricao(dados.motivo_cancelamento());
    }
}
