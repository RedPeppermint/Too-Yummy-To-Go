package pt.tooyummytogo.facade;

import java.util.Optional;
import java.util.logging.Logger;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.domain.CatalogoComerciantes;
import pt.tooyummytogo.domain.CatalogoUtilizadores;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

/**
 * Esta é a classe do sistema.
 */
public class TooYummyToGo {

    private CatalogoUtilizadores catUtil = new CatalogoUtilizadores();
    private CatalogoComerciantes catComerc = new CatalogoComerciantes();


    // UC1
    public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
        return new RegistarUtilizadorHandler(catUtil);
    }

    /**
     * Returns an optional Session representing the authenticated user.
     *
     * @param username
     * @param password
     * @return UC2
     */
    public Optional<Sessao> autenticar(String username, String password) {
        Logger log = Logger.getLogger(TooYummyToGo.class.getName());
        if (catUtil.contains(username)) {
            if (catUtil.autenticar(username, password)) {
                log.info("Autenticacao efetuada com sucesso.");
                return Optional.of(new Sessao(catComerc, catUtil.getUtilizador(username).get()));
            } else {
                log.info("Palavra-passe errada.");
                return Optional.empty();
            }
        } else if (catComerc.contains(username)) {
            if (catComerc.autenticar(username, password)) {
                log.info("Autenticacao efetuada com sucesso.");
                return Optional.of(new Sessao(catComerc, catComerc.getComerciante(username).get()));
            } else {
                log.info("Palavra-passe errada.");
                return Optional.empty();
            }
        } else {
            log.info("Nome de utilizador nao existe.");
            return Optional.empty();
        }
    }

    // UC3
    public RegistarComercianteHandler getRegistarComercianteHandler() {
        return new RegistarComercianteHandler(catComerc);
    }

}
