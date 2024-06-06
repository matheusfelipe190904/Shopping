package shopping;

public class TotalPriceDiscount implements DiscountStrategy {
    private double minTotalPrice;
    private double discountPercentage;

    public TotalPriceDiscount(double minTotalPrice, double discountPercentage) {
        this.minTotalPrice = minTotalPrice;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double applyDiscount(Carrinho carrinho) {
        double total = carrinho.calcularTotal();
        if (total >= minTotalPrice) {
            return total * (1 - discountPercentage / 100);
        }
        return total;
    }
}