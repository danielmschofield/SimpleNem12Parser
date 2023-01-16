package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.configuration.TestConfig;
import io.SimpleNEM12Parser.entity.Header;
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
class HeaderMapperTest {

    @Autowired
    HeaderMapper headerMapper;

    @Test
    void testMapRow_validInput_validHeader() {
        //Given
        String[] input = {"100"};
        String[] previousInput = null;

        //When
        Header header = headerMapper.mapRow(input, previousInput);

        //Then
        assertNotNull(header);
        assertEquals(RecordType.HEADER, header.getRecordType());
    }

    @Test
    void testMapRow_invalidRecordType_throwsException() {
        //Given
        String[] input = {"M", "100"};
        String[] previousInput = null;

        //When and Then
        try {
            headerMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            assertEquals("Invalid record type: M", e.getMessage());
        }
    }


    @Test
    void testMapRow_invalidPreviousRow_throwsException() {
        //Given
        String[] input = {"100"};
        String[] previousInput = {"900"};

        //When
        try {
            headerMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Previous row must be null"));
        }
    }

    @Test
    void testMapRow_invalidNumberOfColumns_throwsException() {
        //Given
        String[] input = {"900", "xyz"};
        String[] previousInput = null;

        //When
        try {
            headerMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid number of columns for header"));
        }
    }



}

