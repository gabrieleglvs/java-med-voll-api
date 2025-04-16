package med.voll.api.consulta;

public enum MotivoCancelamento {
    PACIENTE_DESISTIU("Paciente desistiu"),
    MEDICO_CANCELADO("Médico cancelou"),
    OUTROS("Outros"),
    VAZIO("");

    private final String descricao;

    MotivoCancelamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
