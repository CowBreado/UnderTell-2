package Game;

import Consumable.Consumable;
import Enemies.Enemy;
import Inventory.Inventory;
import Skills.Firada;
import Skills.Healius;
import Skills.HeroicSacrifice;
import Skills.KnightDuty;
import gameCharacters.Character;
import gameCharacters.Hero;
import gameCharacters.Mage;
import gameCharacters.Priest;
import gameCharacters.Tank;
import java.util.Scanner;

public class Game {
    public static void teamSetup(Character character) {
        System.out.println(" __   __  __    _  ______   _______  ______   _______  _______  ___      ___        _______  _     _  _______\n|  | |  ||  |  | ||      | |       ||    _ | |       ||       ||   |    |   |      |       || | _ | ||       |\n|  | |  ||   |_| ||  _    ||    ___||   | || |_     _||    ___||   |    |   |      |_     _|| || || ||   _   |\n|  |_|  ||       || | |   ||   |___ |   |_||   |   |  |   |___ |   |    |   |        |   |  |       ||  | |  |\n|       ||  _    || |_|   ||    ___||    __ |  |   |  |    ___||   |___ |   |___     |   |  |       ||  |_|  |\n|       || | |   ||       ||   |___ |   |  ||  |   |  |   |___ |       ||       |    |   |  |   _   ||       |\n|_______||_|  |__||______| |_______||___|  ||  |___|  |_______||_______||_______|    |___|  |__| |__||_______|");
        System.out.println("|---------------------------------A Simple Text-Based RPG Game-----------------------------------------------|");
        System.out.println("                                Enter The Name Of Your Character                            ");
        System.out.print("                                          ");
        String playerName = sc.nextLine();
        Hero player = new Hero(200, 200, 30, 0, 0, 0, 1, playerName, false, false);
        Mage mage = new Mage(120, 120, 20, 200, 200, 0, 1, "Mage", false, false);
        Priest priest = new Priest(100, 100, 20, 200, 200, 0, 1, "Priest", false, false);
        Tank tank = new Tank(300, 300, 25, 100, 100, 0, 1, "Tank", false, false);
        Character.addTeamCharacter((Character)player);
        Character.addTeamCharacter((Character)mage);
        Character.addTeamCharacter((Character)priest);
        Character.addTeamCharacter((Character)tank);
    }

    public static void intro() {
        System.out.println("Welcome To UNDERTELL 2!");
        System.out.println("CONTEXT: ");
        System.out.print("After Being Defeated By The Undead Lich. You Are Woken Up By A Group Of Lost Adventurers Like Yourself.\nPress Enter To Continue: ");
        sc.nextLine();
        System.out.print("You Already Know Firsthand The Dangers Of Venturing Such An Accursed Place And Do Not Plan On Staying Here\nPress Enter To Continue: ");
        sc.nextLine();
        System.out.print("But Then You Realized, That Under Such Situation, Your Best Bet Would Be To Conjure A Teleportation Potion\nPress Enter To Continue: ");
        sc.nextLine();
        System.out.print("This However, Requires You To Defeat A Very Challenging Creature Multiple Times\nPress Enter To Continue: ");
        sc.nextLine();
        System.out.print("May You Find Solace Wherever You Are.\nPress Enter To Begin: ");
        sc.nextLine();
    }

    public static void addCharacterSkill(Character character) {
        HeroicSacrifice hs = new HeroicSacrifice();
        Healius healius = new Healius();
        Firada firada = new Firada();
        KnightDuty soh = new KnightDuty();
        Character.characterSkills.put("blood sacrifice", hs);
        Character.characterSkills.put("healius", healius);
        Character.characterSkills.put("firada", firada);
        Character.characterSkills.put("shield of hope", soh);
    }

    public static String BREAK_LINE = "----------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public static String ENEMY_BREAK_LINE = "------------------------------------------------ENEMY ENCOUNTER-------------------------------------------------------------------------------------------------";

    static Scanner sc = new Scanner(System.in);

    public static int position = 0;

