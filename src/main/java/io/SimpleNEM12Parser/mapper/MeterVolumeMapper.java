package io.SimpleNEM12Parser.mapper;

import io.SimpleNEM12Parser.entity.MeterVolume;
import io.SimpleNEM12Parser.entity.Quality;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;

import io.SimpleNEM12Parser.validation.MeterVolumeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

@Service
public class MeterVolumeMapper {

    @Autowired
    MeterVolumeValidator meterVolumeValidator;

    public MeterVolumeMapper(MeterVolumeValidator meterVolumeValidator) {
        this.meterVolumeValidator = meterVolumeValidator;
    }

    public MeterVolume mapRow(String[] input, String[] previousInput) {
        Errors errors = new BindException(input, "input");
        meterVolumeValidator.validateRow(input, previousInput, errors);
        if (errors.hasErrors()) {
            throw new SimpleNem12FileParsingException(errors.getAllErrors().toString());
        }
        return new MeterVolume(new BigDecimal(input[2]), Quality.valueOf(input[3]));
    }
}