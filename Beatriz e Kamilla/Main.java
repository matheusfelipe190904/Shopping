package shopping; // Define o pacote onde a classe está localizada

import javax.swing.*; // Importa a biblioteca Swing para criação de interfaces gráficas em Java
import java.io.BufferedReader;
import java.io.FileReader; // Importa a classe FileReader para ler arquivos no sistema de arquivos
import java.io.IOException; // Importa a classe IOException para tratar possíveis erros de entrada e saída
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Carrinho carrinho = new Carrinho();

        // Categorias para o usuario
        String[] categorias = {"Alimentacao", "Roupa", "Eletrodomestico"}; // Cria um array de strings com categorias de produtos
        JComboBox<String> categoriasComboBox = new JComboBox<>(categorias); // Cria um JComboBox para permitir que o usuário selecione uma categoria

        // Produtos
        JComboBox<Produto> produtosComboBox = new JComboBox<>(); // Cria um JComboBox para a seleção de produtos
        JTextField quantidadeField = new JTextField(5); // Cria um campo de texto para entrada da quantidade de produtos
        // (columns: 5) = largura de 5 colunas


        // Painel principal
        JPanel panel = new JPanel(); // Cria um painel da interface
        GroupLayout layout = new GroupLayout(panel); // Gerencia a disposição dos componentes no painel
        panel.setLayout(layout); // Define o layout do painel
        layout.setAutoCreateGaps(true); // Criar automaticamente gaps (espaços) entre os componentes
        layout.setAutoCreateContainerGaps(true); // Criar gaps (espaços) nas bordas do container


        // Labels
        JLabel categoriaLabel = new JLabel("Escolha a categoria:");
        JLabel produtoLabel = new JLabel("Escolha o produto:");
        JLabel quantidadeLabel = new JLabel("Quantidade:");

        // Botões
        JButton adicionarButton = new JButton("Adicionar");
        JButton finalizarButton = new JButton("Finalizar");
        JButton cancelarButton = new JButton("Cancelar");




