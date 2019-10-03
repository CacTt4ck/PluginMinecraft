package fr.cactt4ck.cacplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class Bot implements CommandExecutor {

    private Entity bot;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

        Player p = (Player)s;

        if(cmd.getName().equalsIgnoreCase("bot")){
            if(args.length > 2)
                return false;
            else {
                if(args[0].equalsIgnoreCase("summon")){
                    NPCManager.createNPC(p,args[1]);
                }
            }
        }

        return true;
    }

}
