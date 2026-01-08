package fr.uge.character;

import java.util.Objects;

/**
 * Stores the data for an enemy.
 */
public record EnemyData(String name, int HPMax, int minAttack, int maxAttack, int minDefense, int maxDefense, int xp) {
	public EnemyData{
		Objects.requireNonNull(name); 
		if(HPMax < 0 || minAttack < 0 || maxAttack < 0 || minDefense < 0 || maxDefense < 0 || xp < 0) {
			throw new IllegalArgumentException("Values must be >= 0"); 
		}
	}
}
