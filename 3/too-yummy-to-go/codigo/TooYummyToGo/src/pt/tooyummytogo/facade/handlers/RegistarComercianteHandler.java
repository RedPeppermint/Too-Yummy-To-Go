package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.CatalogoComerciantes;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class RegistarComercianteHandler {

    private CatalogoComerciantes catComerc;

    public RegistarComercianteHandler(CatalogoComerciantes catComerc) {
        this.catComerc = catComerc;
    }

    /**
     * Registers a new Comerciante
     *
     * @param username The username
     * @param password The passoword
     * @ensures {@code catComerc.contains(username)}
     */
    public void registarComerciante(String username, String password, PosicaoCoordenadas p) {
        if (!catComerc.contains(username)) {
            Comerciante com = new Comerciante(username, password, p);
            catComerc.addComerciante(com);

        } else {
            System.out.println("Erro: esse nome de utilizador ja existe.");
        }
    }

}
