package pt.tooyummytogo;

import pt.tooyummytogo.domain.CatalogoComerciantes;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.exceptions.NoPermissionsException;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

import java.util.Optional;

public class Sessao {

    private final Optional<Comerciante> ca;
    private final Optional<Utilizador> ua;
    private final CatalogoComerciantes catComer;

    public Sessao(CatalogoComerciantes catComer, Comerciante comerciante) {
        this.ca = Optional.of(comerciante);
        this.ua = Optional.empty();
        this.catComer = catComer;
    }

    public Sessao(CatalogoComerciantes catComer, Utilizador utilizador) {
        this.ca = Optional.empty();
        this.ua = Optional.of(utilizador);
        this.catComer = catComer;
    }

    // UC4
    public AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler() throws NoPermissionsException {
        if (ca.isPresent()) {
            return new AdicionarTipoDeProdutoHandler(ca.get());
        } else {
            throw new NoPermissionsException();
        }
    }

    // UC5
    public ColocarProdutoHandler getColocarProdutoHandler() throws NoPermissionsException {
        if (ca.isPresent()) {
            return new ColocarProdutoHandler(ca.get());
        } else {
            throw new NoPermissionsException();
        }
    }

    public EncomendarHandler getEncomendarHandler() throws NoPermissionsException {
        if (ua.isPresent()) {
            return new EncomendarHandler(catComer, ua.get());
        } else {
            throw new NoPermissionsException();
        }
    }

    public String getNomeAtual() {
        if (ua.isPresent()) {
            return this.ua.get().getUsername();
        }
        return this.ca.get().getUsername();
    }
}
