package me.thedreps.hypixeltweaks.runnable;

import me.thedreps.hypixeltweaks.reference.CurrentGame;
import me.thedreps.hypixeltweaks.utility.LogHelper;
import me.thedreps.hypixeltweaks.utility.PlayerStats;
import net.minecraft.entity.player.EntityPlayer;
import org.json.simple.JSONObject;

import java.util.UUID;

import static me.thedreps.hypixeltweaks.utility.HypixelAPI.*;

public class FetchStats implements Runnable {
    public void run() {

        EntityPlayer ep = CurrentGame.getNextPlayer();

        if(ep == null){
            return;
        }

        UUID uuid = ep.getUniqueID();

        LogHelper.info("Stat checking for player " + uuid.toString() + " started");

        PlayerStats playerStats = new PlayerStats();

        JSONObject playerJson = getPlayerJson(uuid);

        try{
            playerStats.name = getStringFromJson(getJsonFromJson(playerJson, "player"), "playername");
        }catch (NullPointerException e){
            LogHelper.info(ep.getName() + " IS A NICK");
            playerStats.isNicked = true;
        }

        playerStats.networkLevel = Math.floor(1 + (-3.5) + Math.sqrt(12.25 + 0.0008 * getDoubleFromJson(getJsonFromJson(playerJson, "player"), "networkExp")));

        playerStats.bedwarsFinalKills = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Bedwars"), "final_kills_bedwars");
        playerStats.bedwarsFinalDeaths = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Bedwars"), "final_deaths_bedwars");
        playerStats.bedwarsStars = getDoubleFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "achievements"), "bedwars_level");
        playerStats.bedwarsFkdr = playerStats.bedwarsFinalKills / playerStats.bedwarsFinalDeaths;
        playerStats.bedwarsIndex = playerStats.bedwarsStars * Math.pow(playerStats.bedwarsFkdr, 2);
        playerStats.bedwarsWins = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Bedwars"), "wins_bedwars");
        playerStats.bedwarsLosses = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Bedwars"), "losses_bedwars");
        playerStats.bedwarsWlr = playerStats.bedwarsWins / playerStats.bedwarsLosses;
        playerStats.bedwarsWinstreak = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Bedwars"), "winstreak");
        playerStats.bedwarsBedsBroken = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Bedwars"), "beds_broken_bedwars");
        playerStats.bedwarsBedsLost = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Bedwars"), "beds_lost_bedwars");
        playerStats.bedwarsBblr = playerStats.bedwarsBedsBroken / playerStats.bedwarsBedsLost;

        playerStats.skywarsStars = sw_xp_to_lvl(getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Skywars"), "skywars_experience"));
        playerStats.skywarsKills = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Skywars"), "kills");
        playerStats.skywarsDeaths = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Skywars"), "deaths");
        playerStats.skywarsWins = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Skywars"), "wins");
        playerStats.skywarsLosses = getDoubleFromJson(getJsonFromJson(getJsonFromJson(getJsonFromJson(playerJson, "player"), "stats"), "Skywars"), "losses");
        playerStats.skywarsKdr = playerStats.skywarsKills / playerStats.skywarsDeaths;
        playerStats.skywarsWlr = playerStats.skywarsWins / playerStats.skywarsLosses;


        LogHelper.info(playerStats.name + " network level " + playerStats.networkLevel);
        LogHelper.info(playerStats.name + " nicked " + playerStats.isNicked);
        LogHelper.info(playerStats.name + " bwFks " + playerStats.bedwarsFinalKills);
        LogHelper.info(playerStats.name + " bwFds " + playerStats.bedwarsFinalDeaths);
        LogHelper.info(playerStats.name + " bwStars " + playerStats.bedwarsStars);
        LogHelper.info(playerStats.name + " bwFkdr " + playerStats.bedwarsFkdr);
        LogHelper.info(playerStats.name + " bwIndex " + playerStats.bedwarsIndex);
        LogHelper.info(playerStats.name + " bwWins " + playerStats.bedwarsWins);
        LogHelper.info(playerStats.name + " bwLosses " + playerStats.bedwarsLosses);
        LogHelper.info(playerStats.name + " bwWlr " + playerStats.bedwarsWlr);
        LogHelper.info(playerStats.name + " bwWs " + playerStats.bedwarsWinstreak);
        LogHelper.info(playerStats.name + " bwBedBroken " + playerStats.bedwarsBedsBroken);
        LogHelper.info(playerStats.name + " bwBedLost " + playerStats.bedwarsBedsLost);
        LogHelper.info(playerStats.name + " bwBblr " + playerStats.bedwarsBblr);
        LogHelper.info(playerStats.name + " swStar " + playerStats.skywarsStars);
        LogHelper.info(playerStats.name + " swKills " + playerStats.skywarsKills);
        LogHelper.info(playerStats.name + " swDeaths " + playerStats.skywarsDeaths);
        LogHelper.info(playerStats.name + " swWins " + playerStats.skywarsWins);
        LogHelper.info(playerStats.name + " swLosses " + playerStats.skywarsLosses);
        LogHelper.info(playerStats.name + " swKdr " + playerStats.skywarsKdr);
        LogHelper.info(playerStats.name + " swWlr " + playerStats.skywarsWlr);

        CurrentGame.addPlayerStats(playerStats);

        LogHelper.info("Stats for player " + playerStats.name + " recorded");

    }


    // Solen from https://hypixel.net/threads/skywars-level-from-api.1912987/ User: https://hypixel.net/members/socuul.3683036/
    public static int sw_xp_to_lvl(double xp) {
        int[] xps = {0, 20, 70, 150, 250, 500, 1000, 2000, 3500, 6000, 10000, 15000};
        if (xp >= 15000) {
            return (int) (xp - 15000) / 10000 + 12;
        } else {
            for (int i = 0; i < xps.length; i++) {
                if (xp < xps[i]) {
                    return (int) (i + (xp - xps[i - 1]) / (xps[i] - xps[i - 1]));
                }
            }
        }
        return 0;
    }


}
