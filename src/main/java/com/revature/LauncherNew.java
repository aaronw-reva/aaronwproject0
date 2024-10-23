package com.revature;

import com.revature.controllers.PlayerController;
import com.revature.controllers.TeamController;
import io.javalin.Javalin;

public class LauncherNew {

    public static void main(String[] args) {
        var app = Javalin.create().start(7000);

        app.get("/", ctx -> ctx.result("Hello Javalin and postman"));

        PlayerController pc = new PlayerController();
        TeamController tc = new TeamController();

        app.get("/players", pc.getPlayerHandler);
        app.get("/players/{id}", pc.getPlayersByTeamHandler);
        app.get("/teams", tc.getTeamHandler);
        app.get("/teams/{id}", tc.getTeamByIdHandler);

        app.post("/players", pc.insertPlayerHandler);
        app.post("/quickteam", tc.insertTeamWithPlayers);

        app.patch("/players/{id}", pc.updatePlayerTeam);
        app.patch("/roundrobin", tc.singleRobinHandler);
        app.patch("/roundrobin/{num}", tc.roundRobinHandler);
        app.patch("/resetwins", tc.winsResetHandler);

        app.delete("/players/{id}", pc.deletePlayerHandler);
        app.delete("/teams/{id}", tc.deleteTeamHandler);

    }

}
