package pt.tooyummytogo.domain;

import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.observer.Observer;
import pt.tooyummytogo.observer.ReservaEvent;
import pt.tooyummytogo.strategyNaoNecessario.SearchStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public class Comerciante implements Observer<ReservaEvent> {

    private String username;
    private String password;
    private PosicaoCoordenadas local;

    private Map<String, TipoDeProduto> tiposProdutos = new HashMap<>();
    private List<Venda> vendas = new ArrayList<>();

    private List<String> bufferMensagensReservas = new ArrayList<>();

    /**
     * Constructor of the class Comerciante
     *
     * @param username    Username of the Comerciante
     * @param password    Password of the Comerciante
     * @param coordenadas Coordinates of the Comerciante
     */
    public Comerciante(String username, String password, PosicaoCoordenadas coordenadas) {
        this.username = username;
        this.password = password;
        this.local = coordenadas;
    }

    /**
     * Gets a list of the codigo's of the TipoDeProduto's in the list tiposProdutos
     *
     * @return a list of the Codigo of the TipoDeProduto's
     */
    public List<String> getList() {
        List<String> result = new ArrayList<>();
        for (TipoDeProduto value : tiposProdutos.values()) {
            result.add(value.getCodigo());
        }
        return result;
    }

    /**
     * Creates a Venda
     *
     * @return the created Venda
     */
    public Venda criarVenda() {
        return new Venda();
    }

    /**
     * Finds a Produto and adds an amount, quantidade, to it
     *
     * @param codigo     codigo of the Produto
     * @param quantidade amount to add to the Produto
     * @return The found Produto
     * @requires temProduto(codigo)
     */
    public Produto procurarProduto(String codigo, int quantidade) {
        return tiposProdutos.get(codigo).addProduto(quantidade);
    }

    /**
     * Checks if a Produto with codigo exists in the map
     *
     * @param codigo The codigo of the Produto
     * @return whether a Produto with codigo exists
     */
    public boolean temProduto(String codigo) {
        return tiposProdutos.containsKey(codigo);
    }

    /**
     * Adds a new Venda to the list of Vendas
     *
     * @param va The Venda to add
     */
    public void adicionaVenda(Venda va) {
        vendas.add(va);
    }

    /**
     * Checks if the Comerciante is within d km of coordinate
     *
     * @param coordinate The point to compare the distance to
     * @param d          The distance
     * @return Whether the point coordinate is within distance d km of Comerciante
     */
    public boolean estaNoRaio(PosicaoCoordenadas coordinate, int d) {
        return coordinate.distanciaEmMetros(local) <= d;
    }

    /**
     * Checks if this has Venda's during a certain time and within a certain place
     *
     * @param localizacao Where the Utilizador is
     * @param raio        The radius the Utilizador will go to
     * @param hi          The starting time the Utilizador is available
     * @param hf          The final time the Utilizador is available
     * @return Whether the Comerciante has Venda's within the requirements
     */
    public boolean estaDisponivel(PosicaoCoordenadas localizacao, int raio, LocalDateTime hi, LocalDateTime hf) {
        for (Venda v : vendas) {
            if (v.estaDisponivel(hi, hf) && estaNoRaio(localizacao, raio)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of Venda
     *
     * @return vendas
     */
    public List<Venda> getVendas() {
        return this.vendas;
    }

    /**
     * Returns the username
     *
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Adds a new TipoDeProduto. If it already exists, it updates it
     * If it catches an exception related to finding the class with in the properties file,
     * it sends a message to the stdout.
     *
     * @param tp    codigo of the TipoDeProduto
     * @param preco Price of the new TipoDeProduto
     */
    public void adicionaTipoProduto(String tp, double preco) {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(new File("config.properties")));
            String strategy = properties.getProperty("searchTipoProdutoStrategy");

            try {
                @SuppressWarnings("unchecked")
                Class<SearchStrategy<TipoDeProduto, TipoDeProduto>> klass = (Class<SearchStrategy<TipoDeProduto, TipoDeProduto>>) Class.forName(strategy);
                Constructor<SearchStrategy<TipoDeProduto, TipoDeProduto>> cons = klass.getConstructor();
                SearchStrategy<TipoDeProduto, TipoDeProduto> strat = cons.newInstance();
                Optional<TipoDeProduto> search = strat.search(tp, new ArrayList<>(tiposProdutos.values()));

                if (search.isPresent()) {
                    TipoDeProduto tipo = search.get();
                    atualizarPreco(tipo, preco);
                } else {
                    String codigo = tp + tiposProdutos.size();
                    tiposProdutos.put(codigo, new TipoDeProduto(tp, codigo, preco));
                }

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println("Class or constructor related exception");
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }

    }

    /**
     * Updates the price of a TipoDeProduto
     *
     * @param tp    TipdoDeProduto to update
     * @param preco New price
     */
    private void atualizarPreco(TipoDeProduto tp, double preco) {
        tp.atualizaPreco(preco);
    }

    @Override
    public void handleNewEvent(ReservaEvent e) {
        bufferMensagensReservas.add("Reserva " + e.getCodigo() + " feita em " +
                e.getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " com o valor " +
                e.getPreco() + "€.");
    }

    /**
     * Reads the message buffer and writes it into the logger.
     */
    public void lerBuffer() {
        Logger LOGGER = Logger.getLogger(Comerciante.class.getName());
        for (int i = 0; i < bufferMensagensReservas.size(); i++) {
            String msg = bufferMensagensReservas.get(i);
            LOGGER.info("Mensagem numero " + (i + 1) + ": " + msg);
        }
        bufferMensagensReservas.clear();

    }

    /**
     * Checks if a password matches this Comerciante's password
     *
     * @param password The password to check
     * @return Whether the password matches
     */
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
