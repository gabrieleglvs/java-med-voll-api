package med.voll.api.medico;

public record DadosListagemMedico(String nome,
                                  String email,
                                  String crm,
                                  Especialidade especialidade) {
    public DadosListagemMedico(Medico d) {
        this(d.getNome(), d.getEmail(), d.getCrm(), d.getEspecialidade());
    }
}
