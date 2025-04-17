package med.voll.api.service;

import jakarta.transaction.Transactional;
import med.voll.api.consulta.*;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;
import med.voll.api.validador.ConsultaValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaValidador validador;

    public List<DadosListagemConsulta> listarConsulta() {
        return repository.findAll()
                .stream().map(consulta -> new DadosListagemConsulta(consulta))
                .collect(Collectors.toList());
    }

    public DadosCadastroConsulta validarExistenciaMedicoConsulta(DadosCadastroConsulta dados) throws Exception {
        List<DadosListagemConsulta> consultas = listarConsulta();

        if(dados.medico_id() == null) {
            var dadosConferidos = validador.validarMedicoParaAtendimento(dados, consultas);
            return dadosConferidos;
        }
        return dados;
    }

    @Transactional
    public void marcarConsulta(DadosCadastroConsulta dados) throws Exception {
        var dadosValidados = validarExistenciaMedicoConsulta(dados);

        //System.out.println(dadosValidados);

        var paciente = pacienteRepository.getReferenceById(dadosValidados.paciente_id());
        var medico = medicoRepository.getReferenceById(dadosValidados.medico_id());
        var consultasPaciente = repository.findByPaciente(paciente);
        var consultasMedico = repository.findByMedico(medico);

        if(validador.validarAtivos(paciente, medico)) {
            if(validador.validarAgendaPacienteMedico(dadosValidados, consultasPaciente, consultasMedico)) {
                if(validador.validarDataEHorario(dadosValidados)) {
                    var consulta = new Consulta(paciente, medico, dadosValidados.dataEHora());
                    repository.save(consulta);
                }
            }
        }
    }

    @Transactional
    public void cancelarConsulta(DadosCancelarConsulta dados) {
        var consulta = repository.getReferenceById(dados.id());
        consulta.cancelarConsulta(dados);
    }
}
