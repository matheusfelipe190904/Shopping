package shopping;

public class CombinedDiscount implements DiscountStrategy {
    private int minQuantity;
    private double minTotalPrice;
    private double discountPercentage;

    //construtor
    public CombinedDiscount(int minQuantity, double minTotalPrice, double discountPercentage) {
        this.minQuantity = minQuantity;
        this.minTotalPrice = minTotalPrice;
        this.discountPercentage = discountPercentage;
    }

    // Ele implementa a interface DiscountStrategy e aplica um desconto se tanto a quantidade de itens no carrinho quanto o total do carrinho excederem certos valores mínimos.
    @Override
    public double applyDiscount(Carrinho carrinho) {
        double total = carrinho.calcularTotal();
        if (carrinho.getQuantidadeItens() >= minQuantity && total >= minTotalPrice) {
            return total * (1 - discountPercentage / 100);
        }
        return total;
    }
}