package platform.service;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import platform.model.Gist;
import platform.model.GistDTO;
import platform.repository.CustomGistRepositoryImpl;
import platform.repository.GistRepository;

import java.util.List;
import java.util.Objects;


@Service
@Scope("singleton")
public class GistService {

    @Autowired
    private GistRepository repository;

    @Autowired
    private CustomGistRepositoryImpl customGistRepository;

    public List<GistDTO> getLatest(int limit) {
        var gists = customGistRepository.getLatest(limit);

        gists.forEach(this::updateGistViewLimit);
        return gists.stream().map(GistDTO::convertToDto).toList();
    }

    public String save(GistDTO gistDTO) {
        return repository.save(GistDTO.convertToEntity(gistDTO)).getUuid().toString();
    }

    @Synchronized
    public Gist getGist(String uuid) {
        var gist = customGistRepository.findByUuid(uuid);
        if (Objects.nonNull(gist)) {
            if (deleteGistIfExpired(gist)) {
                return null;
            }
            updateGistViewLimit(gist);
            return gist;
        }
        return null;
    }

    private void updateGistViewLimit(Gist gist) {
        var viewsLimit = gist.getViews();
        if (viewsLimit != null) {
            viewsLimit--;
            gist.setViews(gist.getViews() - 1, true);
            if (viewsLimit == 0) {
                repository.deleteById(gist.getId());
            } else {
                repository.save(gist);
            }
        }
    }

    private boolean deleteGistIfExpired(Gist gist) {
        if (gist.isExpired()) {
            repository.deleteById(gist.getId());
            return true;
        }
        return false;
    }
}
