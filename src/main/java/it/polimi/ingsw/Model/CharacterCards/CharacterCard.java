package it.polimi.ingsw.Model.CharacterCards;

public class CharacterCard {
    private final int idCard;
    private int cost;
    private boolean used;
    private Effect effect;

    public CharacterCard(int idCard, int cost, Effect effect) {
        this.idCard = idCard;
        this.cost = cost;
        this.used = false;
        this.effect = effect;
    }

    public int getIdCard() {
        return idCard;
    }

    public int getCost() {
        return cost;
    }

    public void isUsed() {
        used = true;
        cost += 1;
    }

    public boolean getUsed() {
        return used;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

}
