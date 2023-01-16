package io.SimpleNEM12Parser.entity;

import io.SimpleNEM12Parser.exception.SimpleNem12FileParsingException;

// Enum for the different types of records in the NEM12 file
public enum RecordType {
    HEADER("100"),
    METER_READ("200"),
    METER_VOLUME("300"),
    FOOTER("900");

    private final String recordType;

    private RecordType(String recordType) {
        this.recordType = recordType;
    }

    public static RecordType fromValue(String recordType) {
        for (RecordType rt : values()) {
            if (rt.recordType.equals(recordType)) {
                return rt;
            }
        }
        throw new SimpleNem12FileParsingException("Invalid record type: " + recordType);
    }
}