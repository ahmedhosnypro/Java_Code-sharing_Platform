package platform.service;

import lombok.Synchronized;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import platform.model.Gist;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Scope("singleton")
public class GistService {
    private final Deque<Gist> gists = new ConcurrentLinkedDeque<>();

    AtomicInteger idIndex = new AtomicInteger(0);

    public List<Gist> getLatest(int limit) {
        limit = Math.min(limit, gists.size());

        Iterator<Gist> gistsIterator = gists.iterator();
        List<Gist> latestGists = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            latestGists.add(gistsIterator.next());
        }
        return latestGists;
    }

    public int save(Gist gist) {
        gist.setDate(Instant.now());
        gist.setId(idIndex.incrementAndGet());
        gists.addFirst(gist);
        return gist.getId();
    }

    public Gist getGist(int id) {
        return gists.stream().filter(gist -> gist.getId() == id).findFirst().orElse(null);
    }

    public void deleteGist(int id) {
        gists.removeIf(gist -> gist.getId() == id);
    }

    public void updateGist(int id, Gist gist) {
        Gist gistToUpdate = getGist(id);
        gistToUpdate.setCode(gist.getCode());
        gistToUpdate.setDate(Instant.now());
    }

    public int getGistsCount() {
        return gists.size();
    }

    public List<Gist> getGists() {
        return new ArrayList<>(gists);
    }
}
