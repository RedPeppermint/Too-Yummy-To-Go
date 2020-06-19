package pt.tooyummytogo.facade.dto;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Venda;
import pt.tooyummytogo.observer.Observer;
import pt.tooyummytogo.observer.ReservaEvent;
import pt.tooyummytogo.strategyNaoNecessario.SearchStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

public class ComercianteInfo implements Observer<ReservaEvent> {

    private Comerciante comerc;

    /**
     * Constructor of the class
     *
     * @param comerc The Comerciante that matches this ComercianteInfo
     */
    public ComercianteInfo(Comerciante comerc) {
        this.comerc = comerc;
    }


    /**
     * Finds the Venda's within a certain time frame
     *
     * @param hi Start time
     * @param hf End time
     * @return Optional.of(The list of Venda ' s within the time frame) or Optional.empty() if something goes wrong
     * or there are no Venda's that match the time
     */
    public Optional<List<ProdutoInfo>> getListaProdutos(LocalDateTime hi, LocalDateTime hf) {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(new File("config.properties")));
            String strategy = properties.getProperty("searchVendasStrategy");

            try {
                @SuppressWarnings("unchecked")
                Class<SearchStrategy<Venda, List<ProdutoInfo>>> klass = (Class<SearchStrategy<Venda, List<ProdutoInfo>>>) Class.forName(strategy);
                Constructor<SearchStrategy<Venda, List<ProdutoInfo>>> cons = klass.getConstructor();
                SearchStrategy<Venda, List<ProdutoInfo>> strat = cons.newInstance();
                Optional<List<ProdutoInfo>> search = strat.search(
                        hi.toString() + ";" + hf.toString(), comerc.getVendas());
                return search;

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println("Class or constructor related exception");
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return Optional.empty();
    }

    @Override
    public void handleNewEvent(ReservaEvent reservaEvent) {
        this.comerc.handleNewEvent(reservaEvent);
    }
}
