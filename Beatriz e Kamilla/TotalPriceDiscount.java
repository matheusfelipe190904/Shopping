package shopping;
//estrategia para calcular o total da compra, considerando o desconto.

public class TotalPriceDiscount implements DiscountStrategy {
    private double minTotalPrice;
    private double discountPercentage;

    //contrutor.
    public TotalPriceDiscount(double minTotalPrice, double discountPercentage) {
        this.minTotalPrice = minTotalPrice;
        this.discountPercentage = discountPercentage;
    }

    //função que calcula o preço total de acordo com o seu desconto, através da interface DiscountStrategy.
    @Override
    public double applyDiscount(Carrinho carrinho) {
        double total = carrinho.calcularTotal();
        if (total >= minTotalPrice) {
            return total * (1 - discountPercentage / 100);
        }
        return total;
    }
}