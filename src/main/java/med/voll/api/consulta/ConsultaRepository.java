package med.voll.api.consulta;

import med.voll.api.medico.Medico;
import med.voll.api.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPaciente(Paciente paciente);

    List<Consulta> findByMedico(Medico medico);
}
