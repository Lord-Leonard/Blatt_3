package dhbw.datenbanken.ormexample.data.repository;

import dhbw.datenbanken.ormexample.connection.ConnectionHelper;

import javax.persistence.EntityManager;

public class MeasureRepository extends GenericRepository<Measure, Long> {

    protected EntityManager em = ConnectionHelper.getConnection();

    protected final Class<Measure> measure = Measure.class;

    public MeasureRepository() {
        super( Measure.class );
    }

    public Measure findOneByTitle(String title) {
        return (Measure) em.createQuery(
                "SELECT m FROM Measure m WHERE m.title LIKE :measureTitle")
                .setParameter("measureTitle", title)
                .setMaxResults(1)
                .getSingleResult();
    }

}
