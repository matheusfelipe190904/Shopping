package shopping;

public class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(Carrinho carrinho) {
        return carrinho.calcularTotal();
    }
}