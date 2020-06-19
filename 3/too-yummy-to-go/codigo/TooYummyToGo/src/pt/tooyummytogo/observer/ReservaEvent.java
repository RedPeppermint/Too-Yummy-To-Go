package pt.tooyummytogo.observer;

import java.time.LocalDateTime;

public class ReservaEvent implements Event {

    private String codigo;
    private LocalDateTime data;
    private double preco;

    /**
     * Constructor of the class, representing the event of creating a Reserva
     *
     * @param codigo The code of the Reserva
     * @param data   The date of the Reserva
     * @param preco  The price of the Reserva
     */
    public ReservaEvent(String codigo, LocalDateTime data, double preco) {
        this.codigo = codigo;
        this.data = data;
        this.preco = preco;
    }

    /**
     * Gets the code
     *
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Gets the date
     *
     * @return data
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Gets the price
     *
     * @return preco rounded to cents
     */
    public double getPreco() {
        return Math.floor((preco + 0.005) * 100) / 100;
    }
}
