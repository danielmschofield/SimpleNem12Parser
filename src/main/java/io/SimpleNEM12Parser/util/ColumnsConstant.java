package io.SimpleNEM12Parser.util;

public final class ColumnsConstant {

    private ColumnsConstant() {}

    public static final String[] HEADER_COLUMNS = {"RecordType"};
    public static final String[] FOOTER_COLUMNS = {"RecordType"};
    public static final String[] METER_READ_COLUMNS = {"RecordType", "NMI", "EnergyUnit"};
    public static final String[] METER_VOLUME_COLUMNS = {"RecordType", "Date", "Volume", "Quality"};
}
