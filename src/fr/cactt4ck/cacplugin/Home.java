package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import static fr.cactt4ck.cacplugin.CacPlugin.*;

public class Home implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

            Player p = (Player)sender;
            String pname = p.getName();

            if(cmd.getName().equalsIgnoreCase("sethome")){
                if(args.length == 1){
                    CacPlugin.home.set(pname + "." + args[0] + ".x", p.getLocation().getBlockX());
                    CacPlugin.home.set(pname + "." + args[0] + ".y", p.getLocation().getBlockY());
                    CacPlugin.home.set(pname + "." + args[0] + ".z", p.getLocation().getBlockZ());
                    CacPlugin.home.set(pname + "." + args[0] + ".world", p.getWorld().getName());
                    p.sendMessage(ChatColor.GRAY + "Home " + args[0] + " défini !");
                }else{
                    p.sendMessage(ChatColor.RED + "Erreur, faites /sethome <nom du home> !");
                }
                saveHome();
            }else if(cmd.getName().equalsIgnoreCase("home")){
                CacPlugin.home = YamlConfiguration.loadConfiguration(homeFile);
                Location ploc = p.getLocation();
                Location previousLoc = new Location(p.getWorld(), ploc.getBlockX(), ploc.getBlockY(), ploc.getBlockZ());
                try{
                    if(args[0].contains(":")) {
                        String pseudo = args[0].split(":")[0];
                        String homeName = args[0].split(":")[1];
                        if (home.contains(pseudo)) {
                            if (home.contains(pseudo + "." + homeName)) {
                                int x = CacPlugin.home.getInt(pseudo + "." + homeName + ".x");
                                int y = CacPlugin.home.getInt(pseudo + "." + homeName + ".y");
                                int z = CacPlugin.home.getInt(pseudo + "." + homeName + ".z");
                                World w = Bukkit.getServer().getWorld(CacPlugin.home.getString(pseudo + "." + homeName + ".world"));
                                Location l = new Location(w, x, y, z);
                                if(back.containsKey(p.getName())){
                                    back.remove(p.getName());
                                    back.put(p.getName(), previousLoc);
                                }else{
                                    back.put(p.getName(), previousLoc);
                                }
                                p.teleport(l);
                                p.sendMessage(ChatColor.GRAY + "Téléportation au home " + ChatColor.LIGHT_PURPLE + homeName + ChatColor.GRAY
                                        +" du joueur " + ChatColor.LIGHT_PURPLE + pseudo + ChatColor.GRAY + " !");
                            }
                        }else {
                            p.sendMessage(ChatColor.RED + "Erreur ce joueur n'existe pas!");
                        }
                    }else if(args.length == 1){
                        if(home.contains(p.getName() + "." + args[0])){
                            int x = CacPlugin.home.getInt(p.getName() + "." + args[0] + ".x");
                            int y = CacPlugin.home.getInt(p.getName() + "." + args[0] + ".y");
                            int z = CacPlugin.home.getInt(p.getName() + "." + args[0] + ".z");
                            World w = Bukkit.getServer().getWorld(CacPlugin.home.getString(p.getName() + "." + args[0] + ".world"));
                            Location l = new Location(w, x, y, z);
                            p.teleport(l);
                            p.sendMessage(ChatColor.GRAY + "Téléportation à " + args[0] + " !");
                        }else{
                            p.sendMessage(ChatColor.RED + "Erreur! Ce home n'existe pas !");
                        }
                    }else {
                            p.sendMessage(ChatColor.RED + "Erreur, faites /home <nom du home> !");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(cmd.getName().equalsIgnoreCase("delhome")){
                CacPlugin.home = YamlConfiguration.loadConfiguration(homeFile);
                if(args.length == 1){
                    if(!home.contains(p.getName())){
                        p.sendMessage(ChatColor.RED + "Vous n'avez aucun home !");
                    }else{
                        home.set(p.getName() + "." + args[0], null);
                        saveHome();
                        p.sendMessage(ChatColor.GRAY + "Home " + args[0] + " supprimé !");
                    }
                }else{
                    p.sendMessage(ChatColor.RED + "Erreur! Faites /delhome <nom du home> !");
                }
            }
        return true;
    }
}
