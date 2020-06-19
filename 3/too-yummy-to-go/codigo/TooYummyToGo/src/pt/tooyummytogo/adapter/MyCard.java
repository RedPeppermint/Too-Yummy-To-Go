package pt.tooyummytogo.adapter;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;
import pt.portugueseexpress.InvalidCardException;
import pt.portugueseexpress.PortugueseExpress;

import java.util.Optional;

public class MyCard {

    private final Optional<Card> mc;
    MonsterCardAPI mcAPI = new MonsterCardAPI();
    private final Optional<PortugueseExpress> pg;

    /**
     * Constructor of a MyCard that receives a MonsterCard
     * @param mc a MonsterCard
     */
    public MyCard(Card mc) {
        this.mc = Optional.of(mc);
        this.pg = Optional.empty();
    }

    /**
     * Constructor of a MyCard that receives a PortugueseExpress Card
     * @param pg a PortugueseExpress Card
     */
    public MyCard(PortugueseExpress pg) {
        this.pg = Optional.of(pg);
        this.mc = Optional.empty();
    }

    /**
     * Validates the card that was received in the constructor
     * @return whether the card is validated
     */
    public boolean validate() {
        if (this.pg.isPresent()) {
            return pg.get().validate();
        }
        return mc.get().validate();

    }

    /**
     * Blocks the card
     * @param preco the amount to block
     * @throws InvalidCardException if the card is invalid
     */
    public void block(double preco) throws InvalidCardException {
        if (this.pg.isPresent()) {
            pg.get().block(preco);
            return;
        }
        mcAPI.block(mc.get(), preco);
    }

    /**
     * Charges the card
     * @param preco amount to charge
     * @throws InvalidCardException if the card is invalid
     */
    public void charge(double preco) throws InvalidCardException {
        if (this.pg.isPresent()) {
            pg.get().charge(preco);
            return;
        }
        mcAPI.charge(mc.get(), preco);
    }
}
