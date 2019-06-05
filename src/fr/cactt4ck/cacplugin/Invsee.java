package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Invsee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

        Player p = (Player)s;
        Player t = Bukkit.getServer().getPlayer(args[0]);

        try{
            if(cmd.getName().equalsIgnoreCase(("invsee"))){
                if(args.length == 1){
                    final UUID uuid = CacUtils.getPlayerUUID(args[0]);
                    if (uuid == null){
                        p.sendMessage(ChatColor.RED + "Le joueur " + args[0] + " n'existe pas !");
                    }else {
                        Inventory tInv = t.getInventory();
                        p.openInventory(tInv);
                    }
                }else{
                    p.sendMessage(ChatColor.RED + "/invsee <joueur>!");
                }
            }
        }catch (CommandException e){
            e.printStackTrace();
            p.sendMessage(e.getMessage());
        }
        return true;
    }

}
