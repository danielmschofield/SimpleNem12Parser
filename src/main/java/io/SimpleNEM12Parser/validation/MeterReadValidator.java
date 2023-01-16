package io.SimpleNEM12Parser.validation;

import io.SimpleNEM12Parser.entity.EnergyUnit;
import io.SimpleNEM12Parser.entity.MeterRead;
import io.SimpleNEM12Parser.entity.RecordType;
import io.SimpleNEM12Parser.util.ColumnsConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.EnumSet;

@Component
@RequiredArgsConstructor
public class MeterReadValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (MeterRead.class.equals(clazz));
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeterRead meterRead = (MeterRead) target;
    }

    public void validateRow(String[] input, String[] previousInput, Errors errors) {
        isValidPreviousRow(previousInput, errors);
        isValidRecordType(input, errors);
        validNumberOfColumns(input, errors);
        isValidNMI(input[1], errors);
        isValidEnergyUnit(input[2], errors);
    }

    private void isValidRecordType(String[] input, Errors errors ) {
        if (RecordType.fromValue(input[0]) != RecordType.METER_READ) {
            errors.reject("Invalid record type for footer");
        }
    }

    private void isValidPreviousRow(String[] previousInput, Errors errors ) {
        if (previousInput == null || (RecordType.fromValue(previousInput[0]) != RecordType.HEADER && RecordType.fromValue(previousInput[0]) != RecordType.METER_VOLUME)) {
            errors.reject( "Invalid file format");
        }
    }

    private void validNumberOfColumns(String[] input, Errors errors ) {
        if (input.length != ColumnsConstant.METER_READ_COLUMNS.length) {
            errors.reject( "Invalid number of columns for Meter Read row");
        }
    }

    private void isValidEnergyUnit(String value, Errors errors) {
        if(!Arrays.stream(EnergyUnit.values()).anyMatch(e -> e.name().equalsIgnoreCase(value))) {
            errors.reject("Invalid Energy Unit: " + value);
        }
    }


    private void isValidNMI(String value, Errors errors) {
        if (!(value.length() == 10)) {
            errors.reject("Invalid NMI :" + value);
        }
    }

}
