package fr.cactt4ck.cacplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class Spotter implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        Player p = (Player)s;

        if(cmd.getName().equalsIgnoreCase("spotter")){
            if(args.length == 0){

                p.sendMessage(ChatColor.RED + "Utilisez /spotter on/off!");
                return true;

            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("on")){

                        p.sendMessage(ChatColor.RED + "Spotter activé!");
                        p.setCanPickupItems(false);
                        return true;

                }else if(args[0].equalsIgnoreCase("off")){

                        p.sendMessage(ChatColor.RED + "Spotter désactivé!");
                        p.setCanPickupItems(true);
                        return true;
                }

            }else {

                p.sendMessage(ChatColor.RED + "Utilisez /spotter on/off!");
                return true;
            }
        }
        return true;
    }
}
