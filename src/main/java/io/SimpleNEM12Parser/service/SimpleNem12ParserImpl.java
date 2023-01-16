package io.SimpleNEM12Parser.service;

import io.SimpleNEM12Parser.entity.*;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;
import io.SimpleNEM12Parser.mapper.FooterMapper;
import io.SimpleNEM12Parser.mapper.HeaderMapper;
import io.SimpleNEM12Parser.mapper.MeterReadMapper;
import io.SimpleNEM12Parser.mapper.MeterVolumeMapper;
import io.SimpleNEM12Parser.util.CSVFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SimpleNem12ParserImpl implements SimpleNem12Parser {

    @Autowired
    private HeaderMapper headerMapper;

    @Autowired
    private MeterReadMapper meterReadMapper;

    @Autowired
    private MeterVolumeMapper meterVolumeMapper;

    @Autowired
    private FooterMapper footerMapper;


    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) throws SimpleNem12FileParsingException {

        CSVFileReader csvFileReader = new CSVFileReader(simpleNem12File);
        List<String[]> lines = csvFileReader.readFile();

        List<MeterRead> meterReads = new ArrayList<>();
        MeterRead currentMeterRead = null;
        String[] previousLine = null;

        for (String[] line : lines) {

            RecordType recordType = RecordType.fromValue(line[0]);

            switch (recordType) {
                case HEADER:
                    headerMapper.mapRow(line, previousLine);
                    previousLine = line;
                    break;
                case FOOTER:
                    footerMapper.mapRow(line, previousLine);
                    previousLine = line;
                    break;
                case METER_READ:
                    currentMeterRead = meterReadMapper.mapRow(line, previousLine);

                    if(currentMeterRead != null){
                        meterReads.add(currentMeterRead);
                    }

                    previousLine = line;
                    break;
                case METER_VOLUME:

                    MeterVolume meterVolume =  meterVolumeMapper.mapRow(line, previousLine);
                    currentMeterRead.appendVolume(LocalDate.parse(line[1], formatter), meterVolume);

                    previousLine = line;
                    break;
            }

        }

        return meterReads;

    }

}

