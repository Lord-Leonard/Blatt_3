package ormexample;

import dhbw.datenbanken.ormexample.connection.ConnectionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;


@DisplayName("Database connection tests")
public class ConnectionTest {

    @Test
    @DisplayName("Test successfully establishing connection to DB")
    void testConnectionToDB() {
        EntityManager em = ConnectionHelper.getConnection();
        Assertions.assertNotNull( em );
        em.close();
    }
}
