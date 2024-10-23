package com.revature.DAOS;

import com.revature.models.Player;
import com.revature.models.Team;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class PlayerDAO implements PlayerDAOInterface{
    @Override
    public Player insertPlayer(Player play) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO player_list (player_name, player_teamid) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, play.getPlayer_name());
            ps.setInt(2, play.getPlayer_teamid());

            ps.executeUpdate();

            return play;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not insert");
        }
        return null;
    }

    @Override
    public ArrayList<Player> getAllPlayers() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM player_list";
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            ArrayList<Player> players = new ArrayList<>();

            TeamDAO tDAO = new TeamDAO();

            while(rs.next()){
                //need new employee object for each row
                Player p = new Player(rs.getInt("player_id"), rs.getString("player_name"), rs.getInt("player_teamid"));
                Team team = tDAO.getTeamByID(rs.getInt("player_teamid"));
                p.setTeam(team);
                players.add(p);
            }

            return players;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not get all");
        }

        return null;
    }

    public ArrayList<Player> getPlayersByTeam(int teamID) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM player_list WHERE player_teamid = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, teamID);

            ResultSet rs = ps.executeQuery();

            ArrayList<Player> players = new ArrayList<>();

            TeamDAO tDAO = new TeamDAO();

            while(rs.next()){
                //need new employee object for each row
                Player p = new Player(rs.getInt("player_id"), rs.getString("player_name"), rs.getInt("player_teamid"));
                Team team = tDAO.getTeamByID(rs.getInt("player_teamid"));
                p.setTeam(team);
                players.add(p);
            }

            return players;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not get by id");
        }

        return null;
    }

    public int updatePlayerTeam(int player_id, int teamID) {
        try(Connection conn = ConnectionUtil.getConnection()){

            //SQL statement
            String sql = "UPDATE player_list SET player_teamid = ? WHERE player_id = ?";

            //Create a PreparedStatement to fill in the variables
            PreparedStatement ps = conn.prepareStatement(sql);

            //ps.set() to set the variables values
            ps.setInt(1, teamID);
            ps.setInt(2, player_id);

            //execute the update!
            ps.executeUpdate();

            //return the new salary
            return teamID;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("couldn't update team");
        }

        return 0;

    }

    public int deletePlayerByID(int player_id){
        try(Connection conn = ConnectionUtil.getConnection()){

            //SQL statement
            String sql = "DELETE FROM player_list WHERE player_id = ?";

            //Create a PreparedStatement to fill in the variables
            PreparedStatement ps = conn.prepareStatement(sql);

            //ps.set() to set the variables values
            ps.setInt(1, player_id);

            //execute the update!
            ps.executeUpdate();

            //return the new salary
            return player_id;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("couldn't update team");
        }

        return 0;
    }
}
