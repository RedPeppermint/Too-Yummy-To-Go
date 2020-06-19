package pt.tooyummytogo.strategyNaoNecessario;

import pt.tooyummytogo.domain.Comerciante;

import java.util.List;
import java.util.Optional;

public class SearchComerciante implements SearchStrategy<Comerciante, Comerciante> {

    /**
     * Finds a Comerciante with a certain username in a list of Comerciante
     *
     * @param username The username to find
     * @param list     The list to search in
     * @return Optional.empty() if the Comerciante is not found or Optional.of(the found Comerciante)
     */
    @Override
    public Optional<Comerciante> search(String username, List<Comerciante> list) {
        for (Comerciante c : list) {
            if (c.getUsername().equals(username)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
}
