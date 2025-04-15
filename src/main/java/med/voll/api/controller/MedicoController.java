package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoService service;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        service.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return service.listar(paginacao);
    }

    public List<DadosListagemMedico> listarMedicos(){
        return service.listarMedicos();
    }

    @PutMapping
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        service.atualizar(dados);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
