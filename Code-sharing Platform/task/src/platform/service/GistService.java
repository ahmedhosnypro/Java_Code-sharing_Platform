package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import platform.model.Gist;
import platform.repository.GistRepository;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
@Scope("singleton")
public class GistService {

    @Autowired
    private GistRepository repository;
    AtomicLong idIndex = new AtomicLong(0);

    public List<Gist> getLatest(int limit) {
        Page<Gist> page = repository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
        return page.getContent();
    }

    public long save(Gist gist) {
        gist.setDate(Instant.now());
        gist.setId(idIndex.incrementAndGet());
        repository.save(gist);
        return gist.getId();
    }

    public Gist getGist(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteGist(long id) {
        repository.deleteById(id);
    }

    public void updateGist(long id, Gist gist) {
        gist.setId(id);
        repository.save(gist);
    }

    public long getGistsCount() {
        return repository.count();
    }

    public List<Gist> getGists() {
        return repository.findAll();
    }
}
