package fr.uge.character;

import java.util.Objects;

/**
 * Stores the data for an enemy.
 * @param name the name of the enemy, cannot be null
 * @param HPMax the enemy's max health, must be 0 or more
 * @param minAttack the enemy's minimum attack, must be 0 or more
 * @param maxAttack the enemy's maximum attack, must be 0 or more
 * @param minDefense the enemy's minimum defense, must be 0 or more
 * @param maxDefense the enemy's maximum defense, must be 0 or more
 * @param xp how much experience the player gets for defeating this enemy, must be 0 or more
 */
public record EnemyData(String name, int HPMax, int minAttack, int maxAttack, int minDefense, int maxDefense, int xp) {
	public EnemyData{
		Objects.requireNonNull(name); 
		if(HPMax < 0 || minAttack < 0 || maxAttack < 0 || minDefense < 0 || maxDefense < 0 || xp < 0) {
			throw new IllegalArgumentException("Values must be >= 0"); 
		}
	}
}
