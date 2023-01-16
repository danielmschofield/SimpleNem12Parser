package io.SimpleNEM12Parser;

import io.SimpleNEM12Parser.entity.MeterRead;
import io.SimpleNEM12Parser.service.SimpleNem12Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.Collection;

@SpringBootApplication
public class SimpleNem12FileParserApplication {

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SimpleNem12FileParserApplication.class, args);
        SimpleNem12Parser simpleNem12Parser = context.getBean(SimpleNem12Parser.class);
        File simpleNem12File = new File("src/main/resources/SimpleNem12.csv");
        Collection<MeterRead> meterReads = simpleNem12Parser.parseSimpleNem12(simpleNem12File);
        MeterRead read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume()));  // Should be -36.84

        MeterRead read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume()));  // Should be 14.33

    }

}
