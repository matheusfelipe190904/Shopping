package shopping;

//estratégia utiliada a fim de não aplicar desconto.
public class NoDiscount implements DiscountStrategy {

    //compra sem desconto, a função retorna apenas o valor da compra, através da interface DiscountStrategy.
    @Override
    public double applyDiscount(Carrinho carrinho) {
        return carrinho.calcularTotal();
    }
}