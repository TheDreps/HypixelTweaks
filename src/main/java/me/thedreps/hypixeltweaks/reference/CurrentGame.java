package me.thedreps.hypixeltweaks.reference;

import me.thedreps.hypixeltweaks.runnable.FetchStats;
import me.thedreps.hypixeltweaks.utility.LogHelper;
import me.thedreps.hypixeltweaks.utility.PlayerStats;
import me.thedreps.hypixeltweaks.utility.Utils;
import net.minecraft.entity.player.EntityPlayer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrentGame {

    private static final ExecutorService service = Executors.newFixedThreadPool(50);

    private static ConcurrentHashMap<String, PlayerStats> playersStats = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<EntityPlayer, Boolean> playersInGame = new ConcurrentHashMap<>();


    public static void addPlayerStats(PlayerStats ps){
        playersStats.put(ps.name, ps);
    }
    public static void addPlayerToGame(EntityPlayer player){
        LogHelper.info("Adding " + player.getName() + " to game");
        playersInGame.put(player, false);
        service.execute(new FetchStats());
    }
    public static void removePlayerFromGame(String name){
        playersInGame.remove(Utils.nameToPlayer(name));
        playersStats.remove(name);
    }

    public static int getAmountOfPlayersInGame(){
        return playersInGame.size();
    }

    public static int getAmountOfPlayerStats(){
        return playersStats.size();
    }

    public static EntityPlayer getNextPlayer(){
        for(EntityPlayer ep : playersInGame.keySet()){
            if(!playersInGame.get(ep)){
                playersInGame.put(ep, true);
                return ep;
            }
        }
        return null;
    }

    public static void resetGame(){
        playersStats = new ConcurrentHashMap<>();
        playersInGame = new ConcurrentHashMap<>();

    }


    public static void debug() {
        LogHelper.info("-=-=-=-=-=-=-=-=-=-");
        LogHelper.info("START OF DEBUG");
        LogHelper.info("PLAYERS THAT HAVE STATS: " + getAmountOfPlayerStats());
        LogHelper.info("PLAYERS COUNTED IN GAME: " + getAmountOfPlayersInGame());
        LogHelper.info("-=-=-=-=-=-=-=-=-=-");
        LogHelper.info("LIST OF PLAYERS IN GAME:");
        for(EntityPlayer player : playersInGame.keySet()){
            LogHelper.info(player.getName());
        }
        LogHelper.info("-=-=-=-=-=-=-=-=-=-");
        LogHelper.info("LIST OF STATS / NETWORK LEVELS");
        for(String name : playersStats.keySet()){
            if(playersStats.get(name).isNicked){
                LogHelper.info(name + ": NICKED");
            }else{
                LogHelper.info(name + ": " + playersStats.get(name).networkLevel);
            }

        }
        LogHelper.info("-=-=-=-=-=-=-=-=-=-");
    }
}
