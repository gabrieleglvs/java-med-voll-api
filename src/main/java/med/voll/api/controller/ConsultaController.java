package med.voll.api.controller;

import jakarta.transaction.Transactional;
import med.voll.api.consulta.DadosCadastroConsulta;
import med.voll.api.consulta.DadosListagemConsulta;
import med.voll.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private ConsultaService service;

    @PostMapping
    @Transactional
    public void marcarConsulta(@RequestBody DadosCadastroConsulta dados) throws Exception {
        service.marcarConsulta(dados);
    }

    @GetMapping
    public List<DadosListagemConsulta> listar() {
        return service.listarConsulta();
    }
}
