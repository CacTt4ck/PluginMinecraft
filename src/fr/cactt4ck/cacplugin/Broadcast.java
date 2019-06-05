package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("all")
public class Broadcast implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {

        char ch = '&';

        if (cmd.getName().equalsIgnoreCase("bc")) {

            if(args.length == 0){
                sender.sendMessage("§4/bc <message>");
                return true;
            }else{
                String bc = "";
                for(String message : args){
                    bc = (bc + message + " ");
                }
                Bukkit.broadcastMessage("§f[§2CacBroadcast§f] " + ChatColor.translateAlternateColorCodes(ch, bc));
            }
        } else if (cmd.getName().equalsIgnoreCase("clearchat")){
            for (int i = 0; i < 100; i++) {
                Bukkit.broadcastMessage("");
            }

            Bukkit.broadcastMessage(ChatColor.GOLD + "|-------------------+====+-------------------|");
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + " The chat has been cleared by a staff member.");
            Bukkit.broadcastMessage(ChatColor.GOLD + "|-------------------+====+-------------------|");
        }
        return true;
    }
}