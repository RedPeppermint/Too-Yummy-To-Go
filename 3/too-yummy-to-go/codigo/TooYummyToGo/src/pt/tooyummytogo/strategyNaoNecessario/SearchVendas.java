package pt.tooyummytogo.strategyNaoNecessario;

import pt.tooyummytogo.domain.Venda;
import pt.tooyummytogo.facade.dto.ProdutoInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchVendas implements SearchStrategy<Venda, List<ProdutoInfo>> {

    /**
     * Finds a list all ProdutoInfo within a certain time frame in a list of Venda
     *
     * @param timeFrameString The String with the time frame, as: startTime;endTime
     * @param list            The list to search in
     * @return Optional.empty() if no ProdutoInfo is found or Optional.of(the list with all the ProdutoInfo)
     */
    @Override
    public Optional<List<ProdutoInfo>> search(String timeFrameString, List<Venda> list) {
        String[] split = timeFrameString.split(";");
        LocalDateTime hi = LocalDateTime.parse(split[0]);
        LocalDateTime hf = LocalDateTime.parse(split[1]);

        List<ProdutoInfo> produtos = new ArrayList<>();
        for (Venda v : list) {
            if (v.estaDisponivel(hi, hf)) {
                produtos.addAll(v.getProdutosInfo());
            }
        }
        return produtos.isEmpty() ? Optional.empty() : Optional.of(produtos);
    }
}
