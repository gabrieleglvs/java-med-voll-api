package med.voll.api.controller;

import jakarta.transaction.Transactional;
import med.voll.api.consulta.Consulta;
import med.voll.api.consulta.ConsultaRepository;
import med.voll.api.consulta.DadosCadastroConsulta;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void marcarConsulta(@RequestBody DadosCadastroConsulta dados){
        var paciente = pacienteRepository.getReferenceById(dados.paciente_id());
        var medico = medicoRepository.getReferenceById(dados.medico_id());

        var consulta = new Consulta(paciente, medico, dados.dataEHora());

        repository.save(consulta);
    }
}
