package fr.cactt4ck.cacplugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class Gamemode implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("gm")) {
                if (args.length == 0) {
                    if (p.getGameMode() == GameMode.CREATIVE) {
                        p.setGameMode(GameMode.SURVIVAL);
                        sender.sendMessage(ChatColor.GOLD + "Nouveau mode de jeu : " + ChatColor.BOLD + "Survie");
                    } else if (p.getGameMode() == GameMode.SURVIVAL) {
                        p.setGameMode(GameMode.CREATIVE);
                        sender.sendMessage(ChatColor.GOLD + "Nouveau mode de jeu : " + ChatColor.BOLD + "Créatif");
                    } else if (p.getGameMode() == GameMode.ADVENTURE) {
                        p.setGameMode(GameMode.SURVIVAL);
                        sender.sendMessage(ChatColor.GOLD + "Nouveau mode de jeu : " + ChatColor.BOLD + "Survie");
                    } else if (p.getGameMode() == GameMode.SPECTATOR) {
                        p.setGameMode(GameMode.CREATIVE);
                        sender.sendMessage(ChatColor.GOLD + "Nouveau mode de jeu : " + ChatColor.BOLD + "Créatif");
                    }
                } else if (args.length == 1) {
                    if (Integer.valueOf(args[0]).intValue() == 0) {
                        p.setGameMode(GameMode.SURVIVAL);
                    } else if (Integer.valueOf(args[0]).intValue() == 1) {
                        p.setGameMode(GameMode.CREATIVE);
                    } else if (Integer.valueOf(args[0]).intValue() == 2) {
                        p.setGameMode(GameMode.ADVENTURE);
                    } else if (Integer.valueOf(args[0]).intValue() == 3) {
                        p.setGameMode(GameMode.SPECTATOR);
                    }
                }
        }
        return true;
    }
}
