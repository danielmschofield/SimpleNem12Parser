package io.SimpleNEM12Parser.service;

import io.SimpleNEM12Parser.configuration.TestConfig;
import io.SimpleNEM12Parser.entity.MeterRead;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class SimpleNem12ParserImplTest {

    @Autowired
    SimpleNem12ParserImpl simpleNem12Parser;

    @Test
    void testParseSimpleNem12Success() {
        //Given
        File simpleNem12File = new File("src/test/resources/SimpleNem12.csv");

        //When
        Collection<MeterRead> meterReads = simpleNem12Parser.parseSimpleNem12(simpleNem12File);

        // Then
        assertNotNull(meterReads);
        assertFalse(meterReads.isEmpty());
        MeterRead read6123456789 = meterReads.stream().filter(r -> r.getNmi().equals("6123456789")).findAny().orElse(null);
        assertNotNull(read6123456789);
        assertEquals(new BigDecimal("-36.84") , read6123456789.getTotalVolume());

        MeterRead read6987654321 = meterReads.stream().filter(r -> r.getNmi().equals("6987654321")).findAny().orElse(null);
        assertNotNull(read6987654321);
        assertEquals(new BigDecimal("14.33") , read6987654321.getTotalVolume());

    }

    @Test
    void testParseSimpleNem12Failure_invalidCsv() {
        //Given
        File simpleNem12File = new File("src/test/resources/SimpleNem12test.csv");

        try {
            simpleNem12Parser.parseSimpleNem12(simpleNem12File);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid CSV File"));
        }

    }

    @Test
    void testParseSimpleNem12Failure_invalidFile() {
        //Given
        File simpleNem12File = new File("src/test/resources/SimpleNem12Fail.csv");

        try {
            simpleNem12Parser.parseSimpleNem12(simpleNem12File);
            fail("Expected SimpleNem12FileParsingException to be thrown");
        } catch (SimpleNem12FileParsingException e) {
            //Then
            String errorMessage = e.getMessage();
            assertTrue(errorMessage.contains("Invalid file format"));
        }

    }

}
