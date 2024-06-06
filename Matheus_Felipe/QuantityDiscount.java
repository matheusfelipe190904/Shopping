package shopping;

public class QuantityDiscount implements DiscountStrategy {
    private int minQuantity;
    private double discountPercentage;

    public QuantityDiscount(int minQuantity, double discountPercentage) {
        this.minQuantity = minQuantity;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double applyDiscount(Carrinho carrinho) {
        if (carrinho.getQuantidadeItens() >= minQuantity) {
            return carrinho.calcularTotal() * (1 - discountPercentage / 100);
        }
        return carrinho.calcularTotal();
    }
}