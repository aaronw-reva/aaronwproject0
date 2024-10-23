package com.revature.controllers;

//JSON comes in and gets translated to java, and other way

import com.revature.DAOS.PlayerDAO;
import com.revature.models.Player;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class PlayerController {

    PlayerDAO pDAO = new PlayerDAO();

    public Handler getPlayerHandler = ctx -> {
        ArrayList<Player> players = pDAO.getAllPlayers();
        if(players == null){
            ctx.result("no players found");
            ctx.status(404); //not found
        } else {
            ctx.json(players);
            ctx.status(200); //200 - OK
        }
    };

    public Handler getPlayersByTeamHandler = ctx -> {
        int team_id = Integer.parseInt(ctx.pathParam("id"));
        ArrayList<Player> players = pDAO.getPlayersByTeam(team_id);
        if(players.isEmpty()){
            ctx.result("no players found");
            ctx.status(404); //not found
        } else {
            ctx.json(players);
            ctx.status(200); //200 - OK
        }
    };

    public Handler insertPlayerHandler = ctx -> {
        Player newPlayer = ctx.bodyAsClass(Player.class);

        if(newPlayer.getPlayer_name() == null || newPlayer.getPlayer_name().isBlank()) {
            ctx.result("name is required");
            ctx.status(400); //bad request
        } else {
            Player insertedPlayer = pDAO.insertPlayer(newPlayer);
            ctx.status(201); //created
            ctx.json(insertedPlayer);
        }

    };

    public Handler updatePlayerTeam = ctx -> {
        int player_id = Integer.parseInt(ctx.pathParam("id"));
        int teamID = Integer.parseInt(ctx.body());

        int newTeamID = pDAO.updatePlayerTeam(player_id,teamID);
        if(newTeamID == 0){
            ctx.result("could not update");
            ctx.status(404);
        } else {
            ctx.result("Player ID: " + player_id + " moved to team " + newTeamID);
            ctx.status(200);
        }
    };

    public Handler deletePlayerHandler = ctx -> {
        int player_id = Integer.parseInt(ctx.pathParam("id"));

        int deletedPlayer = pDAO.deletePlayerByID(player_id);
        ctx.result("Player ID: " + player_id + " removed");
        ctx.status(200);

        if(player_id <= 0){
            ctx.result("ID must be greater than zero");
            ctx.status(400); //Bad Request
        } else if (deletedPlayer == 0){
            ctx.result("player " + player_id + " not found");
            ctx.status(404);
        } else {
            ctx.result("player id deleted: " + deletedPlayer);
            ctx.status(200);
        }
    };
}
