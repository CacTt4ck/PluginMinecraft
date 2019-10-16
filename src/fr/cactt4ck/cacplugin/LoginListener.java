package fr.cactt4ck.cacplugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.logging.log4j.core.util.Loader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

@SuppressWarnings("All")
public class LoginListener implements Listener {

	private final JavaPlugin plugin;

	private static volatile HashSet<FreezerThread> currentLogins = new HashSet<FreezerThread>();
	public static HashMap<Player, Location> spawnLocation = new HashMap<Player, Location>();

	public LoginListener(JavaPlugin plugin){
		this.plugin = plugin;
	}

	@EventHandler
	private void onPlayerChat(AsyncPlayerChatEvent e) {
		final Player p = e.getPlayer();
		if (!LoginListener.isPlayerAuthenticated(p)) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Veuillez vous authentifier pour pouvoir parler dans le chat !");
		}
	}

	@EventHandler
    private void onPlayerMove(PlayerMoveEvent e){
	    final Player p = e.getPlayer();
	    final Location loc = spawnLocation.get(p);
	    if (FreezerThread.doFreeze = true){
            if(spawnLocation.containsKey(p)){
                p.teleport(loc);
            }else{
                //do nothing
            }
        }else{
	        //do nothing
        }
    }

	@EventHandler
	private void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		final String command = e.getMessage().toLowerCase();
		final Player p = e.getPlayer();

		if (!LoginListener.isPlayerAuthenticated(p) && !command.startsWith("/login ") && !command.startsWith("/lg ") && !command.startsWith("/log ") && !command.startsWith("/register ") && !command.startsWith("/reg ")) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Veuillez vous authentifier pour pouvoir utiliser des commandes !");
		}
	}

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = e.getPlayer();
        final Location loc = player.getLocation();
        spawnLocation.put(player, loc);

		if (CacPlugin.money.getConfigurationSection(player.getUniqueId().toString()) == null) { // if (!CacPlugin.money.contains("player.getUniqueId.toString()"))
			CacPlugin.money.set(player.getUniqueId().toString() + ".value", 0);
			CacPlugin.saveMoney();
		}

		FreezerThread playerFreezer = new FreezerThread(player);
		LoginListener.currentLogins.add(playerFreezer);
		Bukkit.getScheduler().runTaskAsynchronously(plugin, playerFreezer);
	}



	public static boolean isPlayerAuthenticated(final Player player) {
		return !LoginListener.currentLogins.contains(LoginListener.getFreezerThread(player));
	}

	public synchronized static void doPlayerAuthenticated(final Player player) {
		if (LoginListener.isPlayerAuthenticated(player))
			throw new IllegalStateException("Player " + player.getName() + " is already authenticated !");

		FreezerThread playerFreezer = LoginListener.getFreezerThread(player);
		playerFreezer.stopFreezing();
		LoginListener.currentLogins.remove(playerFreezer);
	}

	private static FreezerThread getFreezerThread(final Player player) {
		for (FreezerThread cFreezer : currentLogins) {
			if (cFreezer.getPlayer().equals(player))
				return cFreezer;
		}

		return null;
	}



}

class FreezerThread extends BukkitRunnable{

	private final Player player;
	private final GameMode gameMode;
	private final Location location;
	private final ItemStack[] inventoryContents;

	public static boolean doFreeze;


	public FreezerThread(final Player player) {
		this.player = player;
		this.gameMode = player.getGameMode();
		this.location = player.getLocation();
		this.inventoryContents = player.getInventory().getContents();

		this.doFreeze = true;
	}



	public Player getPlayer() {
		return this.player;
	}



	@Override
	public void run() {
	    try{
            this.player.getInventory().clear();
            this.player.setGameMode(GameMode.SPECTATOR);
            this.doFreeze = true;

		/*while (doFreeze) {
			if (!this.player.getLocation().equals(this.location))
				this.player.teleport(this.location);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/

            this.player.setGameMode(this.gameMode);
            this.player.getInventory().setContents(this.inventoryContents);
            this.doFreeze = false;
        }catch (Exception e){}

	}

	public void stopFreezing() {
		this.doFreeze = false;
		this.player.setGameMode(this.gameMode);
		this.player.getInventory().setContents(this.inventoryContents);
	}

}










/*
class FreezerThread extends BukkitRunnable {

	private final Player player;
	private final GameMode gameMode;
	private final Location location;
	private final ItemStack[] inventoryContents;
	private final double health;
	private final int foodLevel;
	private final float saturation, exp;

	private boolean doFreeze;


	public FreezerThread(final Player player) {
		this.player = player;
		this.gameMode = player.getGameMode();
		this.location = player.getLocation();
		this.health = player.getHealth();
		this.foodLevel = player.getFoodLevel();
		this.saturation = player.getSaturation();
		this.exp = player.getExp();
		this.inventoryContents = player.getInventory().getContents();

		this.doFreeze = true;
	}



	public Player getPlayer() {
		return this.player;
	}



	@Override
	public void run() {
		this.player.getInventory().clear();
		this.player.setHealth(20.0D);
		this.player.setFoodLevel(20);
		this.player.setSaturation(20F);
		this.player.setExp(0);
		this.player.setGameMode(GameMode.ADVENTURE);

		while (doFreeze) {
			this.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6, 4, false, false));
			if (!this.player.getLocation().equals(this.location))
				this.player.teleport(this.location);

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		player.setGameMode(this.gameMode);
		this.player.setExp(this.exp);
		this.player.setSaturation(this.saturation);
		this.player.setFoodLevel(this.foodLevel);
		this.player.setHealth(this.health);
		this.player.getInventory().setContents(this.inventoryContents);
	}

	public void stopFreezing() {
		this.doFreeze = false;
	}

}
 */

