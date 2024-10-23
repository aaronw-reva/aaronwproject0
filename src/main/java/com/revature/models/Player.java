package com.revature.models;

public class Player {

    private int player_id;
    private String player_name;
    private Team team; //player objects contain a whole team object, NOT a foreign key
    private int player_teamid;

    public Player() {

    }

    public Player(int player_id, String player_name, Team team) {
        this.player_id = player_id;
        this.player_name = player_name;
        this.team = team;
    }

    public Player(int player_id, String player_name, int player_teamid) {
        this.player_id = player_id;
        this.player_name = player_name;
        this.player_teamid = player_teamid;
    }

    public Player(String player_name, int player_teamid) {
        this.player_name = player_name;
        this.player_teamid = player_teamid;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getPlayer_teamid() {
        return player_teamid;
    }

    public void setPlayer_teamid(int player_teamid) {
        this.player_teamid = player_teamid;
    }

    @Override
    public String toString() {
        return "Player{" +
                "player_id=" + player_id +
                ", player_name='" + player_name + '\'' +
                ", team=" + team +
                ", player_teamid=" + player_teamid +
                '}';
    }
}
