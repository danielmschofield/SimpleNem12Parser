package io.SimpleNEM12Parser.validation;

import io.SimpleNEM12Parser.entity.Header;
import io.SimpleNEM12Parser.entity.RecordType;
import io.SimpleNEM12Parser.util.ColumnsConstant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class HeaderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (Header.class.equals(clazz));
    }

    @Override
    public void validate(Object target, Errors errors) {
        Header header = (Header) target;
    }

    public void validateRow(String[] input, String[] previousInput, Errors errors) {
        isValidPreviousRow(previousInput, errors);
        isValidRecordType(input, errors);
        validNumberOfColumns(input, errors);
    }

    private void isValidRecordType(String[] input, Errors errors ) {
        if (RecordType.fromValue(input[0]) != RecordType.HEADER) {
            errors.reject("Invalid record type for Header row");
        }
    }

    private void isValidPreviousRow(String[] previousInput, Errors errors ) {
        if (previousInput != null) {
            errors.reject("Previous row must be null");
        }
    }

    private void validNumberOfColumns(String[] input, Errors errors ) {
        if (input.length != ColumnsConstant.HEADER_COLUMNS.length) {
            errors.reject( "Invalid number of columns for header");
        }
    }
}
