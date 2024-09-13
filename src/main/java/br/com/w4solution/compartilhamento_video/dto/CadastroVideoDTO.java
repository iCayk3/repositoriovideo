package br.com.w4solution.compartilhamento_video.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroVideoDTO(@NotBlank String titulo,@NotBlank String descricao,@NotBlank String url) {
}
