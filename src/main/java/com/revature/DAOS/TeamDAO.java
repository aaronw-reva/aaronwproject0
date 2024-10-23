package com.revature.DAOS;

import com.revature.models.Player;
import com.revature.models.Team;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class TeamDAO implements TeamDAOInterface{

    public ArrayList<Team> getAllTeams() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM team_list ORDER BY team_wins DESC";
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            ArrayList<Team> teams = new ArrayList<>();

           // TeamDAO tDAO = new TeamDAO();

            while(rs.next()){
                //need new employee object for each row
                Team t = new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getInt("team_wins"));
                //Team team = tDAO.getTeamByID(rs.getInt("player_teamid"));
                //p.setTeam(team);
                teams.add(t);
            }

            return teams;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not get all");
        }

        return null;
    }

    @Override
    public Team getTeamByID(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM team_list WHERE team_id = ?"; //QUESTION MARK REQUIRED
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); //the 1 means change the first question mark in string
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Team team = new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getInt("team_wins"));
                return team;
            }
        } catch (SQLException e){
            e.printStackTrace(); //what went wrong
            System.out.println("Could not get team by ID");
        }
        return null;
    }

    @Override
    public Team insertTeam(String team, String player1, String player2, String player3){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO team_list (team_name, team_wins) VALUES (?, ?)";
            String sql1 = "INSERT INTO player_list (player_name, player_teamid) VALUES (?, ?)";
            String sql2 = "INSERT INTO player_list (player_name, player_teamid) VALUES (?, ?)";
            String sql3 = "INSERT INTO player_list (player_name, player_teamid) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, team);
            ps.setInt(2, 0);
            ps.executeUpdate();

            //get id from database
            String sqlselect = "SELECT * FROM team_list WHERE team_name = ?";
            PreparedStatement psselect = conn.prepareStatement(sqlselect);
            psselect.setString(1,team); //the 1 means change the first question mark in string
            ResultSet rs = psselect.executeQuery();
            rs.next();
            int newID = rs.getInt("team_id");

            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, player1);
            ps1.setInt(2, newID);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, player2);
            ps2.setInt(2, newID);
            ps2.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setString(1, player3);
            ps3.setInt(2, newID);
            ps3.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not insert");
        }
        return null;
    }

    @Override
    public int updateTeamWins(int id, int newWins) {

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE team_list SET team_wins = ? WHERE team_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, newWins);
            ps.setInt(2, id);

            ps.executeUpdate();

            return newWins;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not update wins");
        }

        return 0;
    }

    public int updateTeamAddWin(int id) {

        Team testTeam = getTeamByID(id);
        int currentWins = testTeam.getTeam_wins();
        int newWins = currentWins + 1;

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE team_list SET team_wins = ? WHERE team_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, newWins);
            ps.setInt(2, id);

            ps.executeUpdate();

            return newWins;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not update wins");
        }

        return 0;
    }

    public int roundRobin(int runTimes) {
        Random rand = new Random();

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT team_id, team_wins FROM team_list";
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            ArrayList<Integer> teamIDs = new ArrayList<>();
            ArrayList<Integer> newTeamWins = new ArrayList<>();

            while(rs.next()){
                teamIDs.add(rs.getInt("team_id"));
                newTeamWins.add(rs.getInt("team_wins"));
            }

            for (int currentPlays = 0; currentPlays<runTimes;currentPlays++){
                for(int i = 0; i<teamIDs.size(); i++){
                    for(int j = i + 1; j<teamIDs.size(); j++){
                        System.out.println("team " + teamIDs.get(i) + " vs team " + teamIDs.get(j));
                        if(rand.nextDouble()<.5){
                            int currentWins = newTeamWins.get(i) + 1;
                            //currentWins += 1;
                            newTeamWins.set(i, currentWins);
                            //updateTeamAddWin(teamIDs.get(i));
                            System.out.println("team id " + teamIDs.get(i) + " wins");
                        } else {
                            int currentWins = newTeamWins.get(j) + 1;
                            //currentWins += 1;
                            newTeamWins.set(j, currentWins);
                            //updateTeamAddWin(teamIDs.get(j));
                            System.out.println("team id " + teamIDs.get(j) + " wins");
                        }
                    }
                }
            }

            for(int i = 0; i<teamIDs.size(); i++){
                updateTeamWins(teamIDs.get(i),newTeamWins.get(i));
            }

            return runTimes;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not round robin");
        }

        return 0;
    }

    public int winsReset() {
        int teamsReset = 0;
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT team_id FROM team_list";
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            ArrayList<Integer> teamIDs = new ArrayList<>();

            while(rs.next()){
                teamIDs.add(rs.getInt("team_id"));
            }

            for(int i = 0; i<teamIDs.size(); i++){
                updateTeamWins(teamIDs.get(i), 0);
                teamsReset++;
            }


            return teamsReset;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not reset wins");
        }

        return 0;
    }

    public int deleteTeamByID(int team_id){
        try(Connection conn = ConnectionUtil.getConnection()){

            String sql = "DELETE FROM team_list WHERE team_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, team_id);
            ps.executeUpdate();

            return team_id;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("couldn't delete team");
        }

        return 0;
    }
}
