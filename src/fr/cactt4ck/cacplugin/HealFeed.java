package fr.cactt4ck.cacplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class HealFeed implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		Player p = (Player)s;
		
		if (cmd.getName().equalsIgnoreCase("heal")) {

			Player t;
			
			if (args.length == 0)
				t = p;
			else if (args.length == 1)
				t = CacUtils.getOnlinePlayer(args[0]);
			else {
				p.sendMessage(ChatColor.RED + "Utilisez /heal <joueur> ou /heal");
				return true;
			}
			
			t.setHealth(20D);
			t.sendMessage(ChatColor.DARK_PURPLE + "Vous avez été soigné !");
			return true;
			
		} else if (cmd.getName().equalsIgnoreCase("feed")) {
			
			final Player t;
			
			if (args.length == 0)
				t = p;
			else if (args.length == 1)
				t = CacUtils.getOnlinePlayer(args[0]);
			else {
				p.sendMessage(ChatColor.RED + "Utilisez /feed <joueur> ou /feed");
				return true;
			}

			t.setFoodLevel(20);
			t.setSaturation(20F);
			t.sendMessage(ChatColor.DARK_PURPLE + "Vous êtes rassasié !");
			return true;
			
		}
		
		return true;
		
	}
	
}
