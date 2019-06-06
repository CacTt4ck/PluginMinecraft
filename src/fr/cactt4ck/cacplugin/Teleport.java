package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static fr.cactt4ck.cacplugin.CacPlugin.back;

@SuppressWarnings("all")
public class Teleport implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		final Player p = (Player)sender;
		
		if (cmd.getName().equalsIgnoreCase("tp")) {
			
			if (args.length == 1) { // Teleports the current player to another player
				
				final Player t = CacUtils.getOnlinePlayer(args[0]);
				if (t == null) {
					p.sendMessage(ChatColor.RED + "Le joueur est introuvable !");
					return true;
				}
				
				p.teleport(t);
				p.sendMessage(ChatColor.DARK_PURPLE + "Vous avez été téléporté à " + t.getName() + " !");

					back.remove(p.getName());
					back.put(p.getName(), p.getLocation());
				
				return true;
				
			} else if (args.length == 2) { // Teleports a player to another player
				
				final Player t0 = CacUtils.getOnlinePlayer(args[0]);
				if (t0 == null) {
					p.sendMessage(ChatColor.RED + "Le joueur spécifié est introuvable !");
					return true;
				}
				
				final Player t1 = CacUtils.getOnlinePlayer(args[1]);
				if (t1 == null) {
					p.sendMessage(ChatColor.RED + "Le joueur cible est introuvable !");
					return true;
				}
				
				t0.teleport(t1);
				p.sendMessage(ChatColor.DARK_PURPLE + "Vous avez téléporté " + t0.getName() + " à " + t1.getName() + " !");
				t0.sendMessage(ChatColor.DARK_PURPLE + "Vous avez été téléporté à " + t1.getName() + " !");
				
				return true;
				
			} else if (args.length == 3) { // Teleports the current player to the coordinates
				
				final int[] coordinates = this.getCoordinates(args[0], args[1], args[2]);
				if (coordinates == null) {
					p.sendMessage(ChatColor.RED + "Les coordonnées entrées sont erronnées !");
					return true;
				}
				
				p.teleport(new Location(p.getWorld(), coordinates[0], coordinates[1], coordinates[2]));
				p.sendMessage("Vous avez été téléporté aux coordonnées ( " + coordinates[0] + " ; " + coordinates[1] + " ; " + coordinates[2] + " ) !");

				return true;
				
			} else if (args.length == 4) { // Teleports a player to the coordinates
				
				final Player t = CacUtils.getOnlinePlayer(args[0]);
				if (t == null) {
					p.sendMessage(ChatColor.RED + "Le joueur spécifié est introuvable !");
					return true;
				}
				
				final int[] coordinates = this.getCoordinates(args[1], args[2], args[3]);
				if (coordinates == null) {
					p.sendMessage(ChatColor.RED + "Les coordonnées entrées sont erronnées !");
					return true;
				}
				
				t.teleport(new Location(p.getWorld(), coordinates[0], coordinates[1], coordinates[2]));
				p.sendMessage("Vous avez téléporté " + t.getName() + " aux coordonnées ( " + coordinates[0] + " ; " + coordinates[1] + " ; " + coordinates[2] + " ) !");
				t.sendMessage("Vous avez été téléporté aux coordonnées ( " + coordinates[0] + " ; " + coordinates[1] + " ; " + coordinates[2] + " ) !");
				
				return true;
				
			}
			
		} else if (cmd.getName().equalsIgnoreCase("tph")) {
			
			if (args.length > 0) {
				
				final String pName = p.getName();
				int n = 0;
				
				for (int i = 0; i < args.length; i++) {
					final Player t = CacUtils.getOnlinePlayer(args[i]);
					if (t == null)
						p.sendMessage(ChatColor.RED + "Le joueur " + args[i] + " est introuvable !");
					else {
						t.teleport(p);
						n++;
						t.sendMessage(ChatColor.DARK_PURPLE + "Vous avez été téléporté à " + pName + " !");
					}
				}
				
				String msg;
				
				if (args.length == 1) {
					if (n == 0)
						msg = "Aucun joueur n'a été téléporté.";
					else
						msg = "Le joueur " + args[0] + " a bien été téléporté à vous.";
				} else {
					if (n == 0)
						msg = "Aucun joueur n'a été téléporté sur les " + args.length + " spécifiés.";
					else if (n == 1)
						msg = "1 joueur a été téléporté sur les " + args.length + " spécifiés.";
					else if (n == args.length)
						msg = "Tous les joueurs ont été téléportés !";
					else
						msg = String.valueOf(n) + " joueurs ont été téléportés sur les " + args.length + " spécifiés.";
				}
				
				p.sendMessage(ChatColor.DARK_PURPLE + msg);
				
				return true;
				
			}
			
		} else if (cmd.getName().equalsIgnoreCase("tpall")) {
			
			if (args.length == 0) {
				
				for (final Player c : Bukkit.getServer().getOnlinePlayers()) {
					if (c != p) {
						c.teleport(p);
						c.sendMessage(ChatColor.DARK_PURPLE + "Vous avez été téléporté à " + p.getName() + " !");
					}
				}
				
				return true;
				
			} else if (args.length == 1) {
				
				final Player t = CacUtils.getOnlinePlayer(args[0]);
				if (t == null) {
					p.sendMessage(ChatColor.RED + "Le joueur spécifié est introuvable !");
					return true;
				}
				
				for (final Player c : Bukkit.getServer().getOnlinePlayers()) {
					if (c != t) {
						c.teleport(t);
						c.sendMessage(ChatColor.DARK_PURPLE + "Vous avez été téléporté à " + t.getName() + " !");
					}
				}
				
				return true;
				
			} else if (args.length == 3) {
				
				final int[] coordinates = this.getCoordinates(args[1], args[2], args[3]);
				if (coordinates == null) {
					p.sendMessage(ChatColor.RED + "Les coordonnées entrées sont erronnées !");
					return true;
				}
				
				final Location location = new Location(p.getWorld(), coordinates[0], coordinates[1], coordinates[2]);
				
				for (final Player c : Bukkit.getServer().getOnlinePlayers()) {
					if (c != p) {
						c.teleport(location);
						c.sendMessage("Vous avez été téléporté aux coordonnées ( " + coordinates[0] + " ; " + coordinates[1] + " ; " + coordinates[2] + " ) !");
					}
				}
				return true;
				
			}
			
		}else if(cmd.getName().equalsIgnoreCase("back")){

			if (back.containsKey(p.getName())) {
                p.teleport(back.get(p.getName()));
			    back.remove(p.getName());
                p.sendMessage(ChatColor.GRAY + "Retour à votre emplacement précédent...");
            }else {

			    p.sendMessage(ChatColor.RED + "Impossible de revenir à votre emplacement précédent!");
            }
			return true;

		}else if(cmd.getName().equalsIgnoreCase("randomtp")){
			Random ran1 = new Random();
			Random ran2 = new Random();

			int x = ran1.nextInt(20000) + 1000;
			int z = ran2.nextInt(20000) + 1000;
			int y = 128;
			int signex = 0 + (int)(Math.random() * ((1 - 0) + 1));
			int signez = 0 + (int)(Math.random() * ((1 - 0) + 1));
			if(signex == 1){
				x = x;
			}else if(signex == 0){
				x = -x;
			}
			if(signez == 1){
				z = z;
			}else if(signez == 0){
				z = -z;
			}
			World world = p.getWorld();
			Location l = new Location(world, x, y, z);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 127, false, false));
			p.teleport(l);
			p.sendMessage("Vous avez été téléporté en " + x + "  " + y + "  " + z);

		}
		
		return false;
		
	}
	
	
	
	private int[] getCoordinates(final String x, final String y, final String z) {
		int[] coordinates = new int[3];
		
		try {
			coordinates[0] = Integer.valueOf(x);
			coordinates[1] = Integer.valueOf(y);
			coordinates[2] = Integer.valueOf(z);
		} catch (NumberFormatException e) {
			coordinates = null;
		}
		
		return coordinates;
	}

}
