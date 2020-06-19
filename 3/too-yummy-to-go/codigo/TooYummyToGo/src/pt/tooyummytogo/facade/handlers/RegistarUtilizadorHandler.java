package pt.tooyummytogo.facade.handlers;


import pt.tooyummytogo.domain.CatalogoUtilizadores;
import pt.tooyummytogo.domain.Utilizador;

public class RegistarUtilizadorHandler {

    CatalogoUtilizadores catUtil;

    public RegistarUtilizadorHandler(CatalogoUtilizadores catUtil) {
        this.catUtil = catUtil;
    }

    /**
     * Registers a Utilizador
     *
     * @param username The username
     * @param password The password
     * @ensures {@code catUtil.contains(username)}
     */
    public void registarUtilizador(String username, String password) {
        Utilizador user = new Utilizador(username, password);
        catUtil.addUtilizador(user);
    }

}
