package pt.tooyummytogo.strategyNaoNecessario;

import pt.tooyummytogo.domain.TipoDeProduto;

import java.util.List;
import java.util.Optional;

public class SearchTipoProduto implements SearchStrategy<TipoDeProduto,TipoDeProduto> {

    /**
     * Finds a SearchTipoProduto with a certain name in a list of SearchTipoProduto
     * @param nome The name to find
     * @param list The list to search in
     * @return Optional.empty() if the SearchTipoProduto is not found or Optional.of(the found SearchTipoProduto)
     */
    @Override
    public Optional<TipoDeProduto> search(String nome, List<TipoDeProduto> list) {
        for (TipoDeProduto tp : list) {
            if (tp.getNome().equals(nome)) {
                return Optional.of(tp);
            }
        }
        return Optional.empty();
    }
}
