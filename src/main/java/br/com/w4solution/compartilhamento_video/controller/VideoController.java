package br.com.w4solution.compartilhamento_video.controller;

import br.com.w4solution.compartilhamento_video.domain.Video;
import br.com.w4solution.compartilhamento_video.dto.AtualizarVideosDTO;
import br.com.w4solution.compartilhamento_video.dto.CadastroVideoDTO;
import br.com.w4solution.compartilhamento_video.dto.MensagemDTO;
import br.com.w4solution.compartilhamento_video.dto.VideoDTO;
import br.com.w4solution.compartilhamento_video.repository.VideosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("videos")
public class VideoController {

    @Autowired
    VideosRepository videosRepository;

    @GetMapping
    public ResponseEntity<Page<VideoDTO>> listarVideos(@PageableDefault(size = 10) Pageable paginacao){
        var pageVideos = videosRepository.findAll(paginacao).map(VideoDTO::new);
        return ResponseEntity.ok(pageVideos);
    }
    @GetMapping("/{id}")
    public ResponseEntity pegarVideo(@PathVariable Long id){
        var video = videosRepository.findById(id);
        if(video.isPresent()){
            return ResponseEntity.ok(new VideoDTO(video.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado.");
    }
    @PostMapping
    @Transactional
    public ResponseEntity criarVideo(@RequestBody @Valid CadastroVideoDTO dados, UriComponentsBuilder uri){
        var video = new Video(null, dados.titulo(), dados.descricao(), dados.url());
        videosRepository.save(video);
        var videoCriado = uri.path("/video/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(videoCriado).body(new VideoDTO(video));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarVideo(@PathVariable Long id, @RequestBody @Valid AtualizarVideosDTO dados){
        var video = videosRepository.findById(id).get();
        video.atualizarVideo(dados);
        return ResponseEntity.ok().body(new VideoDTO(video));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarVideo(@PathVariable Long id) {
        var video = videosRepository.findById(id);
        videosRepository.delete(video.get());
        return ResponseEntity.ok().build();
    }
}
