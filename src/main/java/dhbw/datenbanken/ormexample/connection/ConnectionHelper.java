package dhbw.datenbanken.ormexample.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ConnectionHelper {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionHelper.class);


    public static EntityManager getConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "ORMExamplePU" );
        EntityManager em = entityManagerFactory.createEntityManager();

        LOGGER.info( "# --- Connection established ---" );

        return em;
    }

}
