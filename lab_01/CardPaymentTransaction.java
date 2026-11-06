import java.time.YearMonth;

public final class CardPaymentTransaction extends PaymentTransaction {
    private String lastFourDigits;
    private YearMonth expiryMonth;
    private boolean contactless;

    public CardPaymentTransaction(String reference, int amountMinor, String currencyCode,
                                  String merchant, String orderReference, int feeMinor,
                                  String lastFourDigits, YearMonth expiryMonth, boolean contactless) {
        super(reference, amountMinor, currencyCode, merchant, orderReference, feeMinor);
        setLastFourDigits(lastFourDigits);
        setExpiryMonth(expiryMonth);
        setContactless(contactless);
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        if (lastFourDigits == null || !lastFourDigits.matches("[0-9]{4}")) {
            throw new IllegalArgumentException("Card suffix must contain exactly four digits");
        }
        this.lastFourDigits = lastFourDigits;
    }

    public YearMonth getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(YearMonth expiryMonth) {
        if (expiryMonth == null) {
            throw new IllegalArgumentException("Expiry month is required");
        }
        this.expiryMonth = expiryMonth;
    }

    public boolean isContactless() {
        return contactless;
    }

    public void setContactless(boolean contactless) {
        this.contactless = contactless;
    }

    public String maskedCardNumber() {
        return "**** " + lastFourDigits;
    }

    public boolean isExpiredOn(YearMonth month) {
        if (month == null) {
            throw new IllegalArgumentException("Month is required");
        }
        return month.isAfter(expiryMonth);
    }

    public boolean fitsContactlessLimit(long limitMinor) {
        if (limitMinor < 0) {
            throw new IllegalArgumentException("Contactless limit must not be negative");
        }
        return contactless && totalDebit() <= limitMinor;
    }

    public static void main(String[] args) {
        CardPaymentTransaction payment = new CardPaymentTransaction(
                "T-1", 12500, "RUB", "Bookshop", "O-1", 250, "0042", YearMonth.of(2030, 6), true);
        System.out.println("Card: " + payment.maskedCardNumber());
        System.out.println("Total debit (minor units): " + payment.totalDebit() + " " + payment.getCurrencyCode());
        System.out.println("Within contactless limit: " + payment.fitsContactlessLimit(15000));
    }
}
