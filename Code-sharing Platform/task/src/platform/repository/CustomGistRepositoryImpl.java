package platform.repository;

import org.springframework.stereotype.Repository;
import platform.model.Gist;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;


@Repository
public class CustomGistRepositoryImpl implements CustomGistRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Gist> getLatest(int limit) {
        return entityManager.createQuery("""
                                SELECT new gist(g.id,g.uuid, g.code,g.creationDate, g.gistRestriction)
                                FROM gist g
                                WHERE g.gistRestriction.deleteDate IS NULL AND g.gistRestriction.views IS NULL
                                ORDER BY g.id DESC
                                """
                        , Gist.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Gist findByUuid(String uuid) {
        try {
            return entityManager.createQuery("SELECT g FROM gist g WHERE g.uuid = :uuid", Gist.class)
                    .setParameter("uuid", UUID.fromString(uuid))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
