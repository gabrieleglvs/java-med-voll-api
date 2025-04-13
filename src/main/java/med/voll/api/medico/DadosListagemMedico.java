package med.voll.api.medico;

public record DadosListagemMedico(Long id,
                                  String nome,
                                  String email,
                                  String crm,
                                  Especialidade especialidade) {
    public DadosListagemMedico(Medico d) {
        this(d.getId(), d.getNome(), d.getEmail(), d.getCrm(), d.getEspecialidade());
    }
}
