package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.configuration.TestConfig;
import io.SimpleNEM12Parser.entity.Footer;
import io.SimpleNEM12Parser.entity.RecordType;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
class FooterMapperTest {

    @Autowired
    FooterMapper footerMapper;

    @Test
    void testMapRow_validInput_validFooter() {
        //Given
        String[] input = {"900"};
        String[] previousInput = {"300", "20161221", "4.5", "A"};

        //When
        Footer footer = footerMapper.mapRow(input, previousInput);

        //Then
        assertNotNull(footer);
        assertEquals(RecordType.FOOTER, footer.getRecordType());
    }

    @Test
    void testMapRow_invalidRecordType_throwsException() {
        //Given
        String[] input = {"M", "900"};
        String[] previousInput = {"300", "20161221", "4.5", "A"};

        //When and Then
        try {
            footerMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            assertEquals("Invalid record type: M", e.getMessage());
        }
    }


    @Test
    void testMapRow_invalidPreviousRow_throwsException() {
        //Given
        String[] input = {"900"};
        String[] previousInput = {"100"};

        //When
        try {
            footerMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid file format"));
        }
    }

    @Test
    void testMapRow_invalidNumberOfColumns_throwsException() {
        //Given
        String[] input = {"900", "xyz"};
        String[] previousInput = {"300", "20161221", "4.5", "A"};

        //When
        try {
            footerMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid number of columns for footer"));
        }
    }



}

