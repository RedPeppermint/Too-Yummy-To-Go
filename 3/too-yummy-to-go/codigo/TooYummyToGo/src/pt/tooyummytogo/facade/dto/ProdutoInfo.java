package pt.tooyummytogo.facade.dto;

import pt.tooyummytogo.domain.Produto;

public class ProdutoInfo {

    private Produto produto;

    /**
     * Constructor of the class
     *
     * @param produto The Produto that matches this ProdutoInfo
     */
    public ProdutoInfo(Produto produto) {
        this.produto = produto;
    }

    /**
     * Gets the amount of produto
     *
     * @return The amount of the Produto
     */
    public int getQuantidade() {
        return produto.getQuantidade();
    }

    /**
     * Gets the price of produto
     *
     * @return The price of the Produto
     */
    public double getPreco() {
        return produto.getPreco();
    }

    /**
     * Checks if the amount of the ProdutoInfo is greater or equal to quantidade
     *
     * @param quantidade The amount to check
     * @return Whether the amount of ProdutoInfo is greater or equal to quantidade
     */
    public boolean verificaQuantidade(int quantidade) {
        return this.getQuantidade() >= quantidade;
    }

    /**
     * Reduces the amount of produto by quantidade
     *
     * @param quantidade The amount to reduce
     */
    public void reduzirQuantidade(Integer quantidade) {
        this.produto.reduzirQuantidade(quantidade);
    }

}
