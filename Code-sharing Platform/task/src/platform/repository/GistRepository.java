package platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import platform.model.Gist;

@RestController
public interface GistRepository extends JpaRepository<Gist, Long> {
}
