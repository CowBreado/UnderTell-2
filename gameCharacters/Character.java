package gameCharacters;

import Enemies.Enemy;
import Skills.Skills;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Character {
    public int maxHp;

    public int atk;

    public int mana;

    public int maxMana;

    public int exp;

    public int level;

    public float hp;

    public String name;

    public boolean isDefend = false;

    public boolean deathDebuffed = false;

    public static HashMap<String, Skills> characterSkills = new HashMap<>();

    public static ArrayList<Character> teamCharacters = new ArrayList<>();

    public static Scanner sc = new Scanner(System.in);

    public Character() {}

    Character(float hp, int maxHp, int atk, int mana, int maxMana, int exp, int level, String name, boolean isDefend, boolean deathDebuffed) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.atk = atk;
        this.mana = mana;
        this.maxMana = maxMana;
        this.exp = exp;
        this.level = level;
        this.name = name;
        this.isDefend = isDefend;
        this.deathDebuffed = deathDebuffed;
    }

    public static void addTeamCharacter(Character character) {
        teamCharacters.add(character);
    }

    public static void displayTeam() {
        int i = 0;
        for (Character character : teamCharacters) {
            System.out.printf("Index: " + i + "     Name:\t%-15s", new Object[] { character.name + "\t" });
            System.out.printf("\t| HP:\t%-15s", new Object[] { "" + character.hp + "\t| Max HP: " + character.maxHp });
            System.out.printf("\t| Attack:\t%-4s", new Object[] { "" + character.atk + " " });
            System.out.printf("\t| Mana:\t%-6s", new Object[] { "" + character.mana + "\t| \tMax Mana: \t" + character.maxMana });
            System.out.printf("\t| LVL:\t%-6s", new Object[] { "" + character.level + "\t| EXP: " + character.level });
            System.out.println();
            i++;
        }
    }

    public static void characterLevelUp(Character character) {
        System.out.println();
        int levelUpValue = 100;
        for (Character currentCharacter : teamCharacters) {
            if (currentCharacter.exp >= 100)
                while (currentCharacter.exp >= levelUpValue) {
                    int healthUp = (int)(Math.random() * 3.0D + 1.0D);
                    int attackUp = (int)(Math.random() * 3.0D + 1.0D);
                    int manaUp = (int)(Math.random() * 3.0D + 1.0D);
                    if (currentCharacter.hp > 0.0F)
                        currentCharacter.hp += healthUp;
                    currentCharacter.maxHp += healthUp;
                    currentCharacter.atk += attackUp;
                    if (currentCharacter != teamCharacters.get(0)) {
                        currentCharacter.mana += manaUp;
                        currentCharacter.maxMana += manaUp;
                    }
                    currentCharacter.level++;
                    currentCharacter.exp -= 100;
                    if (currentCharacter.hp > 0.0F)
                        currentCharacter.hp = currentCharacter.maxHp;
                    currentCharacter.mana = currentCharacter.maxMana;
                    System.out.println(currentCharacter.name + " Has Leveled Up To Level: " + currentCharacter.name + "!\nThe following Stats Have Increased:");
                    System.out.println("Max Health Points Increased By: " + healthUp + "\tMax Attack Increased By: " + attackUp + "\t Max Mana Increased By: " + manaUp);
                    System.out.println();
                }
        }
        System.out.println();
    }

    public static boolean checkTeamHP(Character character) {
        for (int i = 0; i < teamCharacters.size(); i++) {
            if (((Character)teamCharacters.get(i)).hp > 0.0F)
                return true;
        }
        return false;
    }

    public static void skillsMenu(Character character) {
        System.out.println("Available Skills: ");
        System.out.println("Healius          | Restores 50 HP of an ally, costs 30 mana. Can only be used by a Priest.");
        System.out.println("Shield of Hope   | Restores 25 HP of all ally, costs 20 mana. Can only be used by a Tank.");
        System.out.print("Enter the name of the skill you wish to use: ");
        String name = sc.nextLine().toLowerCase();
        useSkill(character, name);
    }

    public static void useSkill(Character character, String name) {
        Enemy enemy = new Enemy();
        if (!characterSkills.containsKey(name)) {
            System.out.println(name + " Skill Doesn't Exist.");
            return;
        }
        if (name.equalsIgnoreCase("healius"))
            ((Character)teamCharacters.get(2)).usingSkill(enemy);
        if (name.equalsIgnoreCase("shield of hope"))
            ((Character)teamCharacters.get(3)).usingSkill(enemy);
    }

    public void usingSkill(Enemy enemy) {}

    public static void skillsMenu(Character character, Enemy enemy, int currentCharacter) {
        System.out.println();
        ((Character)teamCharacters.get(currentCharacter)).usingSkill(enemy);
    }
}
