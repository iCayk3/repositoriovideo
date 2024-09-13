package br.com.w4solution.compartilhamento_video.repository;

import br.com.w4solution.compartilhamento_video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideosRepository extends JpaRepository<Video, Long> {
}
