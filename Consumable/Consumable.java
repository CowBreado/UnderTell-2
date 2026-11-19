package Consumable;

public class Consumable {
    public int healVal;

    public int manaVal;

    public int count;

    public String description;

    public String name;

    public Consumable(int healVal, int count, int manaVal, String description, String name) {
        this.healVal = healVal;
        this.count = count;
        this.manaVal = manaVal;
        this.description = description;
        this.name = name;
    }
}
