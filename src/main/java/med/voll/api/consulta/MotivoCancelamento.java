package med.voll.api.consulta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MotivoCancelamento {
    PACIENTE_DESISTIU("Paciente desistiu"),
    MEDICO_CANCELOU("Médico cancelou"),
    OUTROS("Outros"),
    VAZIO("");

    private final String descricao;

    MotivoCancelamento(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static MotivoCancelamento fromDescricao(String descricao) {
        for (MotivoCancelamento motivo : values()) {
            if (motivo.getDescricao().equalsIgnoreCase(descricao)) {
                return motivo;
            }
        }
        throw new IllegalArgumentException("Motivo de cancelamento inválido: " + descricao);
    }
}
