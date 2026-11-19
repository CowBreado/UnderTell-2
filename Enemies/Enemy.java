package Enemies;

import Consumable.BoneMeal;
import Consumable.Consumable;
import Consumable.DaedalusTears;
import Consumable.RottenFlesh;
import Consumable.SlimeEssence;
import Game.Game;
import Inventory.Inventory;
import gameCharacters.Character;
import java.util.ArrayList;

public class Enemy {
    public ArrayList<Enemy> enemies = new ArrayList<>(4);

    int enemyCount;

    boolean[] buffedEnemy = new boolean[4];

    public String name;

    String idle;

    public int hp;

    int maxHp;

    int atk;

    int expDrop;

    public Enemy() {}

    Enemy(String name, int hp, int maxHp, int atk, int expDrop, String idle) {
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.atk = atk;
        this.expDrop = expDrop;
        this.idle = idle;
    }

    public void enemyDisplay() {
        System.out.println("------------------------------------------------Enemy Count: " + this.enemies.size() + "--------------------------------------------------------------------------------------------------");
        for (int i = 0; i < this.enemies.size(); i++) {
            System.out.printf("Index:\t " + i + "\tName:\t%-15s", new Object[] { ((Enemy)this.enemies.get(i)).name + "\t" });
            System.out.printf("\t| HP:\t%-6s", new Object[] { "" + ((Enemy)this.enemies.get(i)).hp + "\t| Max HP: " + ((Enemy)this.enemies.get(i)).hp });
            System.out.printf("\t| Attack:\t%-4s", new Object[] { "" + ((Enemy)this.enemies.get(i)).atk + " " });
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void enemyGenerate(int position) {
        if (position > 100) {
            this.enemyCount = 1;
        } else if (position >= 0) {
            this.enemyCount = (int)(Math.random() * 4.0D + 1.0D);
        }
        for (int i = 0; i < this.enemyCount; i++) {
            if (position >= 100) {
                Enemy lich = new Lich();
                this.enemies.add(lich);
            } else if (position >= 19) {
                Enemy daedalus = new Daedalus();
                this.enemies.add(daedalus);
            } else if (position >= 15 && position < 19) {
                Enemy ghoul = new Ghoul();
                this.enemies.add(ghoul);
            } else if (position >= 10) {
                Enemy skeleton = new Skeleton();
                this.enemies.add(skeleton);
            } else if (position < 10) {
                Enemy slime = new Slime();
                this.enemies.add(slime);
            }
        }
    }

    public void enemyTurn(Character character, int position) {
        if (this.enemies.size() > 0) {
            int actionValue = 0;
            for (int i = 0; i < this.enemies.size(); i++) {
                if (position >= 0 && position <= 20) {
                    actionValue = (int)(Math.random() * 9.0D);
                } else if (position >= 21) {
                    actionValue = (int)(Math.random() * 20.0D);
                }
                if (actionValue >= 15) {
                    ((Enemy)this.enemies.get(i)).death(character);
                } else if (actionValue >= 10) {
                    ((Enemy)this.enemies.get(i)).soulDevour(character);
                } else if (actionValue >= 6) {
                    enemyBuff(i);
                } else if (actionValue >= 3) {
                    enemyAttack(i, character);
                } else if (actionValue <= 2) {
                    enemyIdle(i);
                }
            }
        }
    }

    public void targetEnemy(int index, Character character, int i) {
        Game.space();
        if (index >= 0 && index < this.enemies.size()) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(((Character)Character.teamCharacters.get(i)).name + " Attacked Enemy Index #" + ((Character)Character.teamCharacters.get(i)).name + " " + index + ", and Dealt: " + ((Enemy)this.enemies.get(index)).name + " Damage!");
            ((Enemy)this.enemies.get(index)).hp -= ((Character)Character.teamCharacters.get(i)).atk;
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            if (((Enemy)this.enemies.get(index)).hp <= 0)
                enemyDefeated(index);
        } else {
            System.out.println("Invalid Target Index.");
        }
    }

    void enemyAttack(int i, Character character) {
        int target = (int)(Math.random() * Character.teamCharacters.size());
        if (((Character)Character.teamCharacters.get(target)).hp > 0.0F && !this.buffedEnemy[i])
            if (!((Character)Character.teamCharacters.get(target)).isDefend) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                ((Character)Character.teamCharacters.get(target)).hp -= ((Enemy)this.enemies.get(i)).atk;
                System.out.println("The enemy " + ((Enemy)this.enemies.get(i)).name + "#" + i + "  attacked: " + ((Character)Character.teamCharacters.get(target)).name + "! and dealt: " + ((Enemy)this.enemies.get(i)).atk + " damage!");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                return;
            }
        if (((Character)Character.teamCharacters.get(target)).hp > 0.0F && this.buffedEnemy[i] == true)
            if (!((Character)Character.teamCharacters.get(target)).isDefend) {
                ((Character)Character.teamCharacters.get(target)).hp = (float)(((Character)Character.teamCharacters.get(target)).hp - ((Enemy)this.enemies.get(i)).atk * 1.5D);
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Critical Hit!");
                System.out.println("The enemy " + ((Enemy)this.enemies.get(i)).name + "#" + i + " attacked: " + ((Character)Character.teamCharacters.get(target)).name + "! and dealt a heavy damage of: " + ((Enemy)this.enemies.get(i)).atk * 1.5D + " damage!");
                this.buffedEnemy[i] = false;
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                return;
            }
        if (((Character)Character.teamCharacters.get(target)).hp > 0.0F && !this.buffedEnemy[i])
            if (((Character)Character.teamCharacters.get(target)).isDefend == true) {
                float defendedAttack = ((Enemy)this.enemies.get(i)).atk - ((Enemy)this.enemies.get(i)).atk * 0.3F;
                ((Character)Character.teamCharacters.get(target)).hp -= defendedAttack;
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("The enemy " + ((Enemy)this.enemies.get(i)).name + "#" + i + " attacked: " + ((Character)Character.teamCharacters.get(target)).name + "! but was blocked! and dealt: " + defendedAttack + " damage!");
                ((Character)Character.teamCharacters.get(target)).isDefend = false;
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                return;
            }
        if (((Character)Character.teamCharacters.get(target)).hp > 0.0F && this.buffedEnemy[i] == true)
            if (((Character)Character.teamCharacters.get(target)).isDefend == true) {
                float defendedAttack = ((Enemy)this.enemies.get(i)).atk * 1.5F - ((Enemy)this.enemies.get(i)).atk * 0.3F;
                ((Character)Character.teamCharacters.get(target)).hp -= defendedAttack;
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Critical Hit!");
                System.out.println("The enemy " + ((Enemy)this.enemies.get(i)).name + "#" + i + " attacked: " + ((Character)Character.teamCharacters.get(target)).name + "! but was blocked! and dealt a heavy damage of: " + defendedAttack + " damage!");
                this.buffedEnemy[i] = false;
                ((Character)Character.teamCharacters.get(target)).isDefend = false;
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                return;
            }
        if (Character.checkTeamHP(character) == true) {
            enemyAttack(i, character);
        } else if (!Character.checkTeamHP(character)) {
            Character.displayTeam();
            Game.gameOver(character);
        }
    }

