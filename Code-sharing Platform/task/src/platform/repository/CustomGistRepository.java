package platform.repository;

import platform.model.Gist;

import java.util.List;

public interface CustomGistRepository {
    public List<Gist> getLatest(int limit);

    Gist findByUuid(String uuid);
}
