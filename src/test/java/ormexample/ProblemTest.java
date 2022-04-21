package ormexample;

import dhbw.datenbanken.ormexample.data.model.Notification;
import dhbw.datenbanken.ormexample.data.model.Problem;
import dhbw.datenbanken.ormexample.data.repository.ProblemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class ProblemTest {

    private static final Logger LOGGER = LogManager.getLogger( ProblemTest.class );

    private static ProblemRepository problemRepository;

    private static Validator validator;


    @BeforeAll
    static void setup() {
        problemRepository = new ProblemRepository();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @AfterAll
    static void done() {
        problemRepository.closeConnection();
    }


    @Test
    @DisplayName("Finding existing problem entity with correct data")
    void testFindingProblemEntity() {
        LOGGER.info( "# --- Starting testFindingProblemEntity test method ---" );

        Problem problem = problemRepository.findById( 123L );

        Assertions.assertEquals( 1, problem.getNotifications().size() );
        Assertions.assertEquals( "Erstes Problem", problem.getTitle() );
        Assertions.assertEquals( 80, problem.getStatus().intValue() );
    }


    @Test
    @DisplayName("Save a new problem entity without notification")
    void testStoringNewProblemEntity() {
        Problem problem = new Problem();
        problem.setProblemNumber( 1000L );
        problem.setTitle( "Problem inserted by ormexample.ProblemTest" );
        problem.setStatus( 500 );

        problemRepository.createEntity( problem );

        Assertions.assertEquals( problem, problemRepository.findById( 1000L ) );
    }


    @Test
    @DisplayName("Save a new problem entity with notification")
    void testStoringNewProblemEntityWithNotification() {
        Notification notification = new Notification();
        notification.setNotificationNumber( 551L );
        notification.setTitle( "Eine weitere Meldung" );

        Long newProblemId = 1029L;
        Problem problem = new Problem();
        problem.setProblemNumber( newProblemId );
        problem.setTitle( "Problem inserted by ormexample.ProblemTest" );
        problem.setStatus( 40 );
        problem.addNotification( notification );

        problemRepository.createEntity( problem );

        // Need to close and reconnect to prevent default caching of Hibernate
        problemRepository.closeConnection();
        problemRepository.reconnectToDatabase();

        Problem storedProblem = problemRepository.findById( newProblemId );

        Assertions.assertEquals( problem, storedProblem );
        Assertions.assertEquals( 1, storedProblem.getNotifications().size() );
    }


    @Test
    @DisplayName("Try to save a new problem entity with failed status constraint")
    void wrongStatusValue() {
        Problem problem = new Problem();
        problem.setProblemNumber( 888L );
        problem.setTitle( "Problem inserted by ormexample.ProblemTest" );
        problem.setStatus( 3000 );

        Set<ConstraintViolation<Problem>> constraintViolations = validator.validate( problem );

        Assertions.assertEquals( 1, constraintViolations.size() );
        Assertions.assertEquals( "muss kleiner-gleich 100 sein.", constraintViolations.iterator().next().getMessage() );
    }


    @Test
    @DisplayName("Delete existing problem")
    void testDeletionOfProblemEntity() {
        Problem problem = new Problem();
        problem.setProblemNumber( 789L );

        problemRepository.deleteEntity( problem );

        Problem storedProblem = problemRepository.findById( 789L );

        Assertions.assertNull( storedProblem );
    }

}
