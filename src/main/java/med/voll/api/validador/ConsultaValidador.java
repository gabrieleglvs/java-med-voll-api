package med.voll.api.validador;

import med.voll.api.consulta.Consulta;
import med.voll.api.consulta.DadosCadastroConsulta;
import med.voll.api.consulta.DadosListagemConsulta;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.paciente.Paciente;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
public class ConsultaValidador {

    @Autowired
    private MedicoService medicoService;

    public boolean validarAtivos(Paciente paciente,
                                 Medico medico) {
        // 6. Verifica se paciente está ativo.
        if(!paciente.getAtivo()) {
            throw new IllegalArgumentException("Não é possível agendamento para paciente inativo!");
        }

        // 7. Verifica se o medico está ativo.
        if(!medico.getAtivo()) {
            throw new IllegalArgumentException("Não é possível agendamento para medico inativo!");
        }
        return true;
    }

    public boolean validarAgendaPacienteMedico(DadosCadastroConsulta dados,
                                               List<Consulta> consultasPaciente,
                                               List<Consulta> consultasMedico){
        // 8. Verifica se já há consulta marcada para o mesmo dia e mesmo paciente.
        Optional<Consulta> consultaPaciente = consultasPaciente.stream()
                .filter(c -> Objects.equals(c.getData_e_hora().toLocalDate(), dados.dataEHora().toLocalDate()))
                .findFirst();

        if(consultaPaciente.isPresent()) {
            throw new IllegalArgumentException("Não é possível agendar mais de uma consulta para o mesmo dia!");
        }

        //9. Verifica se já há consultas para o medico neste mesmo dia e horário.
        Optional<Consulta> consultaMedico = consultasMedico.stream()
                .filter(c -> Objects.equals(c.getData_e_hora(), dados.dataEHora()))
                .findFirst();

        if(consultaMedico.isPresent()) {
            throw new IllegalArgumentException("Este médico já possui consulta para esse dia e horário!");
        }
        return true;
    }

    //-----VALIDAÇÃO DE DATAS E HORÁRIOS-----

    public boolean validarDataEHorario(DadosCadastroConsulta dados) {

        // 2. Verifica horário comercial (07:00 às 19:00).
        verificarHorarioComercial(dados);

        // 3. Verifica se é dia útil.
        verificarDiaUtil(dados);

        // 4. Verifica duração da consulta.
        verificarDuracaoConsulta(dados);

        // 5. Verifica antecedência de 30min para consulta.
        verificarAntecedencia(dados);
        return true;
    }

    public void verificarHorarioComercial(DadosCadastroConsulta dados) {
        LocalTime hora = dados.dataEHora().toLocalTime();

        if (hora.isBefore(LocalTime.of(7, 0)) || hora.isAfter(LocalTime.of(19, 0))) {
            throw new IllegalArgumentException("A consulta deve estar entre 07:00 e 19:00.");
        }
    }

    public void verificarDiaUtil(DadosCadastroConsulta dados) {
        DayOfWeek dia = dados.dataEHora().getDayOfWeek();
        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Consultas só podem ser marcadas em dias úteis.");
        }
    }

    public void verificarDuracaoConsulta(DadosCadastroConsulta dados) {
        LocalTime hora = dados.dataEHora().toLocalTime();
        if(hora.getMinute() != 0){
            throw new IllegalArgumentException("Consultas possuem tempo fixo de 1 hora.");
        }
    }

    public void verificarAntecedencia(DadosCadastroConsulta dados){
        if (dados.dataEHora().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("A consulta deve ser agendada com no mínimo 30 minutos de antecedência" +
                    ".");
        }
    }


    //-----VALIDAÇÃO DO MÉDICO------
    public DadosCadastroConsulta atualizarDadosCadastroConsulta(DadosListagemMedico medicoEscolhido,
                                                                DadosCadastroConsulta dados){
        var paciente = dados.paciente_id();
        var medico = medicoEscolhido.id();
        var dataEhora = dados.dataEHora();

        return new DadosCadastroConsulta(paciente, medico, dataEhora);
    }

    public DadosCadastroConsulta validarMedicoParaAtendimento(DadosCadastroConsulta dados,
                                                              List<DadosListagemConsulta> consultas) throws Exception {
            List<Long> medicosOculpados = consultas.stream()
                    .filter(c -> Objects.equals(c.dataEHora(), dados.dataEHora()))
                    .map(c -> c.medicoId())
                    .toList();

            List<DadosListagemMedico> medicos = medicoService.listarMedicos();
            List<DadosListagemMedico> medicosDisponiveis = medicos.stream()
                    .filter(medico -> !medicosOculpados.contains(medico.id()))
                    .toList();

            //medicosDisponiveis.forEach(System.out::println);

            if (!medicosDisponiveis.isEmpty()) {
                Random random = new Random();
                DadosListagemMedico medicoEscolhido = medicosDisponiveis.get(
                        random.nextInt(medicosDisponiveis.size())
                );
                return atualizarDadosCadastroConsulta(medicoEscolhido, dados);
            } else {
                throw new Exception("Não há medicos disponíveis.");
            }
    }
}
