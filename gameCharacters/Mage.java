package gameCharacters;

import Enemies.Enemy;
import Skills.Skills;

public class Mage extends Character {
    public Mage(int hp, int maxHp, int atk, int mana, int maxMana, int exp, int level, String name, boolean isDefend, boolean deathDebuffed) {
        super(hp, maxHp, atk, mana, maxMana, exp, level, "Mage", isDefend, deathDebuffed);
    }

    public void usingSkill(Enemy enemy) {
        System.out.println("SKILL: ");
        System.out.println("Firada           | Casts a strong whirlwind of flames, dealing 35 damage to all enemies. Costs 40 mana. Can only be used by a Mage.\nUse Skill?");
        System.out.print("1 | Yes\n2 | No\nChoose Your Action: ");
        String name = sc.nextLine().toLowerCase();
        if (name.equalsIgnoreCase("1")) {
            firada(enemy);
            return;
        }
        System.out.println("Cancelled.");
    }

    void firada(Enemy enemy) {
        String skillName = "firada";
        if (this.mana < ((Skills)characterSkills.get(skillName)).manaCost) {
            System.out.println("Insufficient Mana.");
            return;
        }
        for (int i = 0; i < enemy.enemies.size(); i++) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            ((Enemy)enemy.enemies.get(i)).hp -= ((Skills)characterSkills.get("firada")).damage;
            System.out.println("Dealt 35 Damage to: " + ((Enemy)enemy.enemies.get(i)).name + "#" + i);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        this.mana -= ((Skills)characterSkills.get(skillName)).manaCost;
        for (int index = 0; index < enemy.enemies.size(); index++) {
            if (((Enemy)enemy.enemies.get(index)).hp <= 0) {
                System.out.printf("Enemy Index: " + index + "\t   Name:\t%-15s", new Object[] { ((Enemy)enemy.enemies.get(index)).name + "\tHP: " + ((Enemy)enemy.enemies.get(index)).name + "\t   Has Been Defeated!\n" });
                enemy.enemies.remove(enemy.enemies.get(index));
                index = 0;
            }
            if (enemy.enemies.size() == 1 &&
                    ((Enemy)enemy.enemies.get(index)).hp <= 0) {
                System.out.printf("Enemy Index: " + index + "\t   Name:\t%-15s", new Object[] { ((Enemy)enemy.enemies.get(index)).name + "\tHP: " + ((Enemy)enemy.enemies.get(index)).name + "\t   Has Been Defeated!\n" });
                enemy.enemies.remove(index);
                index = 0;
            }
        }
    }
}
