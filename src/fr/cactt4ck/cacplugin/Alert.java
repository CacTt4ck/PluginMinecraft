package fr.cactt4ck.cacplugin;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;


@SuppressWarnings("all")
public class Alert implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) throws NullPointerException{

        Player p = (Player)s;
        Player t = CacUtils.getOnlinePlayer(args[1]);

        if(cmd.getName().equalsIgnoreCase("alert")){

            if(args[0].equalsIgnoreCase("discret")){
                if(!args[1].isEmpty()){
                    t.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Vous avez été averti!");

                }else{
                    p.sendMessage(ChatColor.RED + "Utilisez /alert discret <joueur>!");
                    return true;
                }

                return true;
            }else if(args[0].equalsIgnoreCase("public")){
                if(!args[1].isEmpty()){
                    Firework fw = (Firework) t.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    Random r = new Random();

                    //Get the type
                    int rt = r.nextInt(5) + 1;
                    FireworkEffect.Type type = FireworkEffect.Type.BALL;
                    if (rt == 1) type = FireworkEffect.Type.BALL;
                    if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
                    if (rt == 3) type = FireworkEffect.Type.BURST;
                    if (rt == 4) type = FireworkEffect.Type.CREEPER;
                    if (rt == 5) type = FireworkEffect.Type.STAR;

                    //Get our random colours
                    int r1i = r.nextInt(17) + 1;
                    int r2i = r.nextInt(17) + 1;
                    Color c1 = getColor(r1i);
                    Color c2 = getColor(r2i);

                    FireworkEffect discret = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
                    fwm.addEffect(discret);

                    int rp = r.nextInt(2) + 1;
                    fwm.setPower(rp);

                    fw.setFireworkMeta(fwm);

                    p.sendMessage(ChatColor.BLUE + "Le joueur " + t.getName() + "a été averti!");
                    t.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Vous avez été averti!");
                    t.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Vous avez été averti!");
                    t.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Vous avez été averti!");

                }else{
                    p.sendMessage(ChatColor.RED + "Utilisez /alert public <joueur>!");
                    return true;
                }
                return true;
            }

            return true;
        }

        return true;
    }

    public static Color getColor(int i) {
        Color c = null;
        if(i==1){
            c=Color.AQUA;
        }
        if(i==2){
            c=Color.BLACK;
        }
        if(i==3){
            c=Color.BLUE;
        }
        if(i==4){
            c=Color.FUCHSIA;
        }
        if(i==5){
            c=Color.GRAY;
        }
        if(i==6){
            c=Color.GREEN;
        }
        if(i==7){
            c=Color.LIME;
        }
        if(i==8){
            c=Color.MAROON;
        }
        if(i==9){
            c=Color.NAVY;
        }
        if(i==10){
            c=Color.OLIVE;
        }
        if(i==11){
            c=Color.ORANGE;
        }
        if(i==12){
            c=Color.PURPLE;
        }
        if(i==13){
            c=Color.RED;
        }
        if(i==14){
            c=Color.SILVER;
        }
        if(i==15){
            c=Color.TEAL;
        }
        if(i==16){
            c=Color.WHITE;
        }
        if(i==17){
            c=Color.YELLOW;
        }

        return c;
    }
}
