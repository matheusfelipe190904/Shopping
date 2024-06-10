package shopping;
//estratégia utilizada para aplicar desconto com base na quantidade de itens

public class QuantityDiscount implements DiscountStrategy {
    private int minQuantity;
    private double discountPercentage;

    public double getDiscountPercentage(){return discountPercentage;}
    public int getMinQuantity(){return minQuantity;}

    //construtor que inicializa a quantidade minima e o desconto.
    public QuantityDiscount(int minQuantity, double discountPercentage) {
        this.minQuantity = minQuantity;
        this.discountPercentage = discountPercentage;
    }

    //função que verifica se a quantidade de produto é mínima ou não, caso for, ela recebe desconto, senão ela nao recebe, através da interface DiscountStrategy.
    @Override
    public double applyDiscount(Carrinho carrinho) {
        if (carrinho.getQuantidadeItens() >= minQuantity) {
            return carrinho.calcularTotal() * (1 - discountPercentage / 100);
        }
        return carrinho.calcularTotal();
    }
}