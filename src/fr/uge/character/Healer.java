package fr.uge.character;
 
/**
 * Saves the data for a healer service.
 * @param price the price of the service
 * @param hp the amount of HP restored by the service
 */
public record Healer(int price, int hp) {}