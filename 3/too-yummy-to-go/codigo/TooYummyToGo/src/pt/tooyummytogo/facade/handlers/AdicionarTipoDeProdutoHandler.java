package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.Comerciante;

public class AdicionarTipoDeProdutoHandler {

    private Comerciante ca;

    /**
     * Constuctor of the class
     *
     * @param ca The current Comerciante
     */
    public AdicionarTipoDeProdutoHandler(Comerciante ca) {
        this.ca = ca;

    }

    /**
     * Registers a new TipoDeProduto
     *
     * @param tipoDeProduto The name of the TipoDeProduto
     * @param preco         The price of the TipoDeProduto
     */
    public void registaTipoDeProduto(String tipoDeProduto, double preco) {
        ca.adicionaTipoProduto(tipoDeProduto, preco);
    }

}
