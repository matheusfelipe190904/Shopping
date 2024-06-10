package shopping;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Produto> produtos;
    private int quantidade;

    public Carrinho() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        boolean flag=true;
        quantidade+=produto.getQuantidade();
        for(Produto prod : this.produtos){
            if(Produto.compara(prod,produto)){
                prod.aumentarQuantidade(produto.getQuantidade());
                flag=false;
            }
            if(!flag) break;
        }
        if(flag) produtos.add(produto);
    }

    public double calcularTotal() {
        return produtos.stream().mapToDouble(Produto::calcularTotal).sum();
    }

    public int getQuantidadeItens() {
        return quantidade;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "produtos=" + produtos +
                '}';
    }
}