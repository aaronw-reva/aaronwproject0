package com.revature.DAOS;

import com.revature.models.Player;

import java.util.ArrayList;

public interface PlayerDAOInterface {

    Player insertPlayer(Player play);

    ArrayList<Player> getAllPlayers();

    ArrayList<Player> getPlayersByTeam(int teamID);

    int updatePlayerTeam(int player_id, int teamID);

    int deletePlayerByID(int player_id);

}
