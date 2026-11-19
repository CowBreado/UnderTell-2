package gameCharacters;

import Enemies.Enemy;
import Skills.Skills;

public class Priest extends Character {
    public Priest(int hp, int maxHp, int atk, int mana, int maxMana, int exp, int level, String name, boolean isDefend, boolean deathDebuffed) {
        super(hp, maxHp, atk, mana, maxMana, exp, level, "Priest", isDefend, deathDebuffed);
    }

    public void usingSkill(Enemy enemy) {
        System.out.println("SKILL: ");
        System.out.println("Healius          | Restores 50 HP of an ally, costs 30 mana. Can only be used by a Priest.\nUse Skill?");
        System.out.print("1 | Yes\n2 | No\nChoose Your Action: ");
        String name = sc.nextLine().toLowerCase();
        if (name.equalsIgnoreCase("1")) {
            healius();
            return;
        }
        System.out.println("Cancelled.");
    }

    void healius() {
        String skillName = "healius";
        if (this.mana <= ((Skills)characterSkills.get(skillName)).manaCost) {
            System.out.println("Insufficient Mana.");
            return;
        }
        if (this.hp <= 0.0F) {
            System.out.println("Failed To Use Skill, Character Is Currently Fallen.");
            return;
        }
        Character.displayTeam();
        System.out.print("Selected: Healius | Please select the index of the character you wish to heal: ");
        int target = sc.nextInt();
        sc.nextLine();
        if (target >= teamCharacters.size() || target < 0) {
            System.out.println("Invalid Target Index.");
            return;
        }
        ((Character)teamCharacters.get(target)).hp += ((Skills)characterSkills.get(skillName)).healVal;
        this.mana -= ((Skills)characterSkills.get(skillName)).manaCost;
        if (((Character)teamCharacters.get(target)).hp > ((Character)teamCharacters.get(target)).maxHp)
            ((Character)teamCharacters.get(target)).hp = ((Character)teamCharacters.get(target)).maxHp;
        System.out.println("You healed: " + ((Character)teamCharacters.get(target)).name + ", HP: " + ((Character)teamCharacters.get(target)).hp);
    }
}
