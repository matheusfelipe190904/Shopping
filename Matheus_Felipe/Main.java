package shopping;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Carrinho carrinho = new Carrinho();

        System.out.print("Quantos produtos você deseja adicionar? ");
        int numProdutos = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha

        for (int i = 0; i < numProdutos; i++) {
            System.out.print("Digite o nome do produto: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o preço do produto: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();  // Consumir a nova linha

            carrinho.adicionarProduto(new Produto(nome, preco));
        }

        double totalSemDesconto = carrinho.calcularTotal();

        // Cria as estratégias possíveis
        DiscountStrategy noDiscount = new NoDiscount();
        DiscountStrategy quantityDiscount = new QuantityDiscount(5, 10);  // 10% de desconto para mais de 5 itens
        DiscountStrategy totalPriceDiscount = new TotalPriceDiscount(1500, 20);  // 20% de desconto para total acima de 1500
        DiscountStrategy combinedDiscount = new CombinedDiscount(10, 2500, 30);  // 30% de desconto para mais de 10 itens e total acima de 2500

        // Calcula os preços com cada estratégia
        double noDiscountPrice = noDiscount.applyDiscount(carrinho);
        double quantityDiscountPrice = quantityDiscount.applyDiscount(carrinho);
        double totalPriceDiscountPrice = totalPriceDiscount.applyDiscount(carrinho);
        double combinedDiscountPrice = combinedDiscount.applyDiscount(carrinho);

        // Encontra o menor preço
        double finalPrice = Math.min(Math.min(noDiscountPrice, quantityDiscountPrice),
                Math.min(totalPriceDiscountPrice, combinedDiscountPrice));

        double desconto = totalSemDesconto - finalPrice;

        System.out.println("Itens no carrinho:");
        carrinho.getProdutos().forEach(System.out::println);

        System.out.println("Total sem desconto: " + totalSemDesconto);
        System.out.println("Valor do desconto: " + desconto);
        System.out.println("Total com desconto: " + finalPrice);

        scanner.close();
    }
}