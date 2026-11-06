public class PaymentTransaction extends Transaction {
    private String merchant;
    private String orderReference;
    private int feeMinor;

    public PaymentTransaction(String reference, int amountMinor, String currencyCode,
                              String merchant, String orderReference, int feeMinor) {
        super(reference, amountMinor, currencyCode);
        setMerchant(merchant);
        setOrderReference(orderReference);
        setFeeMinor(feeMinor);
    }

    public String getMerchant() {
        return merchant;
    }

    public final void setMerchant(String merchant) {
        if (merchant == null || merchant.isBlank()) {
            throw new IllegalArgumentException("Merchant must not be blank");
        }
        this.merchant = merchant;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public final void setOrderReference(String orderReference) {
        if (orderReference == null || orderReference.isBlank()) {
            throw new IllegalArgumentException("Order reference must not be blank");
        }
        this.orderReference = orderReference;
    }

    public int getFeeMinor() {
        return feeMinor;
    }

    public final void setFeeMinor(int feeMinor) {
        if (feeMinor < 0) {
            throw new IllegalArgumentException("Fee must not be negative");
        }
        this.feeMinor = feeMinor;
    }

    public long totalDebit() {
        return (long) getAmountMinor() + feeMinor;
    }

    public boolean matchesOrder(String merchant, String orderReference) {
        return this.merchant.equals(merchant) && this.orderReference.equals(orderReference);
    }

    public boolean exceedsFeePercent(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percent must be from 0 to 100");
        }
        return 100L * feeMinor > (long) getAmountMinor() * percent;
    }
}
