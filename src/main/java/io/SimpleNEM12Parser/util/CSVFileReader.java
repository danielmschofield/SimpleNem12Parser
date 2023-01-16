package io.SimpleNEM12Parser.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVFileReader {
    private final File file;

    public CSVFileReader(File file) {
        this.file = file;
    }

    public List<String[]> readFile()  {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new SimpleNem12FileParsingException("Invalid CSV File: " + file.getName());
        }
    }
}
