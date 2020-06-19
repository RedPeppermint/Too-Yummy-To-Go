package pt.tooyummytogo.domain;

import java.util.ArrayList;
import java.util.List;

public class TipoDeProduto {
    private String nome;
    private String codigo;
    private double preco;
    private List<Produto> produtos = new ArrayList<>();

    /**
     * Constructor of the class
     *
     * @param nome   The name
     * @param codigo The code
     * @param preco  The price
     */
    public TipoDeProduto(String nome, String codigo, double preco) {
        this.nome = nome;
        this.codigo = codigo;
        this.preco = preco;
    }

    /**
     * Returns the name of this TipoDeProduto
     *
     * @return the name of this TipoDeProduto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Adds a new Produto to the list produtos
     *
     * @param quantidade The amount of Produto to add
     * @return The created Produto
     */
    public Produto addProduto(int quantidade) {
        Produto p = new Produto(quantidade, this.preco);
        produtos.add(p);
        return p;
    }

    /**
     * Gets the codigo of this TipoDeProduto
     *
     * @return The codigo of this TipoDeProduto
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Updates the price of this TipoDeProduto
     *
     * @param preco The new price
     */
    public void atualizaPreco(double preco) {
        this.preco = preco;
    }
}
