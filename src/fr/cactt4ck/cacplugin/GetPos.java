package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GetPos implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

        Player p = (Player)s;
        Player t = Bukkit.getServer().getPlayer(args[0]);

        if(cmd.getName().equalsIgnoreCase("getpos")){
            if(args.length == 1){
                final UUID uuid = CacUtils.getPlayerUUID(args[0]);
                if (uuid == null) {
                    p.sendMessage(ChatColor.RED + "Le joueur " + args[0] + " n'existe pas !");
                    return true;
                }else {
                    int xpos = t.getLocation().getBlockX();
                    int ypos = t.getLocation().getBlockY();
                    int zpos = t.getLocation().getBlockZ();
                    String world = p.getWorld().getName();
                    p.sendMessage("Le joueur " + ChatColor.DARK_PURPLE + t.getName() + ChatColor.WHITE + " se trouve en " + ChatColor.DARK_PURPLE +
                            xpos + " " + ypos + " " + zpos + ChatColor.WHITE + " et dans le monde : " + ChatColor.DARK_PURPLE + world);
                }
            }else {
                p.sendMessage(ChatColor.RED + "Erreur faites /getpos <player>!");
            }
        }
        return true;
    }
}
