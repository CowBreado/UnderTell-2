package Inventory;

import Consumable.Consumable;
import Consumable.Potion;
import gameCharacters.Character;
import java.util.HashMap;
import java.util.Scanner;

public class Inventory {
    public static HashMap<String, Consumable> characterInventory = new HashMap<>();

    static Scanner sc = new Scanner(System.in);

    public Inventory() {
        Potion potion = new Potion();
        addInventory("potion", (Consumable)potion);
        addInventory("potion", (Consumable)potion);
        addInventory("potion", (Consumable)potion);
    }

    public void useInventory(Character character) {
        System.out.print("Enter the name of the item you wish to consume: ");
        String name = sc.nextLine().toLowerCase();
        if (characterInventory.containsKey(name)) {
            Character.displayTeam();
            System.out.println(name + ", " + name);
            System.out.print("Select the index of the character you want to heal: ");
            int target = sc.nextInt();
            sc.nextLine();
            if (target >= Character.teamCharacters.size() || target < 0) {
                System.out.println("Invalid Target Index.");
                return;
            }
            if (((Character)Character.teamCharacters.get(target)).hp <= 0.0F) {
                System.out.println("Cannot heal fallen allies.");
                return;
            }
            if (target != 0)
                ((Character)Character.teamCharacters.get(target)).mana += ((Consumable)characterInventory.get(name)).manaVal;
            System.out.println("You used: " + name + " on " + ((Character)Character.teamCharacters.get(target)).name);
            System.out.println(((Character)Character.teamCharacters.get(target)).name + "'s HP: " + ((Character)Character.teamCharacters.get(target)).name + ", MP: " + ((Character)Character.teamCharacters.get(target)).hp);
            ((Character)Character.teamCharacters.get(target)).hp += ((Consumable)characterInventory.get(name)).healVal;
            ((Consumable)characterInventory.get(name)).count--;
            if (((Consumable)characterInventory.get(name)).count <= 0)
                characterInventory.remove(name);
            if (((Character)Character.teamCharacters.get(target)).hp >= ((Character)Character.teamCharacters.get(target)).maxHp)
                ((Character)Character.teamCharacters.get(target)).hp = ((Character)Character.teamCharacters.get(target)).maxHp;
            if (((Character)Character.teamCharacters.get(target)).mana >= ((Character)Character.teamCharacters.get(target)).maxMana)
                ((Character)Character.teamCharacters.get(target)).mana = ((Character)Character.teamCharacters.get(target)).maxMana;
        } else if (!characterInventory.containsKey(name)) {
            System.out.println(name + " not found.");
            return;
        }
    }

    public void displayInventory() {
        if (!characterInventory.isEmpty()) {
            System.out.println("\t--- --- ---Player Inventory--- --- ---");
            for (String item : characterInventory.keySet())
                System.out.println("\t         Items: " + item + " #" + ((Consumable)characterInventory.get(item)).count);
        } else {
            System.out.println("\t--- --- ---Player Inventory--- --- ---");
            System.out.println("\t         Inventory is empty.");
        }
    }

    public void addInventory(String name, Consumable consumable) {
        if (characterInventory.containsKey(name)) {
            ((Consumable)characterInventory.get(name)).count++;
        } else if (!characterInventory.containsKey(name)) {
            characterInventory.put(name, consumable);
        }
    }
}
