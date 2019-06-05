package fr.cactt4ck.cacplugin;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("all")
public final class CacUtils {
	
	private CacUtils(){}
	
	
	
	public static UUID getPlayerUUID(final String playerName) {
		final Player onlinePlayer = CacUtils.getOnlinePlayer(playerName);
		if (onlinePlayer != null)
			return onlinePlayer.getUniqueId();
		
		final OfflinePlayer offlinePlayer = CacUtils.getOfflinePlayer(playerName);
		if (offlinePlayer != null)
			return offlinePlayer.getUniqueId();
		
		return null;
	}
	
	public static String getPlayerName(final UUID playerUUID) {
		final Player onlinePlayer = CacUtils.getOnlinePlayer(playerUUID);
		if (onlinePlayer != null)
			return onlinePlayer.getName();
		
		final OfflinePlayer offlinePlayer = CacUtils.getOfflinePlayer(playerUUID);
		if (offlinePlayer != null)
			return offlinePlayer.getName();
		
		return null;
	}
	
	
	//-----------------------------------------------------------------------------------------------------//
	
	
	public static Player getOnlinePlayer(final String playerName) {
		final Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (final Player p : onlinePlayers) {
			if (p.getName().equals(playerName))
				return p;
		}
		
		return null;
	}
	
	public static Player getOnlinePlayer(final UUID playerUUID) {
		final Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (final Player p : onlinePlayers) {
			if (p.getUniqueId().equals(playerUUID))
				return p;
		}
		
		return null;
	}
	
	
	
	public static OfflinePlayer getOfflinePlayer(final String playerName) {
		final OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
		for (final OfflinePlayer p : offlinePlayers) {
			if (p.getName().equals(playerName))
				return p;
		}
		
		return null;
	}

	public static OfflinePlayer getOfflinePlayer(final UUID playerUUID) {
		final OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
		for (final OfflinePlayer p : offlinePlayers) {
			if (p.getUniqueId().equals(playerUUID))
				return p;
		}
		
		return null;
	}

	
}
