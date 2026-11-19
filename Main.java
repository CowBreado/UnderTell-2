import Game.Game;
import Inventory.Inventory;
import gameCharacters.Character;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Character character = new Character();
        Game.teamSetup(character);
        Game.addCharacterSkill(character);
        Game.intro();
        Game.gameMenu(inventory, character);
    }
}
