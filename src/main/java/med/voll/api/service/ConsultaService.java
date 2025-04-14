package med.voll.api.service;

import med.voll.api.consulta.Consulta;
import med.voll.api.consulta.ConsultaRepository;
import med.voll.api.consulta.DadosCadastroConsulta;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    public void marcarConsulta(DadosCadastroConsulta dados){
        var paciente = pacienteRepository.getReferenceById(dados.paciente_id());
        var medico = medicoRepository.getReferenceById(dados.medico_id());

        var consulta = new Consulta(paciente, medico, dados.dataEHora());

        repository.save(consulta);
    }
}
