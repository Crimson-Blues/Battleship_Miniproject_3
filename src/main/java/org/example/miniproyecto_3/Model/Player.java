package org.example.miniproyecto_3.Model;

import java.io.Serializable;

public class Player implements Serializable {
    private String nickname;
    private int sunkShips;

    public Player(){
        this.nickname = "";
        sunkShips = 0;
    }

    public Player(String nickname){
        this.nickname = nickname;
        sunkShips = 0;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setSunkShips(int  sunkShips){
        this.sunkShips = sunkShips;
    }
    public void newSunkShip(){
        sunkShips++;
    }

    public int getSunkShips() {
        return sunkShips;
    }
}
