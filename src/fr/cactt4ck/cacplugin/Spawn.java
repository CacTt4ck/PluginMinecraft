package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import static fr.cactt4ck.cacplugin.CacPlugin.back;
import static fr.cactt4ck.cacplugin.CacPlugin.saveSpawn;
import static fr.cactt4ck.cacplugin.CacPlugin.spawnFile;

@SuppressWarnings("all")
public class Spawn implements CommandExecutor{

    public static Location spawn;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("setspawn")){

            Player p = (Player) sender;

            CacPlugin.spawn.set("spawn.x", p.getLocation().getBlockX());
            CacPlugin.spawn.set("spawn.y", p.getLocation().getBlockY());
            CacPlugin.spawn.set("spawn.z", p.getLocation().getBlockZ());
            CacPlugin.spawn.set("spawn.world", p.getWorld().getName());
            CacPlugin.spawn.set("spawn.yaw", (int) p.getLocation().getYaw());
            CacPlugin.spawn.set("spawn.pitch", (int) p.getLocation().getPitch());

            p.sendMessage(ChatColor.GRAY + "Spawn Set !");

            saveSpawn();

            return true;
        }else if(cmd.getName().equalsIgnoreCase("spawn")){

            Player p = (Player) sender;
            Location ploc = p.getLocation();

            CacPlugin.spawn = YamlConfiguration.loadConfiguration(spawnFile);
            Location previousLoc = new Location(p.getWorld(), ploc.getBlockX(), ploc.getBlockY(), ploc.getBlockZ());

            try{
                String worldname = CacPlugin.spawn.getString("spawn.world");

                int x = CacPlugin.spawn.getInt("spawn.x");
                int y = CacPlugin.spawn.getInt("spawn.y");
                int z = CacPlugin.spawn.getInt("spawn.z");
                double xx = x + 0.5;
                double zz = z + 0.5;
                int yaw = CacPlugin.spawn.getInt("spawn.yaw");
                int pitch = CacPlugin.spawn.getInt("spawn.pitch");
                World w = Bukkit.getServer().getWorld(worldname);

                spawn = new Location(w, xx, y, zz);
                spawn.setYaw((float) yaw);
                spawn.setPitch((float) pitch);

                p.teleport(spawn);
                p.sendMessage("Retour au spawn");
                if(back.containsKey(p.getName())){
                    back.remove(p.getName());
                    back.put(p.getName(), previousLoc);
                }else{
                    back.put(p.getName(), previousLoc);
                }
            }catch (Exception e){
                p.sendMessage(ChatColor.RED + "Erreur le spawn n'est pas défini.");
                System.out.println("Erreur le spawn n'est pas defini.");
            }

            return true;
        }
        return true;
    }
}