package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.configuration.TestConfig;
import io.SimpleNEM12Parser.entity.MeterVolume;
import io.SimpleNEM12Parser.entity.Quality;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
class MeterVolumeMapperTest {

    @Autowired
    MeterVolumeMapper meterVolumeMapper;

    @Test
    void testMapRow_validInput_validMeterVolume() {
        //Given
        String[] input = {"300","20161113", "-50.8", "A"};
        String[] previousInput = {"200","6123456789", "KWH"};

        //When
        MeterVolume meterVolume = meterVolumeMapper.mapRow(input, previousInput);

        //Then
        assertNotNull(meterVolume);
        assertEquals(new BigDecimal("-50.8"), meterVolume.getVolume());
        assertEquals(Quality.A, meterVolume.getQuality());

    }


    @Test
    void testMapRow_invalidRecordType_throwsException() {
        //Given
        String[] input = {"M", "20161113", "-50.8", "A"};
        String[] previousInput = {"200","6123456789", "KWH"};

        //When and Then
        try {
            meterVolumeMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            assertEquals("Invalid record type: M", e.getMessage());
        }
    }


    @Test
    void testMapRow_invalidPreviousRow_throwsException() {
        //Given
        String[] input = {"300","20161113", "-50.8", "A"};
        String[] previousInput = {"900"};

        //When
        try {
            meterVolumeMapper.mapRow(input, previousInput);
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
        String[] input = {"300","20161113", "-50.8", "A", "", ""};
        String[] previousInput = {"200","6123456789", "KWH"};

        //When
        try {
            meterVolumeMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid number of columns for Meter Volume row"));
        }
    }

    @Test
    void testMapRow_invalidQuality_throwsException() {
        //Given
        String[] input = {"300","20161113", "-50.8", "B"};
        String[] previousInput = {"200","6123456789", "KWH"};

        //When
        try {
            meterVolumeMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid Quality Type: " + input[3]));
        }
    }


    @Test
    void testMapRow_invalidDate_throwsException() {
        //Given
        String[] input = {"300","2016113", "-50.8", "A"};
        String[] previousInput = {"200","6123456789", "KWH"};

        //When
        try {
            meterVolumeMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid date format: " + input[1]));
        }
    }


    @Test
    void testMapRow_invalidVolume_throwsException() {
        //Given
        String[] input = {"300","20161113", "a1", "A"};
        String[] previousInput = {"200","6123456789", "KWH"};

        //When
        try {
            meterVolumeMapper.mapRow(input, previousInput);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid Volume value: " + input[2]));
        }
    }

}