////////////////////////////////////////////////////////////////////////////////////////// DEFINICOES DE LAYOUT DA JANELA /////////////////////////////////////////////////////////////////////////////////Q

        // Layout Horizontal
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) // DEfine grupo alinhado à esquerda (LEADING)
                .addGroup(layout.createSequentialGroup() // Cria um grupo sequencial horizontalmente

                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) // Define um grupo paralelo para os labels, alinhado à esquerda
                                .addComponent(categoriaLabel) // Adiciona o labels
                                .addComponent(produtoLabel)
                                .addComponent(quantidadeLabel))

                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) // Define um grupo paralelo para os campos de entrada, alinhado à esquerda
                                .addComponent(categoriasComboBox) // Adiciona o JComboBox
                                .addComponent(produtosComboBox)
                                .addComponent(quantidadeField)))


                .addGroup(layout.createSequentialGroup() // Cria outro grupo sequencial horizontalmente para os botões
                        .addComponent(adicionarButton)
                        .addComponent(finalizarButton)
                        .addComponent(cancelarButton))
        );

        // Layout Vertical
        layout.setVerticalGroup(layout.createSequentialGroup() // Define um grupo sequencial verticalmente

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE) // Cria um grupo paralelo verticalmente para a primeira linha de componentes

                        .addComponent(categoriaLabel)
                        .addComponent(categoriasComboBox))


                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(produtoLabel)
                        .addComponent(produtosComboBox))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(quantidadeLabel)
                        .addComponent(quantidadeField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(adicionarButton)
                        .addComponent(finalizarButton)
                        .addComponent(cancelarButton))
        );


        // Adicionar Ações aos Botões
        adicionarButton.addActionListener(e -> adicionarProdutoAoCarrinho(carrinho, produtosComboBox, quantidadeField));
        finalizarButton.addActionListener(e -> mostrarResumoCompra(carrinho));
        cancelarButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Compra cancelada.");
            System.exit(0);
        });

        // Atualiza os produtos disponíveis quando uma categoria é selecionada
        categoriasComboBox.addActionListener(e -> {
            String categoriaSelecionada = (String) categoriasComboBox.getSelectedItem();
            List<Produto> produtosDisponiveis = lerProdutosDoArquivo(categoriaSelecionada.toLowerCase() + ".txt");
            produtosComboBox.setModel(new DefaultComboBoxModel<>(produtosDisponiveis.toArray(new Produto[0])));
        });

        // Frame Principal
        JFrame frame = new JFrame("Compra de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setPreferredSize(new java.awt.Dimension(400, 200)); // Definindo tamanho preferido da janela
        frame.pack();
        frame.setVisible(true);

        // Inicializa a seleção da primeira categoria
        categoriasComboBox.setSelectedIndex(0);
    }


    // Função para adicionar um produto ao carrinho
    private static void adicionarProdutoAoCarrinho(Carrinho carrinho, JComboBox<Produto> produtosComboBox, JTextField quantidadeField) {

        Produto produtoSelecionado = (Produto) produtosComboBox.getSelectedItem();  // Obtém o produto selecionado no JComboBox de produtos
        int quantidade = Integer.parseInt(quantidadeField.getText()); // Converte o texto do campo de quantidade para um inteiro
        if(produtoSelecionado == null){
            JOptionPane.showMessageDialog(null, "Não foi possível adicionar o produto ao carrinho, tente novamente.");
            return;
        }
        produtoSelecionado.setQuantidade(quantidade);
        carrinho.adicionarProduto(produtoSelecionado);
        JOptionPane.showMessageDialog(null, "Produto adicionado ao carrinho.");
    }

    private static void mostrarResumoCompra(Carrinho carrinho) {
        double totalSemDesconto = carrinho.calcularTotal();
        ArrayList<DiscountStrategy> discountList = new ArrayList<>();

        DiscountStrategy noDiscount = new NoDiscount();
        discountList.add(noDiscount);
        DiscountStrategy quantityDiscount = new QuantityDiscount(5, 10);
        discountList.add(quantityDiscount);
        DiscountStrategy totalPriceDiscount = new TotalPriceDiscount(1500, 20);
        discountList.add(totalPriceDiscount);
        DiscountStrategy combinedDiscount = new CombinedDiscount(10, 2500, 30);
        discountList.add(combinedDiscount);

        double finalPrice=totalSemDesconto;
        String discountMessage="Você não ganhou desconto";

        for(DiscountStrategy discountStrategy : discountList) {
            double price;
            price = discountStrategy.applyDiscount(carrinho);
            if (finalPrice > price) {
                finalPrice=price;
                if (discountStrategy instanceof NoDiscount) {
                    discountMessage = "Você não ganhou desconto";
                } else if (discountStrategy instanceof QuantityDiscount) {
                    discountMessage = String.format("Você ganhou um desconto de %.2f%% porque comprou %d ou mais unidades",
                            ((QuantityDiscount) discountStrategy).getDiscountPercentage(), ((QuantityDiscount) discountStrategy).getMinQuantity());
                }else if(discountStrategy instanceof TotalPriceDiscount){
                    discountMessage = String.format("Você ganhou um desconto de %.2f%% porque gastou mais de %.2f reais em sua  compra",
                            ((TotalPriceDiscount) discountStrategy).getDiscountPercentage(), ((TotalPriceDiscount) discountStrategy).getMinTotalPrice());
                }else if(discountStrategy instanceof CombinedDiscount){
                    discountMessage = String.format("Você ganhou um desconto de %.2f%% porque gastou mais de %.2f reais em sua compra e comprou %d ou mais unidades",
                            ((CombinedDiscount) discountStrategy).getDiscountPercentage(), ((CombinedDiscount) discountStrategy).getMinTotalPrice(), ((CombinedDiscount) discountStrategy).getMinQuantity());
                }
            }
        }

        double desconto = totalSemDesconto - finalPrice;

        StringBuilder mensagem = new StringBuilder("Itens no carrinho:\n");
        for (Produto produto : carrinho.getProdutos()) {
            mensagem.append(produto).append("\n");
            mensagem.append("Quantidade: ").append(produto.getQuantidade()).append("\n");
            mensagem.append("Preço total: ").append(produto.calcularTotal()).append("\n");
            mensagem.append("\n");
        }

        mensagem.append(discountMessage).append("\n");
        mensagem.append("Total sem desconto: ").append(String.format("%.2f", totalSemDesconto)).append("\n");
        mensagem.append("Valor do desconto: ").append(String.format("%.2f", desconto)).append("\n");
        mensagem.append("Total com desconto: ").append(String.format("%.2f", finalPrice)).append("\n");

        JOptionPane.showMessageDialog(null, mensagem.toString());
        System.exit(0);
    }

    private static List<Produto> lerProdutosDoArquivo(String nomeArquivo) {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[0].trim();
                double preco = Double.parseDouble(partes[1].trim());
                produtos.add(new Produto(nome, preco));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}
