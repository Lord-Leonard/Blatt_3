package ormexample;

import dhbw.datenbanken.ormexample.data.repository.MeasureRepository;
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


public class MeasureTest {

    private static final Logger LOGGER = LogManager.getLogger(MeasureTest.class);

    private static MeasureRepository measureRepository;

    private static Validator validator;


    @BeforeAll
    static void setup() {
        measureRepository = new MeasureRepository();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @AfterAll
    static void done() {
        measureRepository.closeConnection();
    }


    @Test
    @DisplayName("Find one Measure")
    void testFindOneMeasure() {
        LOGGER.info("Starting Test \"FindOneMeasure\"");

        Measure measure = measureRepository.findOneByTitle("Maßnahme 1");

        Assertions.assertNotNull(measure.getProblemNumber());
        LOGGER.info("Problmnummer der Maßnahme vorhanden.");

        Assertions.assertNotNull(measure.getTitle());
        LOGGER.info("Titel der Maßnahme vorhanden.");

        Assertions.assertNotNull(measure.getDescription());
        LOGGER.info("Beschreibung der Maßnahme vorhanden.");

        Assertions.assertNotNull(measure.getStatus());
        LOGGER.info("Status der Maßnahme vorhanden.");

        LOGGER.info("Test \"FindOneMeasure\" abgeschlossen");
    }
}
