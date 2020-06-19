package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Produto;
import pt.tooyummytogo.domain.Venda;

import java.time.LocalDateTime;
import java.util.List;

public class ColocarProdutoHandler {

    private Comerciante ca;
    private Venda va;

    /**
     * Constructor of the class
     *
     * @param ca The current Comerciante
     */
    public ColocarProdutoHandler(Comerciante ca) {
        this.ca = ca;
    }

    /**
     * Creates a new venda and returns the list of codigos of TipoDeProduto from the current Comerciante
     *
     * @return The list of codigos of TipoDeProduto from Comerciante
     * @ensures {@code va != null}
     */
    public List<String> inicioDeProdutosHoje() {
        va = ca.criarVenda();
        return ca.getList();
    }

    /**
     * Tries to add a Produto to the current Venda, and returns whether it succeeds
     *
     * @param codigo     The codigo of the TipoDeProdutoProduto
     * @param quantidade The amount of the Produto
     * @return true if the TipoDeProduto exists and it added the Produto, false if it did not
     */
    public boolean indicaProduto(String codigo, int quantidade) {
        if (ca.temProduto(codigo)) {
            Produto p = ca.procurarProduto(codigo, quantidade);
            va.adicionaProduto(p);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param now
     * @param plusHours
     */
    public void confirma(LocalDateTime now, LocalDateTime plusHours) {
        va.adicionaHoras(now, plusHours);
        ca.adicionaVenda(va);
    }

}
