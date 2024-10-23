package com.revature;

import com.revature.DAOS.PlayerDAO;
import com.revature.DAOS.TeamDAO;
import com.revature.models.Player;
import com.revature.models.Team;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) {

        /* try with resources block
        * connection closes on its own */
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println("Connection successful!");
        } catch(SQLException e) {
            e.printStackTrace(); //tells error in red text
            System.out.println("Connection failed!");
        }

        //TESTING DAO METHODS:
        TeamDAO tDAO = new TeamDAO();
        PlayerDAO pDAO = new PlayerDAO();

        //testing getting role by id
        Team t = tDAO.getTeamByID(2);
        System.out.println(t);

        //test insert player (commented to not do it again)
        //Player p = new Player("asdf", 1);
        //System.out.println(pDAO.insertPlayer(p));

        //test update wins to 1
       // System.out.println(tDAO.updateTeamWins(1));

        //test get all players
        System.out.println(pDAO.getAllPlayers());
    }
}
