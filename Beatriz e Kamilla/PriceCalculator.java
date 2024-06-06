package shopping;

public class PriceCalculator {
    private DiscountStrategy strategy;

    public PriceCalculator(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculatePrice(Carrinho carrinho) {
        return strategy.applyDiscount(carrinho);
    }
}