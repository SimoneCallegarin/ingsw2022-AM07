package it.polimi.ingsw.Model;

public class CharacterCard {
    private int idCard;
    private int cost;
    private boolean toUse;
    private Effect effect;

    public CharacterCard(int idCard, int cost, boolean toUse, Effect effect) {
        this.idCard = idCard;
        this.cost = cost;
        this.toUse = toUse;
        this.effect = effect;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isToUse() {
        return toUse;
    }

    public void setToUse(boolean toUse) {
        this.toUse = toUse;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

}
