package io.SimpleNEM12Parser.validation;

import io.SimpleNEM12Parser.entity.Footer;
import io.SimpleNEM12Parser.entity.RecordType;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import io.SimpleNEM12Parser.util.ColumnsConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FooterValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (Footer.class.equals(clazz));
    }

    @Override
    public void validate(Object target, Errors errors) {
        Footer footer = (Footer) target;
//        isValidRow(footer);
    }

    public void validateRow(String[] input, String[] previousInput, Errors errors) {
        isValidPreviousRow(previousInput, errors);
        isValidRecordType(input, errors);
        validNumberOfColumns(input, errors);
    }

    private void isValidRecordType(String[] input, Errors errors) {
        if (RecordType.fromValue(input[0]) != RecordType.FOOTER) {
            errors.reject("Invalid record type for footer");
        }
    }

    private void isValidPreviousRow(String[] previousInput, Errors errors) {
        if (previousInput == null || RecordType.fromValue(previousInput[0]) != RecordType.METER_VOLUME) {
            errors.reject("Invalid file format");
        }
    }

    private void validNumberOfColumns(String[] input, Errors errors) {
        if (input.length != ColumnsConstant.FOOTER_COLUMNS.length) {
            errors.reject("Invalid number of columns for footer");
        }
    }
}
