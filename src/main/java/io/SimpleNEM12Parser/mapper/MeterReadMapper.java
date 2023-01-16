package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.entity.EnergyUnit;
import io.SimpleNEM12Parser.entity.MeterRead;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import io.SimpleNEM12Parser.validation.MeterReadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

@Service
public class MeterReadMapper {

    @Autowired
    MeterReadValidator meterReadValidator;

    public MeterReadMapper(MeterReadValidator meterReadValidator) {
        this.meterReadValidator = meterReadValidator;
    }

    public MeterRead mapRow(String[] input, String[] previousInput) {
        Errors errors = new BindException(input, "input");
        meterReadValidator.validateRow(input, previousInput, errors);
        if (errors.hasErrors()) {
            throw new SimpleNem12FileParsingException(errors.getAllErrors().toString());
        }
        return new MeterRead(input[1], EnergyUnit.valueOf(input[2]));
    }
}