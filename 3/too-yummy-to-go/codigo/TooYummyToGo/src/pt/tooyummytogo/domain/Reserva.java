package pt.tooyummytogo.domain;

import pt.portugueseexpress.InvalidCardException;
import pt.tooyummytogo.adapter.MyCard;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.observer.Observable;
import pt.tooyummytogo.observer.ReservaEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Reserva extends Observable<ReservaEvent> {

    Map<ProdutoInfo, Integer> produtos = new HashMap<>();
    private String codigo;
    private double preco = 0;

    /**
     * Adds a ProdutoInfo to the map
     *
     * @param produto    ProdutoInfo to add
     * @param quantidade Amount of Produto to add
     * @requires produto.getQuantidade() >= quantidade
     */
    public void addProduto(ProdutoInfo produto, int quantidade) {
        produtos.put(produto, quantidade);
        preco += produto.getPreco() * quantidade;
    }

    /**
     * Sets a payment via PortugueseExpress
     *
     * @param pg       The PortugueseExpress card
     * @param username Username of the paying Utilizador
     * @param number   Number of the Reserva
     * @return Optional.empty() if the payment was impossible or Optional.of(username + number) if it went through
     */
    public Optional<String> definirPagamento(MyCard pg, String username, int number) {
        this.codigo = username + number;

        if (pg.validate()) {
            try {
                pg.block(preco);
                pg.charge(preco);

                dispatchEvent(new ReservaEvent(codigo, LocalDateTime.now(), preco));
                return Optional.of(codigo);
            } catch (InvalidCardException e) {
                System.out.println("Cartao invalido");
                return Optional.empty();
            }
        } else {
            System.out.println("Cartao expirado");
            return Optional.empty();
        }

    }

    /**
     * Updates the amount of each ProdutoInfo
     */
    public void atualizaQuantidade() {
        for (ProdutoInfo p : produtos.keySet()) {
            p.reduzirQuantidade(produtos.get(p));
        }
    }
}
