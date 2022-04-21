package ormexample;

import dhbw.datenbanken.ormexample.data.model.Notification;
import dhbw.datenbanken.ormexample.data.model.Problem;
import dhbw.datenbanken.ormexample.data.repository.NotificationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public class NotificationTest {

    private static final Logger LOGGER = LogManager.getLogger( ProblemTest.class );

    private static NotificationRepository notificationRepository;

    private static Validator validator;


    @BeforeAll
    static void setup() {
        notificationRepository = new NotificationRepository();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @AfterAll
    static void done() {
        notificationRepository.closeConnection();
    }


    @Test
    @DisplayName("Update notification data with em merge")
    void testUpdatingNotificationWithMerge() {
        Problem problem = new Problem();
        problem.setProblemNumber( 123L );
        problem.setStatus( 30 );
        problem.setTitle( "Erstes Problem" );

        Notification notification = new Notification();
        notification.setNotificationNumber( 345L );
        notification.setTitle( "Eine weitere Meldung" );
        notification.setStatus( 14 );
        notification.setProblem( problem );

        notificationRepository.updateWithMerge( notification );

        Notification storedNotification = notificationRepository.findById( 345L );

        Assertions.assertEquals( problem.getStatus(), storedNotification.getProblem().getStatus() );
        Assertions.assertEquals( problem.getTitle(), storedNotification.getProblem().getTitle() );
    }


    @Test
    @DisplayName("Not executed update of notification data without em merge")
    void testFailedUpdatingNotificationWithoutMerge() {
        Problem problem = new Problem();
        problem.setProblemNumber( 123L );
        problem.setStatus( 35 );
        problem.setTitle( "Erstes Problem" );

        Notification notification = new Notification();
        notification.setNotificationNumber( 345L );
        notification.setTitle( "Eine weitere Meldung" );
        notification.setStatus( 20 );
        notification.setProblem( problem );

        notificationRepository.updateWithoutMerge( notification );

        Notification storedNotification = notificationRepository.findById( 345L );

        Assertions.assertNotEquals( problem.getStatus(), storedNotification.getProblem().getStatus() );
        Assertions.assertNotEquals( notification.getStatus(), storedNotification.getStatus() );
    }


    @Test
    @DisplayName("Update notification data without em merge")
    void testUpdatingNotificationWithoutMerge() {
        Notification managedNotification = notificationRepository.findById( 345L );
        managedNotification.setStatus( 45 );

        notificationRepository.updateWithoutMerge( managedNotification );

        Notification storedNotification = notificationRepository.findById( 345L );

        Assertions.assertEquals( managedNotification.getStatus(), storedNotification.getStatus() );
    }

}
