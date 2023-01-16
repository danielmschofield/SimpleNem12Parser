package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.configuration.TestConfig;
import io.SimpleNEM12Parser.entity.EnergyUnit;
import io.SimpleNEM12Parser.entity.MeterRead;
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
class MeterReadMapperTest {

    @Autowired
    MeterReadMapper meterReadMapper;

    @Test
    void testMapRow_validInput_validMeterRead() {
        //Given
        String[] input = {"200","6123456789", "KWH"};
        String[] previousInput = {"100"};

        //When
        MeterRead meterRead = meterReadMapper.mapRow(input, previousInput);

        //Then
        assertNotNull(meterRead);
        assertEquals("6123456789", meterRead.getNmi());
        assertEquals(EnergyUnit.KWH, meterRead.getEnergyUnit());
    }


    @Test
    void testMapRow_invalidRecordType_throwsException() {
        //Given
        String[] input = {"M", "100"};
        String[] previousInput = null;

        //When and Then
        try {
            meterReadMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            assertEquals("Invalid record type: M", e.getMessage());
        }
    }


    @Test
    void testMapRow_invalidPreviousRow_throwsException() {
        //Given
        String[] input = {"200","6123456789", "KWH"};
        String[] previousInput = {"900"};

        //When
        try {
            meterReadMapper.mapRow(input, previousInput);
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
        String[] input = {"200","6123456789", "KWH", "", ""};
        String[] previousInput = {"100"};

        //When
        try {
            meterReadMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid number of columns for Meter Read row"));
        }
    }

    @Test
    void testMapRow_invalidEnergyUnit_throwsException() {
        //Given
        String[] input = {"200","6123456789", "KW"};
        String[] previousInput = {"100"};

        //When
        try {
            meterReadMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid Energy Unit: " + input[2]));
        }
    }

    @Test
    void testMapRow_inValidNMI_throwsException() {
        //Given
        String[] input = {"200","612345689", "KWH"};
        String[] previousInput = {"100"};

        //When
        try {
            meterReadMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid NMI :" + input[1]));
        }
    }

}

