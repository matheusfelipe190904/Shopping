package shopping;
//classe que define os produtos

public class Produto {
    private String nome;
    private double preco;
    private int quantidade=1;

    //construtor
    public Produto(String nome, double preco){
        this.nome=nome;
        this.preco = preco;
    }

    public int getQuantidade(){
        return quantidade;
    }
    public String getNome(){ return nome;}
    public double getPreco(){ return preco;}

    public void setQuantidade(int quantidade){this.quantidade=quantidade;}

    public void aumentarQuantidade(int quantidade){this.quantidade+=quantidade;}
    public double calcularTotal(){return preco*(double)quantidade;}
    public static boolean compara(Produto a, Produto b){
        return a.nome.equals(b.nome);
    }

    @Override
    public String toString() {
        return nome + " - " + preco;
    }
}