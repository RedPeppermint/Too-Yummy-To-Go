package pt.tooyummytogo.domain;

public class Produto {
    private int quantidade;
    private double preco;

    /**
     * Constructor of the class
     *
     * @param quantidade Amount of the Produto
     * @param preco      Price of the Produto
     */
    public Produto(int quantidade, double preco) {
        this.quantidade = quantidade;
        this.preco = preco;

    }

    /**
     * Returns the quantidade of this Produto
     *
     * @return The quantidade
     */
    public int getQuantidade() {
        return this.quantidade;
    }

    /**
     * Returns the preco of this Produto
     *
     * @return The preco
     */
    public double getPreco() {
        return this.preco;
    }

    /**
     * Reduces the amount of this Produto
     *
     * @param quantidade Amount to reduce
     */
    public void reduzirQuantidade(Integer quantidade) {
        this.quantidade -= quantidade;
    }
}
