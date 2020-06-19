package pt.tooyummytogo.domain;

import java.util.ArrayList;
import java.util.List;

public class Utilizador {
    private String username;
    private String password;
    private List<Reserva> reservas;

    /**
     * Constructor of the class
     *
     * @param username The username
     * @param password The password
     */
    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
        reservas = new ArrayList<>();
    }

    /**
     * Gets the username of the Utilizador
     *
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the list of Reserva's
     *
     * @return reservas
     */
    public List<Reserva> getReservas() {
        return this.reservas;
    }

    /**
     * Adds a Reserva to the list reservas
     *
     * @param r The Reserva to add
     */
    public void adicionaReserva(Reserva r) {
        reservas.add(r);
    }

    /**
     * Checks if a password matches this Utilizador's password
     *
     * @param password The password to check
     * @return Whether the password matches
     */
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
