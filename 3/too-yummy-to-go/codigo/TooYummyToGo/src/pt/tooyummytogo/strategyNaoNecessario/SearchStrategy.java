package pt.tooyummytogo.strategyNaoNecessario;

import java.util.List;
import java.util.Optional;

public interface SearchStrategy<L,R> {

    Optional<R> search(String find, List<L> list);

}