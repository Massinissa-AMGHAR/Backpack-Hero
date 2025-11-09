package fr.uge.character;
import java.util.Objects;
import fr.uge.inventory.*;


public class Hero implements Fighter {
	private final String name;
	private int HP;
	private int HPMAX;
	private int protection;
	private int energy;
	private int mana;
	private int gold;
	private int level;
	private final Backpack backpack;
	
	public Hero(String name, int HP, int HPMAX, int protection, int energy, int mana, int gold, int level, Backpack backpack) {
		Objects.requireNonNull(name, "name can't be null");
		Objects.requireNonNull(backpack, "backpack can't be null");
        if (HP < 0 || HPMAX < 0 || protection < 0 || energy < 0 || mana < 0 || gold < 0 || level < 0) throw new IllegalArgumentException("Integer must be >= 0");
        this.name = name;
        this.HP = HP;
        this.HPMAX = HPMAX;
        this.protection = protection;
        this.energy = energy;
        this.mana = mana;
        this.gold = gold;
        this.level = level;
        this.backpack = backpack;
	}
	
	public String name() { return name; }
	public int HP() { return HP; } 
	public int HMPAX() { return HPMAX; }
	public int protection() { return protection; }
	public int energy() { return energy; }
	public int mana() { return mana; }
	public int gold() { return gold; }
	public int level() { return level; }
	public Backpack backpack() { return backpack; }
	
	public void attack(Fighter oppenent, Item item) {
		if (!(oppenent instanceof Ennemy)) throw new IllegalArgumentException("Hero can only attack Ennemy");
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println(name + " doesn't have enough resources to use " + item.name());
			return;
		}
		Ennemy ennemy = (Ennemy) oppenent;
		int damage = Math.max(item.damage() - ennemy.protection(), 0);
		ennemy.receiveDamage(damage);
		
	}
	
	public void receiveDamage(int damage) {
		HP = Math.max(HP - damage, 0);
	}
	
	public void defense(Item item) {
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println(name + " doesn't have enough resources to use " + item.name());
			return;
		}
		protection += item.defense();
	}
	
}
