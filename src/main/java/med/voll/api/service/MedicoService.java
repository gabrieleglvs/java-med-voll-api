package med.voll.api.service;

import jakarta.transaction.Transactional;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository repository;

    @Transactional
    public void cadastrar(DadosCadastroMedico dados){
        repository.save(new Medico(dados));
    }

    public Page<DadosListagemMedico> listar(Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
    }

    public List<DadosListagemMedico> listarMedicos(){
        return repository.findAllByAtivoTrue()
                .stream()
                .map(DadosListagemMedico::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void atualizar(DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @Transactional
    public void excluir(Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