    void enemyBuff(int i) {
        if (!this.buffedEnemy[i]) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("The enemy " + ((Enemy)this.enemies.get(i)).name + "#" + i + " empowers itself! increasing their next attack");
            this.buffedEnemy[i] = true;
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        } else if (this.buffedEnemy[i] == true) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("The enemy " + ((Enemy)this.enemies.get(i)).name + "#" + i + " is ready to attack!");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    void enemyIdle(int i) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("The enemy " + ((Enemy)this.enemies.get(i)).name + "#" + i + ", " + ((Enemy)this.enemies.get(i)).idle);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void enemyWipedOut(Inventory inventory, int position, Character character) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        if (this.enemies.size() == 0) {
            System.out.println("You have successfully defeated the enemies!");
            enemyDrop(inventory, position, character);
            enemyExpDrop(position, character);
            Character.characterLevelUp(character);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            return;
        }
    }

    void enemyDrop(Inventory inventory, int position, Character character) {
        for (int i = 0; i < this.enemyCount; i++)
            enemyDropType(position, inventory);
    }

    void enemyExpDrop(int position, Character character) {
        int totalExp = 0;
        for (int i = 0; i < this.enemyCount; i++) {
            if (position >= 19) {
                Daedalus daedalus = new Daedalus();
                totalExp += daedalus.expDrop;
            } else if (position >= 15 && position < 19) {
                Ghoul ghoul = new Ghoul();
                totalExp += ghoul.expDrop;
            } else if (position >= 10) {
                Skeleton skeleton = new Skeleton();
                totalExp += skeleton.expDrop;
            } else if (position < 10) {
                Slime slime = new Slime();
                totalExp += slime.expDrop;
            }
        }
        ((Character)Character.teamCharacters.get(0)).exp += totalExp;
        ((Character)Character.teamCharacters.get(1)).exp += totalExp;
        ((Character)Character.teamCharacters.get(2)).exp += totalExp;
        ((Character)Character.teamCharacters.get(3)).exp += totalExp;
        System.out.printf("Name:\t%-15s Gained\t   EXP: " + totalExp + "\n", new Object[] { ((Character)Character.teamCharacters.get(0)).name });
        System.out.printf("Name:\t%-15s Gained\t   EXP: " + totalExp + "\n", new Object[] { ((Character)Character.teamCharacters.get(1)).name });
        System.out.printf("Name:\t%-15s Gained\t   EXP: " + totalExp + "\n", new Object[] { ((Character)Character.teamCharacters.get(2)).name });
        System.out.printf("Name:\t%-15s Gained\t   EXP: " + totalExp + "\n", new Object[] { ((Character)Character.teamCharacters.get(3)).name });
    }

    void enemyDefeated(int index) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("Enemy Index: " + index + "\t   Name:\t%-15s", new Object[] { ((Enemy)this.enemies.get(index)).name + "\tHP: " + ((Enemy)this.enemies.get(index)).name + "\t   Has Been Defeated!\n" });
        this.enemies.remove(index);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    void enemyDropType(int position, Inventory inventory) {
        if (position >= 19) {
            DaedalusTears daedalusTears = new DaedalusTears();
            int dropRate = (int)(Math.random() * 10.0D);
            if (dropRate >= 7) {
                System.out.println("Obtained: " + daedalusTears.name);
                inventory.addInventory("daedalus tears", (Consumable)daedalusTears);
            }
        } else if (position >= 15) {
            RottenFlesh rottenFlesh = new RottenFlesh();
            int dropRate = (int)(Math.random() * 10.0D);
            if (dropRate >= 7) {
                System.out.println("Obtained: " + rottenFlesh.name);
                inventory.addInventory("rotten flesh", (Consumable)rottenFlesh);
            }
        } else if (position >= 10) {
            BoneMeal boneMeal = new BoneMeal();
            int dropRate = (int)(Math.random() * 10.0D);
            if (dropRate >= 6) {
                System.out.println("Obtained: " + boneMeal.name);
                inventory.addInventory("bone meal", (Consumable)boneMeal);
            }
        } else if (position < 10) {
            SlimeEssence slimeEssence = new SlimeEssence();
            int dropRate = (int)(Math.random() * 10.0D);
            if (dropRate >= 6) {
                System.out.println("Obtained: " + slimeEssence.name);
                inventory.addInventory("slime essence", (Consumable)slimeEssence);
            }
        }
    }

    void soulDevour(Character character) {}

    void death(Character character) {}
}
