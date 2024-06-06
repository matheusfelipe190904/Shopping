package shopping;
//classe que define os produtos

public class Produto {
    private String nome;
    private double preco;

    //construtor
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " - " + preco;
    }
}