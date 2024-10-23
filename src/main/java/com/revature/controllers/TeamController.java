package com.revature.controllers;

import com.revature.DAOS.TeamDAO;
import com.revature.models.Team;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class TeamController {

    TeamDAO tDAO = new TeamDAO();

    public Handler getTeamByIdHandler = ctx -> {
        int team_id = Integer.parseInt(ctx.pathParam("id"));
        Team team = tDAO.getTeamByID(team_id);

        if(team_id <= 0){
            ctx.result("ID must be greater than zero");
            ctx.status(400); //Bad Request
        } else if (team == null){
            ctx.result("team " + team_id + " not found");
            ctx.status(404);
        } else {
            ctx.json(team);
            ctx.status(200); //200 - OK
        }
    };

    public Handler getTeamHandler = ctx -> {
        ArrayList<Team> teams = tDAO.getAllTeams();

        if(teams == null){
            ctx.result("no teams found");
            ctx.status(404);
        } else {
            ctx.json(teams);
            ctx.status(200); //200 - OK
        }
    };

    public Handler insertTeamWithPlayers = ctx -> {
        String responseString = ctx.body();
        String[] afterSplit = responseString.split("[,\\.\\s]");
        tDAO.insertTeam(afterSplit[0],afterSplit[1],afterSplit[2],afterSplit[3]);
    };

    public Handler singleRobinHandler = ctx -> {
        int timesRun = tDAO.roundRobin(1);
        if(timesRun == 0){
            ctx.result("could not round robin!");
            ctx.status(404); //not found
        } else {
            ctx.result("times run: " + timesRun);
            ctx.status(200);
        }
    };

    public Handler roundRobinHandler = ctx -> {
        int timesToRun = Integer.parseInt(ctx.pathParam("num"));
        int timesRun = tDAO.roundRobin(timesToRun);
        if(timesRun == 0){
            ctx.result("could not round robin!");
            ctx.status(404); //not found
        } else {
            ctx.result("times run: " + timesRun);
            ctx.status(200);
        }
    };

    public Handler winsResetHandler = ctx -> {
        int winsReset = tDAO.winsReset();
        if(winsReset == 0){
            ctx.result("could not reset any wins");
            ctx.status(404); //not found
        } else {
            ctx.result("teams reset: " + winsReset);
            ctx.status(200); //ok
        }
    };

    public Handler deleteTeamHandler = ctx -> {
        int team_id = Integer.parseInt(ctx.pathParam("id"));
        int deletedTeam = tDAO.deleteTeamByID(team_id);

        if(team_id <= 0){
            ctx.result("ID must be greater than zero");
            ctx.status(400); //Bad Request
        } else if (deletedTeam == 0){
            ctx.result("team " + team_id + " not found");
            ctx.status(404);
        } else {
            ctx.result("team id deleted: " + deletedTeam);
            ctx.status(200);
        }
    };
}
