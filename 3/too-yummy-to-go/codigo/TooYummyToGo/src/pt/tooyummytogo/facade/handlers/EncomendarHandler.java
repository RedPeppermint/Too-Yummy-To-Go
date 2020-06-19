package pt.tooyummytogo.facade.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import pt.tooyummytogo.domain.CatalogoComerciantes;
import pt.tooyummytogo.adapter.MyCard;
import pt.tooyummytogo.domain.Reserva;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.observer.Observer;
import pt.tooyummytogo.observer.ReservaEvent;
import pt.tooyummytogo.strategy.FindStrategy;

public class EncomendarHandler {

    private CatalogoComerciantes catComerciantes;
    private PosicaoCoordenadas localizacaoAtual;
    private int raio;
    private LocalDateTime hi;
    private LocalDateTime hf;
    private Reserva r;
    private Utilizador u;

    /**
     * The constructor the the class
     *
     * @param catComerciantes The Catalogue of Comerciante
     * @param u               The current Utilizador
     */
    public EncomendarHandler(CatalogoComerciantes catComerciantes, Utilizador u) {
        this.catComerciantes = catComerciantes;
        this.u = u;
    }

    /**
     * Gets the raio of this Utilizador
     *
     * @return raio
     */
    public int getRaio() {
        return raio;
    }

    /**
     * Gets the start time that this Utilizador is available
     *
     * @return hi
     */
    public LocalDateTime getHi() {
        return hi;
    }

    /**
     * Gets the end time that this Utilizador is available
     *
     * @return hf
     */
    public LocalDateTime getHf() {
        return hf;
    }

    /**
     * Gets the Catalogue of Comerciante
     *
     * @return catComerciantes
     */
    public CatalogoComerciantes getCat() {
        return this.catComerciantes;
    }

    /**
     * Sets the location of the current Utilzador and finds ComercianteInfo's within 5 km of him and 1h from now
     *
     * @param coordinate
     * @return The list of ComercianteInfo within 5 km of the current Utilizador
     * and between {@code LocalDateTime.now()} and {@code LocalDateTime.now().plusHours(1)}
     */
    public List<ComercianteInfo> indicaLocalizacaoActual(PosicaoCoordenadas coordinate) {
        localizacaoAtual = coordinate;
        raio = 5;
        hi = LocalDateTime.now();
        hf = hi.plusHours(1);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("config.properties")));
            String strategy = properties.getProperty("findRaioStrategy");

            try {
                @SuppressWarnings("unchecked")
                Class<FindStrategy> klass = (Class<FindStrategy>) Class.forName(strategy);
                Constructor<FindStrategy> cons = klass.getConstructor();
                FindStrategy strat = cons.newInstance();
                return strat.find(localizacaoAtual, this);

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println("Class or constructor related exception");
                return new ArrayList<>();
            }
        } catch (
                IOException e) {
            System.out.println("IOException");
            return new ArrayList<>();
        }
    }

    /**
     * Changes the radius of search
     *
     * @param r The new radius
     * @return The list of ComercianteInfo within r km of the current Utilizador
     * and between {@code LocalDateTime.now()} and {@code LocalDateTime.now().plusHours(1)}
     */
    public List<ComercianteInfo> redefineRaio(int r) {
        raio = r;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("config.properties")));
            String strategy = properties.getProperty("findRaioStrategy");

            try {
                @SuppressWarnings("unchecked")
                Class<FindStrategy> klass = (Class<FindStrategy>) Class.forName(strategy);
                Constructor<FindStrategy> cons = klass.getConstructor();
                FindStrategy strat = cons.newInstance();
                return strat.find(localizacaoAtual, this);

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println("Class or constructor related exception");
                return new ArrayList<>();
            }
        } catch (
                IOException e) {
            System.out.println("IOException");
            return new ArrayList<>();
        }
    }

    /**
     * Changes the times of search
     *
     * @param inicio The new starting hour
     * @param fim    The new final hour
     * @return The list of ComercianteInfo within raio km of the current Utilizador
     * and between inicio and fim
     */
    public List<ComercianteInfo> redefinePeriodo(LocalDateTime inicio, LocalDateTime fim) {
        hi = inicio;
        hf = fim;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("config.properties")));
            String strategy = properties.getProperty("findTempoStrategy");

            try {
                @SuppressWarnings("unchecked")
                Class<FindStrategy> klass = (Class<FindStrategy>) Class.forName(strategy);
                Constructor<FindStrategy> cons = klass.getConstructor();
                FindStrategy strat = cons.newInstance();
                return strat.find(localizacaoAtual, this);

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println("Class or constructor related exception");
                return new ArrayList<>();
            }
        } catch (
                IOException e) {
            System.out.println("IOException");
            return new ArrayList<>();
        }
    }

    /**
     * Creates the Reserva, makes adds and observer to comercianteInfo and gets the list of ProdutoInfo
     * from comercianteInfo between hi and hf
     *
     * @param comercianteInfo The ComercianteInfo chosen by the current Utilizador
     * @return The list of ProdutoInfo from comercianteInfo between hi and hf
     */
    public List<ProdutoInfo> escolheComerciante(ComercianteInfo comercianteInfo) {
        r = new Reserva();
        addObserver(comercianteInfo);
        return comercianteInfo.getListaProdutos(hi, hf).orElse(new ArrayList<>());
    }

    /**
     * Adds a Produto and amount of it to the current Reserva if there's enough of the Produto
     *
     * @param produto    The Produto to add
     * @param quantidade The amount to add
     */
    public void indicaProduto(ProdutoInfo produto, int quantidade) {
        if (produto.verificaQuantidade(quantidade)) {
            r.addProduto(produto, quantidade);
        } else {
            System.out.println("O comerciante apenas tem " + produto.getQuantidade() + ".");
        }
    }

    /**
     * Pays for the Reserva
     *
     * @param mycard The card to pay with
     * @return Whether the payment went through
     */
    public String indicaPagamento(MyCard mycard) {
        Optional<String> codReserva = r.definirPagamento(mycard, u.getUsername(), u.getReservas().size());
        if (codReserva.isEmpty()) {
            System.out.println("Pagamento sem sucesso");
            return "nao";
        } else {
            r.atualizaQuantidade();
            u.adicionaReserva(r);
            return codReserva.get();
        }
    }

    /**
     * Adds an Observer to the current Reserva
     *
     * @param o The Observer to add
     */
    public void addObserver(Observer<ReservaEvent> o) {
        r.addObserver(o);
    }

}
