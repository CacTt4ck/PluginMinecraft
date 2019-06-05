package fr.cactt4ck.cacplugin;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class Money implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player)sender;
		String moneyString = CacPlugin.config.getString("messages.money");
		
		if (cmd.getName().equalsIgnoreCase("money")) {
			
			// Shows the player's money
			if (args.length == 0) {
				p.sendMessage(ChatColor.GOLD + "Vous avez " + Money.getMoney(p.getUniqueId()) + moneyString + " !");
				return true;
				
			} else {
				// Shows the money top ranking players
				if (args[0].equalsIgnoreCase("top")) {
					
					// Returns the money top 10 ranking players
					if (args.length == 1) {
						// DO
						return true;
					// Returns the money top n ranking players
					} else if (args.length == 2) {
						// DO
						return true;
						
					}
					
				// Gives the specified amount of money to the given player
				} else if (args[0].equalsIgnoreCase("give") && args.length == 3) {
					
					final UUID tUUID = CacUtils.getPlayerUUID(args[1]);
					
					if (tUUID == null) {
						p.sendMessage(ChatColor.RED + "Le joueur " + args[1] + " n'existe pas !");
						return true;
					}
					
					Money.giveMoney(tUUID, Integer.valueOf(args[2]));
					
					p.sendMessage(ChatColor.GOLD + "Vous avez donné " + args[2] + " " + moneyString + " " + args[1] + " !");
					
					return true;
					
				// Takes the specified amount of money from the given player
				} else if (args[0].equalsIgnoreCase("take") && args.length == 3) {
					
					final UUID tUUID = CacUtils.getPlayerUUID(args[1]);
					
					if (tUUID == null) {
						p.sendMessage(ChatColor.RED + "Le joueur " + args[1] + " passé en argument n'existe pas !");
						return true;
					}
					
					try {
						Money.takeMoney(tUUID, Integer.valueOf(args[2]));
					} catch (NotEnoughMoneyException e) {
						p.sendMessage(ChatColor.RED + "Vous ne pouvez pas retirer plus de dollars que ce que le joueur en a !");
						return true;
					}
					
					p.sendMessage(ChatColor.GOLD + "Vous avez pris " + args[2] + " "  + moneyString + " à " + args[1] + " !");
					
					return true;
					
				// Resets all the money of the given player(s)
				} else if (args[0].equalsIgnoreCase("reset") && args.length > 1) {
					
					UUID[] uuids = new UUID[args.length-1];
					for (int i = 1; i < args.length; i++) {
						final UUID cUUID = CacUtils.getPlayerUUID(args[i]);
						
						if (cUUID == null) {
							p.sendMessage(ChatColor.RED + "Le joueur " + args[i] + " n'existe pas !");
							return true;
						}
						
						uuids[i-1] = cUUID;
					}
					
					
					for (int i = 0; i < uuids.length; i++)
						Money.resetMoney(uuids[i]);
					
					String playersString = args[1];
					if (args.length > 2) {
						for (int i = 2; i < args.length; i++)
							playersString += (i == args.length - 1 ? " et " : ", ") + args[i];
					}
					
					p.sendMessage(ChatColor.GOLD + "Vous avez réinitialisé l'argent de " + playersString + " !");
					
					return true;
					
				} else if (args[0].equalsIgnoreCase("set") && args.length == 3) {
					
					final UUID tUUID = CacUtils.getPlayerUUID(args[1]);
					
					if (tUUID == null) {
						p.sendMessage(ChatColor.RED + "Le joueur " + args[1] + " n'existe pas !");
						return true;
					}
					
					try {
						Money.setMoney(tUUID, Integer.valueOf(args[2]));
					} catch (NotEnoughMoneyException e) {
						p.sendMessage(ChatColor.RED + "Vous ne pouvez pas définir l'argent de " + args[1] + " sur une valeur négative !");
						return true;
					}
					
					p.sendMessage(ChatColor.GOLD + "Vous avez défini l'argent de " + args[1] + " à " + args[2] + moneyString + " !");
					
					return true;
					
				} else if (args.length == 1) {
					final UUID uuid = CacUtils.getPlayerUUID(args[0]);
					if (uuid == null) {
						p.sendMessage(ChatColor.RED + "Le joueur " + args[0] + " n'existe pas !");
						return true;
					}
					
					p.sendMessage(ChatColor.GOLD + args[0] + " a " + Money.getMoney(uuid) + " " + moneyString + " !");
				}
				
			}
			
		// Makes the current player pay the given player with the specified amount of money
		} else if (cmd.getName().equalsIgnoreCase("pay")) {
			
			if (args.length == 2) {
				
				final UUID tUUID = CacUtils.getPlayerUUID(args[0]);
				final int money = Integer.valueOf(args[1]);
				
				if (tUUID == null) {
					p.sendMessage(ChatColor.RED + "Le joueur " + args[0] + " n'existe pas !");
					return true;
				}
				
				try {
					Money.takeMoney(p.getUniqueId(), money);
				} catch (NotEnoughMoneyException e) {
					p.sendMessage(ChatColor.RED + "Vous ne pouvez pas envoyer plus de dollars que ce que vous en avez !");
					return true;
				}
				Money.giveMoney(tUUID, money);
				
				p.sendMessage(ChatColor.GOLD + "Vous avez envoyé " + money + " " + moneyString + " " + args[0] + " !");
				final Player target = CacUtils.getOnlinePlayer(args[0]);
				if (target != null)
					target.sendMessage(ChatColor.GOLD + "Vous avez reçu " + money + " " + moneyString + " " + p.getName() + " !");
				
				return true;
				
			} else {
				p.sendMessage(ChatColor.RED + "Usage : /pay <joueur> <dollars>");
				return true;
			}
			
		}
		
		return false;
	}
	
	
	
	
	
	public static int getMoney(final Player p) {
		return getMoney(CacUtils.getPlayerUUID(p.getName()));
	}
	
	public static int getMoney(final UUID uuid) {
		return CacPlugin.money.getInt(uuid.toString() + ".value");
	}
	
	
	
	
	public static void giveMoney(final Player p, final int amount) {
		giveMoney(CacUtils.getPlayerUUID(p.getName()), amount);
	}
	
	public static void giveMoney(final UUID uuid, final int amount) {
		try {
			Money.editMoney(uuid, amount);
		} catch (NotEnoughMoneyException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void takeMoney(final Player p, final int amount) throws NotEnoughMoneyException {
		takeMoney(CacUtils.getPlayerUUID(p.getName()), amount);
	}
	
	public static void takeMoney(final UUID uuid, final int amount) throws NotEnoughMoneyException {
		Money.editMoney(uuid, -amount);
	}
	
	
	public static void editMoney(final Player p, final int amount) throws NotEnoughMoneyException {
		editMoney(CacUtils.getPlayerUUID(p.getName()), amount);
	}
	
	public static void editMoney(final UUID uuid, final int amount) throws NotEnoughMoneyException {
		Money.setMoney(uuid, Money.getMoney(uuid) + amount);
	}
	
	
	
	
	public static void resetMoney(final Player p) {
		resetMoney(CacUtils.getPlayerUUID(p.getName()));
	}
	
	public static void resetMoney(final UUID uuid) {
		try {
			Money.setMoney(uuid, 0);
		} catch (NotEnoughMoneyException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void setMoney(final Player p, final int amount) throws NotEnoughMoneyException {
		setMoney(CacUtils.getPlayerUUID(p.getName()), amount);
	}
	
	public static void setMoney(final UUID uuid, final int amount) throws NotEnoughMoneyException {
		if (amount < 0)
			throw new NotEnoughMoneyException(uuid, amount);
		
		CacPlugin.money.set(uuid.toString() + ".value", amount);
		
		CacPlugin.saveMoney();
	}
	
}
