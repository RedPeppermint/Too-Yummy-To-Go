package pt.tooyummytogo.domain;

import pt.tooyummytogo.facade.dto.ProdutoInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {

    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;
    private List<Produto> produtos = new ArrayList<>();

    /**
     * Adds a Produto to the list
     *
     * @param p The Produto to add
     */
    public void adicionaProduto(Produto p) {
        produtos.add(p);
    }

    /**
     * Sets horaInicio
     *
     * @param horaInicio Start time
     */
    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Sets horaFim
     *
     * @param horaFim End time
     */
    public void setHoraFim(LocalDateTime horaFim) {
        this.horaFim = horaFim;
    }

    /**
     * Sets horaFim and horaInicio
     *
     * @param horaInicio Start time
     * @param horaFim    End time
     */
    public void adicionaHoras(LocalDateTime horaInicio, LocalDateTime horaFim) {
        setHoraInicio(horaInicio);
        setHoraFim(horaFim);
    }

    /**
     * Checks if this Venda is available during a certain time
     *
     * @param inicio Start time
     * @param fim    End time
     * @return Whether this Venda is available during this time
     */
    public boolean estaDisponivel(LocalDateTime inicio, LocalDateTime fim) {
        return !(inicio.compareTo(horaFim) > 0 && fim.compareTo(horaInicio) < 0);
    }

    /**
     * Turns each element of produtos into a ProdutoInfo and returns the new list
     *
     * @return The list of produtos turned into a list of ProdutoInfo
     */
    public List<ProdutoInfo> getProdutosInfo() {
        List<ProdutoInfo> result = new ArrayList<>();
        for (Produto produto : produtos) {
            result.add(new ProdutoInfo(produto));
        }
        return result;
    }
}
