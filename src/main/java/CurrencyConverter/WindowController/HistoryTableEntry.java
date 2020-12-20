package CurrencyConverter.WindowController;

public class HistoryTableEntry {
    private String type;
    private String value1;
    private String value2;

    public HistoryTableEntry(String type, String value1, String value2) {
        this.type = type;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getType() {
        return type;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }
}
