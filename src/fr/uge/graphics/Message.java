package fr.uge.graphics;

import java.time.Instant;

/**
 * Represents a message with text and the time when it should disappear.
 *
 * @param text the content of the message
 * @param endTime the time when the message ends
 */
public record Message(String text, Instant endTime) { }

