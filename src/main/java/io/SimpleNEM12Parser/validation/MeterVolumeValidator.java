package io.SimpleNEM12Parser.validation;

import io.SimpleNEM12Parser.entity.EnergyUnit;
import io.SimpleNEM12Parser.entity.MeterRead;
import io.SimpleNEM12Parser.entity.Quality;
import io.SimpleNEM12Parser.entity.RecordType;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import io.SimpleNEM12Parser.util.ColumnsConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MeterVolumeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (MeterRead.class.equals(clazz));
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeterRead meterRead = (MeterRead) target;
//        isValidRow(footer);
    }

    public void validateRow(String[] input, String[] previousInput, Errors errors) {
        isValidPreviousRow(previousInput, errors);
        isValidRecordType(input, errors);
        validNumberOfColumns(input,errors);
        isValidDate(input[1], errors);
        isValidVolume(input[2], errors);
        isValidQuality(input[3], errors);
    }

    private void isValidRecordType(String[] input, Errors errors ) {
        if (RecordType.fromValue(input[0]) != RecordType.METER_VOLUME) {
            errors.rejectValue("recordType", "Invalid record type for footer");
        }
    }

    private void isValidPreviousRow(String[] previousInput, Errors errors ) {
        if (previousInput == null || (RecordType.fromValue(previousInput[0]) != RecordType.METER_READ && RecordType.fromValue(previousInput[0]) != RecordType.METER_VOLUME)) {
            errors.reject( "Invalid file format");
        }
    }

    private void validNumberOfColumns(String[] input, Errors errors ) {
        if (input.length != ColumnsConstant.METER_VOLUME_COLUMNS.length) {
            errors.reject( "Invalid number of columns for Meter Volume row");
        }
    }

    private void isValidQuality(String value, Errors errors) {
        if(!Arrays.stream(Quality.values()).anyMatch(e -> e.name().equalsIgnoreCase(value))) {
            errors.reject("Invalid Quality Type: " + value);
        }
    }

    private void isValidDate(String date, Errors errors) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            errors.reject("Invalid date format: " + date);
        }
    }

    private void isValidVolume(String value, Errors errors) {
        try {
            new BigDecimal(value);
        } catch (NumberFormatException e) {
            errors.reject("Invalid Volume value: " + value);
        }
    }
}
