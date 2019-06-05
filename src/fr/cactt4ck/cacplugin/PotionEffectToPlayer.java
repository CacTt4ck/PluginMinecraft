package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectToPlayer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

        Player p = (Player)s;
        Player t = Bukkit.getServer().getPlayer(args[0]);

        if(cmd.getName().equalsIgnoreCase("popo")){
            if (args.length != 3){
                p.sendMessage(ChatColor.RED + "Erreur! Faites /popo <effet> <durée en secondes> <niveau de l'effet>!");
            }else{
                int duree = Integer.valueOf(args[1])*20;

                if      (args[0].equalsIgnoreCase("speed")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("slowness")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("haste")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("slowdig")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("strength")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("heal")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("harm")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HARM, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("jump")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("nausea")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("regen")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("resist")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("fireresist")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("waterbreath")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("invisibility")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("blind")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("nightvision")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("hunger")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("weakness")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("poison")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("wither")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("healboost")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("absorption")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("saturation")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("glow")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("levitation")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("luck")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, duree, Integer.valueOf(args[2])));
                }else if(args[0].equalsIgnoreCase("unluck")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, duree, Integer.valueOf(args[2])));
                }else{
                    p.sendMessage(ChatColor.RED + "Effet non trouvé! Veuillez rééssayer avec un effet valide!");
                }
            }
        }
        return true;
    }
}