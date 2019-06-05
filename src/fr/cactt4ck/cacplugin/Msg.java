package fr.cactt4ck.cacplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Msg implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {


        Player p = (Player)s;
        if(cmd.getName().equalsIgnoreCase("msg")){

            if(args.length == 0 || args.length == 1 || args.length > 2){
                p.sendMessage(ChatColor.RED + "Erreur faites /msg <Player> <Message>!");

            }else {
                Player target = CacUtils.getOnlinePlayer(args[0]);
                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Le joueur spécifié est introuvable !");

                }else {
                    target.sendMessage(ChatColor.GOLD + "<" + p.getName() + ">" + ChatColor.RED + " --> " + ChatColor.GOLD + "<" + target.getName() + ">: " + ChatColor.GRAY + args[1]);
                    p.sendMessage(ChatColor.GOLD + "<" + p.getName() + ">" + ChatColor.RED + " --> " + ChatColor.GOLD + "<" + target.getName() + ">: " + ChatColor.GRAY + args[1]);
                }
            }
        }
        return true;
    }
}
