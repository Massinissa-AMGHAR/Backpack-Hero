package fr.uge.character;
import java.util.Objects;
import fr.uge.application.*;
import fr.uge.inventory.*;
import java.util.List;


public class Hero implements Fighter {
  private final String name;
  private int HP;
  private int HPMAX;
  private int protection;
  private int energy;
  private int mana;
  private int manaMax; 
  private int gold;
  private int xp; 
  private int level;
  private final Backpack backpack;
  
  public Hero(String name, int HP, int HPMAX, int protection, int energy, int manaMax, int mana, int gold, int xp,  int level, Backpack backpack) {
    Objects.requireNonNull(name, "name can't be null");
    Objects.requireNonNull(backpack, "backpack can't be null");
    if (HP < 0 || HPMAX < 0 || protection < 0 || energy < 0 || mana < 0 || manaMax < 0 || xp < 0 || gold < 0 || level < 0) throw new IllegalArgumentException("Integer must be >= 0");
      this.name = name;
      this.HP = HP;
      this.HPMAX = HPMAX;
      this.protection = protection;
      this.energy = energy;
      this.mana = mana;
      this.manaMax = manaMax;  
      this.gold = gold;
      this.xp = xp; 
      this.level = level;
      this.backpack = backpack;
  }
	
  public String name() { return name; }
  public int HP() { return HP; } 
  public int HPMAX() { return HPMAX; }
  public int protection() { return protection; }
  public int energy() { return energy; }
  public int mana() { return mana; }
  public int manaMax() { return manaMax; }
  public int gold() { return gold; }
  public int level() { return level; }
  public int xp() {return xp; }
  public Backpack backpack() { return backpack; }
	
	
	public int attack(Fighter opponent, Item item) {
		if (!(opponent instanceof Enemy)) throw new IllegalArgumentException("Hero can only attack Ennemy");
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println(name + " doesn't have enough resources to use " + item.name());
			return 0;
		}
		Enemy ennemy = (Enemy) opponent;
		int totalDamage = 0;
		int attackDamage = item.damage();
		
		int protection = ennemy.protection();
		int damageToProtection = Math.min(attackDamage, protection);
	    
	  ennemy.receiveProtectionDamage(damageToProtection); 
	    
		attackDamage -= damageToProtection;
		totalDamage += damageToProtection;
		
		if (attackDamage > 0) {
			ennemy.receiveDamage(attackDamage);
			totalDamage += attackDamage;
		}
		energy -= item.energyCost(); 
		mana -= item.manaCost();
		
		return totalDamage;
	}

	
  public void receiveDamage(int damage) {
    HP = Math.max(HP - damage, 0);
  }
	

	public void receiveProtectionDamage(int damage) {
	    protection = Math.max(protection - damage, 0);
	}
	
	public void resetProtection() {
		protection = 0;
	}
	
	public int defense(Item item) {
		if (mana < item.manaCost() || energy < item.energyCost()) {
			IO.println(name + " doesn't have enough resources to use " + item.name());
			return 0;
		}
		protection += item.defense();
		
		energy -= item.energyCost(); 
		mana -= item.manaCost(); 
		return protection;
	}
	
	public int manaBonus(Item item) {
	  if (mana < item.manaCost() || energy < item.energyCost()) {
	    IO.println(name + " doesn't have enough resources to use " + item.name());
		return 0;
	  }
	  mana += item.manaGained(); 
	  
	  energy -= item.energyCost(); 
	  mana -= item.manaCost();  
	  return mana;
	}
	
  public void winXp(Enemy ennemy) {
    Objects.requireNonNull(ennemy, "Ennemy cannot be null");
    this.xp += ennemy.xp();
  }
  
  public void winGold(int gold) {
  	this.gold += gold; 
  	IO.println(name + " wins " + gold + " gold");
  }
	
  public void resetEnergy() {
  	energy = 3; 
  }
	
  public void resetMana() {
    mana = manaMax; 
  }
  
  public boolean buy(int cost) {
    if(gold < cost) {
    	IO.println("You don't have enough money !"); 
    	return false; 
    }
    gold -= cost; 
    return true; 
  }

	public void sellItem(Merchant merchant, Item item) {
		Objects.requireNonNull(merchant, "Merchant cannot be null");
	  Objects.requireNonNull(item, "Item cannot be null");
	  this.backpack.delete(item);
	  winGold(merchant.goldCost(item)); 
	}

	public void treat(Healer healer) {
		Objects.requireNonNull(healer, "Angel cannot be null");
	  if (buy(healer.price())) {
	  	if(HP < HPMAX) HP += healer.hp();
	  }
	}
	
	private void choiceRotation(Item item, Interactions interaction) {
		IO.println(backpack.toString()); 
		IO.println("Do you want to make a rotation for your item ? (y/n)"); 
		if(interaction.requestYesNo()) {
			item.rotate();
			IO.println(item.toString());
		}
	}
	
	private void choiceDelItem(Interactions interaction) {
		IO.println("Do you want to delete an item in your backpack ? (y/n)"); 
		if(interaction.requestYesNo()) {
			IO.println("Choose an item : (line/column)"); 
			int line = interaction.requestInt(); 
			int column = interaction.requestInt(); 
			var item1 = backpack.choice(line, column); 
			backpack.delete(item1); 
			IO.print(backpack.toString()); 
		}
	}
	
	private void choicePlaceItem(Item item, Interactions interaction, List<Item> listItems, int index) {
		while (true) {
			IO.println("Choose a place for your item : (line/column)"); 
			int line = interaction.requestInt(); 
			int column = interaction.requestInt(); 
			int availableWidth = backpack.lengthLine(line, column);
			int availableHeight = backpack.lengthColumn(line, column);
			if (item.width() <= availableWidth && item.height() <= availableHeight) {
				backpack.add(item, line, column);
				listItems.remove(index); 
				IO.println(backpack.toString()); 
				break;
			} else IO.println("Item doesn't fit at this position. Please try again.");
		}

	}

	public void manageBackpack(Items items, Interactions interaction, boolean isShop) {
		var listItems = items.getItems(); 
		while(listItems.size() > 0) {
			IO.println(items.toString()); 
			IO.println("\nDo you want to add an item in your backpack ? (y/n) "); 
			if(!interaction.requestYesNo())return; 
			IO.println("Choose an item : (line)"); 
			int index = interaction.requestInt(); 
			if(index < 0 || index >= listItems.size()) {
				throw new IllegalArgumentException("Invalid index"); 
			}
			var item = listItems.get(index); 
			if(isShop) {
				if(!buy(item.goldCost())) return; 
				IO.println(this.toString()); 
			}
			IO.println(backpack.toString()); 
			choiceRotation(item, interaction);
			choiceDelItem(interaction);
			choicePlaceItem(item, interaction, listItems, index);
		}
	}
	
	@Override
	public String toString() {
		return this.name() + ": " + this.HP() + "/" + this.HPMAX() + " | Mana " + this.mana + "/" + this.manaMax() + " | Protection: " + this.protection() + " | Energy: " + this.energy() + " | " +  this.gold() + " gold\n";
	}

	
}
