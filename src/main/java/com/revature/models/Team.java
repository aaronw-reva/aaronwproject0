package com.revature.models;

public class Team {
    private  int team_id;
    private String team_name;
    private int team_wins;

    //no args constructor, all args constructor, getter, setter, toString

    public Team() {
    }

    public Team(int team_id, String team_name, int team_wins) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_wins = team_wins;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getTeam_wins() {
        return team_wins;
    }

    public void setTeam_wins(int team_wins) {
        this.team_wins = team_wins;
    }

    @Override
    public String toString() {
        return "Team{" +
                "team_id=" + team_id +
                ", team_name='" + team_name + '\'' +
                ", team_wins=" + team_wins +
                '}';
    }
}
