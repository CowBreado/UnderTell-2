package gameCharacters;

import Enemies.Enemy;
import Skills.Skills;

public class Hero extends Character {
    public Hero(int hp, int maxHp, int atk, int mana, int maxMana, int exp, int level, String name, boolean isDefend, boolean deathDebuffed) {
        super(hp, maxHp, atk, mana, maxMana, exp, level, name, isDefend, deathDebuffed);
    }

    public void usingSkill(Enemy enemy) {
        System.out.println("SKILL: ");
        System.out.println("Blood Sacrifice  | Revives a fallen ally to their max HP. Costs 100 HEALTH of the Hero. Can only be used by a Hero Class at Level 5+\nUse Skill?");
        System.out.print("1 | Yes\n2 | No\nChoose Your Action: ");
        String name = sc.nextLine().toLowerCase();
        if (name.equalsIgnoreCase("1")) {
            bloodSacrifice(enemy);
            return;
        }
        System.out.println("Cancelled.");
    }

    void bloodSacrifice(Enemy enemy) {
        String skillName = "blood sacrifice";
        if (this.hp <= ((Skills)characterSkills.get(skillName)).hpCost) {
            System.out.println("Insufficient Health Points.");
            return;
        }
        if (this.level < 5) {
            System.out.println("Insufficient Character Level. Please Check The Required Level.");
            return;
        }
        if (this.hp <= 0.0F) {
            System.out.println("Failed To Use Skill, Character Is Currently Fallen.");
            return;
        }
        displayTeam();
        System.out.print("Selected: Blood Sacrifice | Please select the index of the character you wish to Ad Vitam Revocare: ");
        int target = sc.nextInt();
        sc.nextLine();
        if (target > enemy.enemies.size() || target < 0) {
            System.out.println("Invalid Target Index.");
            return;
        }
        if (((Character)teamCharacters.get(target)).hp > 0.0F) {
            System.out.println("Invalid Target. Target Must Be Deceased For The Skill To Activate.");
            return;
        }
        ((Character)teamCharacters.get(target)).hp = ((Character)teamCharacters.get(target)).maxHp;
        this.hp -= ((Skills)characterSkills.get(skillName)).hpCost;
        System.out.println(this.name + " Sacrificed 100 Health Points.");
        System.out.println(((Character)teamCharacters.get(target)).name + " was brought back from the Gates of Death.");
    }
}
