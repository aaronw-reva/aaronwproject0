package com.revature.DAOS;

import com.revature.models.Team;

//plan methods for teams

public interface TeamDAOInterface {

    //method for get team by id
    Team getTeamByID(int id);

    //method to add team with three players
    Team insertTeam(String team, String player1, String player2, String player3);

    //method for update team wins
    int updateTeamWins(int id, int newWins);

    int roundRobin(int runTimes);

    int winsReset();
}
