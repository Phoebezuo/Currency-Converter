package CurrencyConverter.WindowController;

public class PopularTableEntry {
    private final String type;
    private String rate0;
    private String rate1;
    private String rate2;
    private String rate3;

    public PopularTableEntry(String type) {
        this.type = type;
    }

    public void setRate(int i, double currentRate, double prevRate) {
        if (i > 3 || i < 0) {
            return;
        }
        String arrow = "-";
        String upArrow = "\u2191";
        String downArrow = "\u2193";
        arrow = currentRate > prevRate ? upArrow : currentRate == prevRate ? "-" : downArrow;

        String rateStr = currentRate != 0 ? Double.toString(Math.round(currentRate * 100) / 100.0) : "";
        rateStr += " " + arrow;
        switch (i) {
            case 0:
                rate0 = rateStr;
                break;
            case 1:
                rate1 = rateStr;
                break;
            case 2:
                rate2 = rateStr;
                break;
            default:
                rate3 = rateStr;
                break;
        }
    }

    public String getType() {
        return type;
    }

    public String getRate0() {
        return rate0;
    }

    public String getRate1() {
        return rate1;
    }

    public String getRate2() {
        return rate2;
    }

    public String getRate3() {
        return rate3;
    }

}
