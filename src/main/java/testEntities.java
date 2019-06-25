import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

/**
 * Illustrates basic use of Hibernate as a JPA provider.
 *
 * @author Steve Ebersole
 */
public class testEntities extends TestCase {
    private EntityManagerFactory entityManagerFactory;

    @Override
    protected void setUp() {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        entityManagerFactory = Persistence.createEntityManagerFactory( "DB_ESCALADE" );
    }

    @Override
    protected void tearDown() {
        entityManagerFactory.close();
    }

    public void testBasicUsage() {
        // create a couple of events...
        EntityManager entityManager = entityManagerFactory.createEntityManager();

    }
}