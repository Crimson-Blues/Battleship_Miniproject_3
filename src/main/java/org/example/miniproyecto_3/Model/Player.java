package org.example.miniproyecto_3.Model;

import java.io.Serializable;

/**
 * Represents a player in the game.
 * <p>
 * Each player has a nickname and a score representing the number of successful sunken ships.
 * This class implements {@link Serializable} to support saving player data.
 * </p>
 */
public class Player implements Serializable {
    /**
     * The nickname of the player.
     */
    private String nickname;
    /**
     * The score of the player.
     */

    private int score;

    /**
     * Constructs a new player with an empty nickname and a score of 0.
     */
    public Player(){
        this.nickname = "";
        score = 0;
    }

    /**
     * Constructs a new player with the specified nickname and a score of 0.
     *
     * @param nickname the nickname of the player
     */
    public Player(String nickname){
        this.nickname = nickname;
        score = 0;
    }

    /**
     * Sets the player's nickname.
     *
     * @param nickname the new nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the player's nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the player's score to the specified value.
     *
     * @param score the new score
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Increments the player's score by one.
     */
    public void plusOneScore(){
        this.score += 1;
    }

    /**
     * Returns the player's current score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }
}
