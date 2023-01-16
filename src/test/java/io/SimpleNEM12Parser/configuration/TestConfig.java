package io.SimpleNEM12Parser.configuration;

import io.SimpleNEM12Parser.mapper.HeaderMapper;
import io.SimpleNEM12Parser.mapper.FooterMapper;
import io.SimpleNEM12Parser.mapper.MeterReadMapper;
import io.SimpleNEM12Parser.mapper.MeterVolumeMapper;
import io.SimpleNEM12Parser.service.SimpleNem12ParserImpl;
import io.SimpleNEM12Parser.validation.FooterValidator;
import io.SimpleNEM12Parser.validation.HeaderValidator;
import io.SimpleNEM12Parser.validation.MeterReadValidator;
import io.SimpleNEM12Parser.validation.MeterVolumeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public HeaderMapper headerMapper() {
        return new HeaderMapper(headerValidator());
    }
    @Bean
    public FooterMapper footerMapper() {
        return new FooterMapper(footerValidator());
    }
    @Bean
    public MeterReadMapper meterReadMapper() {
        return new MeterReadMapper(meterReadValidator());
    }
    @Bean
    public MeterVolumeMapper meterVolumeMapper() {
        return new MeterVolumeMapper(meterVolumeValidator());
    }
    @Bean
    public HeaderValidator headerValidator() {
        return new HeaderValidator();
    }

    @Bean
    public FooterValidator footerValidator() {
        return new FooterValidator();
    }

    @Bean
    public MeterReadValidator meterReadValidator() {
        return new MeterReadValidator();
    }

    @Bean
    public MeterVolumeValidator meterVolumeValidator() {
        return new MeterVolumeValidator();
    }


    @Bean
    public SimpleNem12ParserImpl simpleNem12Parser() {
        return new SimpleNem12ParserImpl();
    }
}
