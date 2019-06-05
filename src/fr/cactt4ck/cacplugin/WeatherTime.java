package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class WeatherTime implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        Player p = (Player)s;

        if(cmd.getName().equalsIgnoreCase("sun")){

            Bukkit.dispatchCommand(p, "weather clear");

        }else if(cmd.getName().equalsIgnoreCase("rain")){

            Bukkit.dispatchCommand(p, "weather rain");

        }else if(cmd.getName().equalsIgnoreCase("day")){

            Bukkit.dispatchCommand(p, "time set day");

        }else if(cmd.getName().equalsIgnoreCase("night")){

            Bukkit.dispatchCommand(p, "time set night");

        }else if(cmd.getName().equalsIgnoreCase("midday")){

            Bukkit.dispatchCommand(p, "time set 6000");
        }else if(cmd.getName().equalsIgnoreCase("thunder")){

            Bukkit.dispatchCommand(p, "weather thunder");

        }

        return true;
    }
}
