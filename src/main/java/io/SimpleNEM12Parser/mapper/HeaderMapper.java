package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.entity.RecordType;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import io.SimpleNEM12Parser.validation.HeaderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import io.SimpleNEM12Parser.entity.Header;

@Service
public class HeaderMapper {

    @Autowired
    HeaderValidator headerValidator;

    public HeaderMapper(HeaderValidator headerValidator) {
        this.headerValidator = headerValidator;
    }

    public Header mapRow(String[] input, String[] previousInput) {
        Errors errors = new BindException(input, "input");
        HeaderValidator headerValidator = new HeaderValidator();
        headerValidator.validateRow(input, previousInput, errors);
        if (errors.hasErrors()) {
            throw new SimpleNem12FileParsingException(errors.getAllErrors().toString());
        }
        Header header = new Header();
        header.setRecordType(RecordType.fromValue(input[0]));
        return header;
    }
}