package br.com.w4solution.compartilhamento_video.dto;

import br.com.w4solution.compartilhamento_video.domain.Video;

public record VideoDTO(Long id, String titulo, String descricao, String url) {
    public VideoDTO(Video dados){
        this(dados.getId(), dados.getTitulo(), dados.getDescricao(), dados.getUrl());
    }
}
