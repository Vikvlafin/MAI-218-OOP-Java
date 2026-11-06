import java.util.Currency;

public class Transaction {
    private String reference;
    private int amountMinor;
    private String currencyCode;

    public Transaction(String reference, int amountMinor, String currencyCode) {
        setReference(reference);
        setAmountMinor(amountMinor);
        setCurrencyCode(currencyCode);
    }

    public String getReference() {
        return reference;
    }

    public final void setReference(String reference) {
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("Reference must not be blank");
        }
        this.reference = reference;
    }

    public int getAmountMinor() {
        return amountMinor;
    }

    public final void setAmountMinor(int amountMinor) {
        if (amountMinor <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amountMinor = amountMinor;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public final void setCurrencyCode(String currencyCode) {
        if (currencyCode == null) {
            throw new IllegalArgumentException("Currency code is required");
        }
        Currency currency = Currency.getInstance(currencyCode);
        if (currency.getDefaultFractionDigits() < 0) {
            throw new IllegalArgumentException("Currency must have a defined minor unit");
        }
        this.currencyCode = currency.getCurrencyCode();
    }

    public void increaseAmount(int extraMinor) {
        if (extraMinor <= 0) {
            throw new IllegalArgumentException("Increase must be positive");
        }
        amountMinor = Math.addExact(amountMinor, extraMinor);
    }

    public void decreaseAmount(int reductionMinor) {
        if (reductionMinor <= 0 || reductionMinor >= amountMinor) {
            throw new IllegalArgumentException("Reduction must be positive and less than the amount");
        }
        amountMinor -= reductionMinor;
    }

    public boolean hasSameValue(Transaction other) {
        return other != null && amountMinor == other.amountMinor && currencyCode.equals(other.currencyCode);
    }
}
