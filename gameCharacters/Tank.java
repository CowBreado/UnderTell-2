package gameCharacters;

import Enemies.Enemy;
import Skills.Skills;

public class Tank extends Character {
    public Tank(int hp, int maxHp, int atk, int mana, int maxMana, int exp, int level, String name, boolean isDefend, boolean deathDebuffed) {
        super(hp, maxHp, atk, mana, maxMana, exp, level, "Tank", isDefend, deathDebuffed);
    }

    public void usingSkill(Enemy enemy) {
        System.out.println("SKILL: ");
        System.out.println("Shield of Hope   | Restores 25 HP of all ally, costs 20 mana. Can only be used by a Tank.\nUse Skill?");
        System.out.print("1 | Yes\n2 | No\nChoose Your Action: ");
        String name = sc.nextLine().toLowerCase();
        if (name.equalsIgnoreCase("1")) {
            shieldOfHope();
            return;
        }
        System.out.println("Cancelled.");
    }

    void shieldOfHope() {
        String skillName = "shield of hope";
        if (this.mana < ((Skills)Character.characterSkills.get(skillName)).manaCost) {
            System.out.println("Insufficient Mana.");
            return;
        }
        if (this.hp <= 0.0F) {
            System.out.println("Failed To Use Skill, Character Is Currently Fallen.");
            return;
        }
        this.mana -= ((Skills)characterSkills.get(skillName)).manaCost;
        for (int i = 0; i < teamCharacters.size(); i++) {
            if (((Character)teamCharacters.get(i)).hp > 0.0F)
                ((Character)teamCharacters.get(i)).hp += ((Skills)characterSkills.get(skillName)).healVal;
            if (((Character)teamCharacters.get(i)).hp >= ((Character)teamCharacters.get(i)).maxHp)
                ((Character)teamCharacters.get(i)).hp = ((Character)teamCharacters.get(i)).maxHp;
            System.out.println("Healed: " + ((Character)teamCharacters.get(i)).name + ", HP: " + ((Character)teamCharacters.get(i)).hp);
        }
    }
}
