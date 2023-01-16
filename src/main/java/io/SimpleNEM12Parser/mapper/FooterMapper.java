package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.entity.Footer;
import io.SimpleNEM12Parser.entity.RecordType;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import io.SimpleNEM12Parser.validation.FooterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

@Service
public class FooterMapper {

    @Autowired
    FooterValidator footerValidator;

    public FooterMapper(FooterValidator footerValidator) {
        this.footerValidator = footerValidator;
    }

    public Footer mapRow(String[] input, String[] previousInput) {
        Errors errors = new BindException(input, "input");
        FooterValidator footerValidator = new FooterValidator();
        footerValidator.validateRow(input, previousInput, errors);
        if (errors.hasErrors()) {
            throw new SimpleNem12FileParsingException(errors.getAllErrors().toString());
        }
        Footer footer = new Footer();
        footer.setRecordType(RecordType.fromValue(input[0]));
        return footer;
    }
}