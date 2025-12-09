package fr.uge.character;
import fr.uge.application.Interactions;
import fr.uge.inventory.*;


public class Merchant implements InteractionCharacters {
  private final Items shop; 
  
  public Merchant(){
    this.shop = new Items(); 
    shop.randomItems(3);
  }
 
  public int goldCost(Item item) {
    int goldCost =  item.goldCost() / 2; 
    IO.println("Merchant : I can buy this item for : " + goldCost + " golds"); 
    return goldCost; 
  }
  
  public Items shop(){ return shop; }
  
  public void interactionBuy(Hero hero) {
  	hero.manageBackpack(shop, true);  
  }
  
  public void interactionSell(Hero hero, Interactions interaction) {
  	IO.println("Merchant : I can recup an item at half the price...\n"); 
  	IO.println("Do you want to sell an item ? (y/n)"); 
  	if(!interaction.requestYesNo())return; 
  	IO.println(hero.backpack().toString()); 
  	
  	IO.println("Choose an item what you want to sell (line and column)"); 
  	
  	int line = interaction.requestInt();
  	int column = interaction.requestInt(); 
  	
  	Item item = hero.backpack().choice(line, column);
  	if(item == null) return; 
  	if(item.type().equals("GOLD")) {
  		IO.println("You can't sell gold"); 
  		return; 
  	}
  	if(hero.sellItem(this, item))IO.println("You sold " + item.name()); 
  	IO.println(hero.backpack()); 
  	
  }
  
  @Override
  public void interaction(Hero hero, Interactions interaction) {
  	while(true) {
  		IO.println("Hello, I am the merchant !"); 
  		IO.println("### SHOP ###"); 
  		IO.println(shop.toString()); 
  		IO.println("What do you want to do ? "); 
  		IO.println("1) Buy an item in my shop"); 
  		IO.println("2) Sell an item"); 
  		IO.println("3) Go back"); 
  		int choice = interaction.requestInt(); 
			switch(choice) {
				case 1 -> interactionBuy(hero);

				case 2 -> interactionSell(hero, interaction); 
				case 3 -> {
					IO.println("Go back"); 
					return; 
				}
				default -> IO.println("This choice doesn't exist"); 
			}
  	}
  }
}