    public static void gameMenu(Inventory inventory, Character character) {
        boolean menuOpen = true;
        while (menuOpen) {
            System.out.println();
            System.out.println("Current position: Room #" + (position+1));
            System.out.println("1 | Move ");
            System.out.println("2 | Inventory");
            System.out.println("3 | Skills ");
            System.out.println("4 | View Team");
            System.out.println("5 | Rest ");
            System.out.println("6 | Finish Your Journey (Obtain 5 Daedalus Tears, All Team Members Must Be Level 5 or Above) ");
            System.out.println("7 | Give Up (Exit).");
            System.out.print("Enter Your Action: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    position = playerMovement(character, inventory);
                    continue;
                case "2":
                    inventory.displayInventory();
                    inventory.useInventory(character);
                    space();
                    continue;
                case "3":
                    Character.skillsMenu(character);
                    space();
                    continue;
                case "4":
                    Character.displayTeam();
                    continue;
                case "5":
                    rest(inventory, character, position);
                    continue;
                case "6":
                    endRequirement(character, inventory);
                    continue;
                case "7":
                    exit();
                    continue;
            }
            System.out.println("Invalid Input.");
        }
    }

    static void exit() {
        System.out.print("Are You Sure You Wish To Give Up?\n(1 | Yes || 2 | No): ");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            System.out.println("You Succumbed To The Labrynth...");
            System.out.println("BAD END.");
            System.exit(0);
        }
        System.out.println("Cancelled.");
    }

    static void rest(Inventory inventory, Character character, int position) {
        System.out.println("Proceed To Rest:\n1 | Yes\n2 | No");
        int eventRoll = (int)(Math.random() * 100.0D);
        System.out.print("Enter Your Choice: ");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            if (eventRoll >= 40) {
                System.out.println("As you begin to rest your restless bodies, you saw something within the shadows...\nREST FAILED!");
                fightMenu(inventory, character, position * 2);
                return;
            }
            System.out.println(((Character)Character.teamCharacters.get(0)).name + "'s Team Rested Successfully");
            restSuccess(character);
        }
        System.out.println("Returning to Main Menu...");
    }

    static void restSuccess(Character character) {
        int healthRestore = (int)(Math.random() * 15.0D + 6.0D);
        int manaRestore = (int)(Math.random() * 10.0D + 11.0D);
        System.out.println(BREAK_LINE);
        for (int i = 0; i < Character.teamCharacters.size(); i++) {
            if (((Character)Character.teamCharacters.get(i)).hp > 0.0F) {
                if (((Character)Character.teamCharacters.get(i)).hp + healthRestore <= ((Character)Character.teamCharacters.get(i)).maxHp) {
                    ((Character)Character.teamCharacters.get(i)).hp += healthRestore;
                    System.out.println(((Character)Character.teamCharacters.get(i)).name + " Restored: " + ((Character)Character.teamCharacters.get(i)).name + " Health Points!");
                } else {
                    ((Character)Character.teamCharacters.get(i)).hp = ((Character)Character.teamCharacters.get(i)).maxHp;
                    System.out.println(((Character)Character.teamCharacters.get(i)).name + " Restored: " + ((Character)Character.teamCharacters.get(i)).name + " Health Points!");
                }
                if (i != 0)
                    if (((Character)Character.teamCharacters.get(i)).mana + manaRestore <= ((Character)Character.teamCharacters.get(i)).maxMana) {
                        System.out.println(((Character)Character.teamCharacters.get(i)).name + " Restored: " + ((Character)Character.teamCharacters.get(i)).name + " Mana Points!");
                        ((Character)Character.teamCharacters.get(i)).mana += manaRestore;
                    } else {
                        ((Character)Character.teamCharacters.get(i)).mana = ((Character)Character.teamCharacters.get(i)).maxMana;
                        System.out.println(((Character)Character.teamCharacters.get(i)).name + " Restored: " + ((Character)Character.teamCharacters.get(i)).name + " Mana Points!");
                    }
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void space() {
        System.out.println();
        System.out.println();
        System.out.println();
    }

    static int playerMovement(Character character, Inventory inventory) {
        int[] mapArray = new int[20];
        String[] dialogue = {
                "Room #1 ", "Room #2", "Room #3", "Room #4", "Room #5", "Room #6", "Room #7", "Room #8", "Room #9", "Room #10",
                "Room #11", "Room #12", "Room #13", "Room #14", "Room #15", "Room #16", "Room #17", "Room #18", "Room #19", "Room #20" };
        System.out.println();
        boolean isMoving = true;
        while (isMoving) {
            space();
            System.out.println("| EXPLORATION MENU |");
            System.out.println(dialogue[position]);
            System.out.println("Current position: Room #" + (position + 1));
            System.out.println("1 | Go Forward.\n2 | Go Backward.\n3 | Return.");
            System.out.print("Enter Your Action: ");
            int movement = sc.nextInt();
            sc.nextLine();
            int eventRoll = (int)(Math.random() * 50.0D);
            if (movement == 1 && position + 1 < mapArray.length && eventRoll < 40) {
                position++;
                continue;
            }
            if (position + 1 == mapArray.length)
                System.out.println("Reached the end of the room.");
            if (movement == 2 && position - 1 >= 0 && eventRoll < 40) {
                position--;
                continue;
            }
            if (position - 1 < 0 && movement != 3 && eventRoll < 40) {
                System.out.println("Reached the end of the room");
                continue;
            }
            if (movement == 3 && eventRoll < 40) {
                System.out.println("Returning...");
                isMoving = false;
                continue;
            }
            if (eventRoll >= 40)
                fightMenu(inventory, character, position);
        }
        return position;
    }

    static void fightMenu(Inventory inventory, Character character, int position) {
        Enemy enemy = new Enemy();
        enemy.enemyGenerate(position);
        if (Character.checkTeamHP(character) == true) {
            while (enemy.enemies.size() > 0) {
                for (int i = 0; i < Character.teamCharacters.size(); i++) {
                    if (((Character)Character.teamCharacters.get(i)).hp > 0.0F) {
                        int index;
                        enemy.enemyDisplay();
                        space();
                        Character.displayTeam();
                        space();
                        System.out.println(((Character)Character.teamCharacters.get(i)).name + "'s current turn: ");
                        System.out.println("1 | Attack ");
                        System.out.println("2 | Defend ");
                        System.out.println("3 | Skills");
                        System.out.println("4 | Inventory ");
                        System.out.println("5 | Run ");
                        System.out.print("Enter Your Action: ");
                        String choice = sc.nextLine();
                        switch (choice) {
                            case "1":
                                System.out.print("Choose the index of the enemy to attack: ");
                                index = sc.nextInt();
                                sc.nextLine();
                                enemy.targetEnemy(index, character, i);
                                break;
                            case "2":
                                ((Character)Character.teamCharacters.get(i)).isDefend = true;
                                System.out.println(((Character)Character.teamCharacters.get(i)).name + " took a defensive stance.");
                                break;
                            case "3":
                                Character.skillsMenu(character, enemy, i);
                                break;
                            case "4":
                                inventory.displayInventory();
                                inventory.useInventory(character);
                                space();
                                break;
                            case "5":
                                if (flee(character))
                                    return;
                                enemy.enemyTurn(character, position);
                                space();
                                break;
                            default:
                                System.out.println("Invalid Input.");
                                break;
                        }
                        if (enemy.enemies.size() == 0) {
                            enemy.enemyWipedOut(inventory, position, character);
                            return;
                        }
                    }
                }
                enemy.enemyTurn(character, position);
            }
        } else if (!Character.checkTeamHP(character)) {
            gameOver(character);
        }
    }

    static void finalFight(Inventory inventory, Character character, int position) {
        Enemy enemy = new Enemy();
        enemy.enemyGenerate(position);
        if (Character.checkTeamHP(character) == true) {
            while (!enemy.enemies.isEmpty()) {
                for (int i = 0; i < Character.teamCharacters.size(); i++) {
                    int index;
                    enemy.enemyDisplay();
                    space();
                    Character.displayTeam();
                    space();
                    if (enemy.enemies.isEmpty()) {
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.println("You Dealt The Final Blow....");
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        break;
                    }
                    System.out.println(((Character)Character.teamCharacters.get(i)).name + "'s current turn: ");
                    System.out.println("1 | Attack ");
                    System.out.println("2 | Defend ");
                    System.out.println("3 | Skills");
                    System.out.println("4 | Inventory ");
                    System.out.print("Enter Your Action: ");
                    String choice = sc.nextLine();
                    switch (choice) {
                        case "1":
                            System.out.print("Choose the index of the enemy to attack: ");
                            index = sc.nextInt();
                            sc.nextLine();
                            enemy.targetEnemy(index, character, i);
                            break;
                        case "2":
                            ((Character)Character.teamCharacters.get(i)).isDefend = true;
                            System.out.println(((Character)Character.teamCharacters.get(i)).name + " took a defensive stance.");
                            break;
                        case "3":
                            Character.skillsMenu(character, enemy, i);
                            break;
                        case "4":
                            inventory.displayInventory();
                            inventory.useInventory(character);
                            space();
                            break;
                        default:
                            System.out.println("Invalid Input.");
                            break;
                    }
                }
                if (enemy.enemies.size() > 0)
                    enemy.enemyTurn(character, position);
                if (!Character.checkTeamHP(character))
                    gameOver(character);
            }
            if (enemy.enemies.size() <= 0)
                return;
        }
    }

    static boolean flee(Character character) {
        int runValue = (int)(Math.random() * 20.0D);
        if (runValue > 11) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(((Character)Character.teamCharacters.get(0)).name + "'s Team Failed To Escape!");
            System.out.println("The Enemy Gets An Extra Turn!");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            return false;
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(((Character)Character.teamCharacters.get(0)).name + "'s Team Escaped Successfully!");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return true;
    }

    static void endRequirement(Character character, Inventory inventory) {
        if (checkLevel(character) == true) {
            System.out.println("Level Check Success!");
            String name = "daedalus tears";
            if (Inventory.characterInventory.containsKey(name))
                if (((Consumable)Inventory.characterInventory.get(name)).count >= 5) {
                    System.out.println("Item Check Success!");
                    endSequence(character, inventory);
                }
        }
    }

    static boolean checkLevel(Character character) {
        boolean isLevelFive = true;
        for (int i = 0; i < Character.teamCharacters.size(); ) {
            if (((Character)Character.teamCharacters.get(i)).level >= 5) {
                i++;
                continue;
            }
            System.out.println("Team Level Failed To Meet Requirements (All Team Member Must Be Level 5 or Above)");
            return false;
        }
        System.out.println("Team Level Requirement Met.");
        return isLevelFive;
    }

    static void endSequence(Character character, Inventory inventory) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Congratulations... For Surviving The Labrynth Long Enough.");
        System.out.println("...");
        System.out.println("You Check Each Necessary Ingredient And Begin To Prepare A Makeshift Bottle For The Potion.");
        System.out.println("...");
        System.out.println("                                    THE END?                                    ");
        System.out.print("...\nPress Enter: ");
        sc.nextLine();
        System.out.print("You Turned Your Back To Finally Bid Goodbye To This Nightmare.\nPress Enter: ");
        sc.nextLine();
        int killedAlly = (int)(Math.random() * 3.0D + 1.0D);
        System.out.print("Oh...\nPress Enter: ");
        sc.nextLine();
        System.out.println(((Character)Character.teamCharacters.get(killedAlly)).name + "'s body was painted red with a scythe...\nPress Enter: ");
        sc.nextLine();
        System.out.println(((Character)Character.teamCharacters.get(killedAlly)).name + " has died and is permanently gone from the team.\nEnter...");
        Character.teamCharacters.remove(killedAlly);
        sc.nextLine();
        System.out.println("Undead Lich Appears Once More!");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        position = 10000;
        finalFight(inventory, character, 1000);
        System.out.println("...\nPress Enter: ");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("The Undead Lich....Has Fallen.\nPress Enter: ");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        sc.nextLine();
        System.out.println("You...Survived...");
        System.out.println("...");
        System.out.println("END OF GAME.");
        System.exit(0);
    }

    public static void gameOver(Character character) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(((Character)Character.teamCharacters.get(0)).name + "'s Team Have Been Defeated...\n\nGAME OVER");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.exit(0);
    }
}
