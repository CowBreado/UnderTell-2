package Enemies;

import Game.Game;
import gameCharacters.Character;

public class Lich extends Enemy {
    Lich() {
        super("Undead Lich", 2000, 2000, 60, 0, "Remains Idle. Your Imminent Death Approaches...");
    }

    void soulDevour(Character character) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        int target = (int)(Math.random() * Character.teamCharacters.size());
        if (!Character.checkTeamHP(character))
            Game.gameOver(character);
        if (((Character)Character.teamCharacters.get(target)).hp > 0.0F) {
            if (!Character.checkTeamHP(character)) {
                System.out.println(this.name + " Used Soul Devour But Missed!");
                return;
            }
            System.out.println(this.name + " Used Soul Devour And Dealt: " + this.name);
            ((Character)Character.teamCharacters.get(target)).hp = (float)(((Character)Character.teamCharacters.get(target)).hp - ((Character)Character.teamCharacters.get(target)).hp * 0.2D);
        } else {
            System.out.println(this.name + " Used Soul Devour But Missed!");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            return;
        }
    }

    void death(Character character) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(this.name + " Casts Death.");
        int fateRoll = (int)(Math.random() * 10.0D);
        if (fateRoll >= 8) {
            int target = (int)(Math.random() * Character.teamCharacters.size());
            if (((Character)Character.teamCharacters.get(target)).hp <= 0.0F)
                if (Character.checkTeamHP(character) == true)
                    System.out.println("Failed To Cast Death!");
            if (!((Character)Character.teamCharacters.get(target)).deathDebuffed) {
                System.out.println(((Character)Character.teamCharacters.get(target)).name + " Is Now Marked By Death.");
                ((Character)Character.teamCharacters.get(target)).deathDebuffed = true;
            } else if (((Character)Character.teamCharacters.get(target)).deathDebuffed == true) {
                System.out.println(((Character)Character.teamCharacters.get(target)).name + " Falls To The Ground, Lifeless.");
                ((Character)Character.teamCharacters.get(target)).hp = 0.0F;
                Character.checkTeamHP(character);
            }
        } else {
            System.out.println(this.name + " Missed Its Spell.");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
