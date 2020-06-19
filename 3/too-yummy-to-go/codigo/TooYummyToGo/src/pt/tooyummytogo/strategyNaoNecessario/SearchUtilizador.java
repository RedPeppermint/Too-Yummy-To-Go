package pt.tooyummytogo.strategyNaoNecessario;

import pt.tooyummytogo.domain.Utilizador;

import java.util.List;
import java.util.Optional;

public class SearchUtilizador implements SearchStrategy<Utilizador,Utilizador> {

    /**
     * Finds a Utilizador with a certain username in a list of Utilizador
     *
     * @param username The username to find
     * @param list     The list to search in
     * @return Optional.empty() if the Utilizador is not found or Optional.of(the found Utilizador)
     */
    @Override
    public Optional<Utilizador> search(String username, List<Utilizador> list) {
        for (Utilizador u : list) {
            if (u.getUsername().equals(username)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}
