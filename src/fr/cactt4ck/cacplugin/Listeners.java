package fr.cactt4ck.cacplugin;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.cactt4ck.cacplugin.Alert.getColor;
import static fr.cactt4ck.cacplugin.CacPlugin.config;
import static fr.cactt4ck.cacplugin.LobbyGestion.compass;
import static fr.cactt4ck.cacplugin.ServerGestion.clock;

@SuppressWarnings("all")
public class Listeners implements Listener {

    private ItemStack[] firstConnexionKit = new ItemStack[]{
            new ItemStack(Material.IRON_HELMET, 1),
            new ItemStack(Material.IRON_CHESTPLATE, 1),
            new ItemStack(Material.IRON_LEGGINGS, 1),
            new ItemStack(Material.IRON_BOOTS, 1),
            new ItemStack(Material.IRON_PICKAXE, 1),
            new ItemStack(Material.COOKED_BEEF, 64)
    };

    private static List<Performer<Player>> onJoinListeners = new ArrayList<Performer<Player>>(), onQuitListeners = new ArrayList<Performer<Player>>();

    public static void addOnJoinListener(Performer<Player> p){
    	onJoinListeners.add(p);
	}

	public static void removeOnJoinListener(Performer<Player> p){
		onJoinListeners.remove(p);
	}

	public static void addOnQuitListener(Performer<Player> p){
		onQuitListeners.add(p);
	}

	public static void removeOnQuitListener(Performer<Player> p){
		onQuitListeners.remove(p);
	}

	char ch = '&';
	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		for (Performer<Player> perf : onJoinListeners){
			perf.perform(p);
		}

		String message = config.getString("messages.connexion").replace("/player/", p.getName());
		String kit = config.getString("messages.firstconnexion");

		ItemStack compassItem = compass();
		ItemStack clockItem = clock();

        /*try {
            Class.forName("java.sql.Driver");
            String dbusername = config.getString("database.dbusername");
            String dbpassword = config.getString("database.dbpassword");
            String dbip = config.getString("database.dbip");
            String dbname = config.getString("database.dbname");
            String dburl = "jdbc:mysql://" + dbip + "/" + dbname + "?useSSL=false";

            Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);

            CallableStatement getPlayer = connection.prepareCall("{call get_player(?)}");
            getPlayer.setString(1, String.valueOf(p.getUniqueId()));
            getPlayer.execute();

            if(!getPlayer.getResultSet().first()) {
                CallableStatement call = connection.prepareCall("{call create_player(?,?,?,?)}");
                call.setString(1, String.valueOf(p.getUniqueId()));
                call.setString(2, p.getName());
                call.setString(3, "default");
                call.setString(4, "AIX");
                call.execute();
                connection.close();
                p.sendMessage("Vous êtes désormais enregistré dans la base de donnée !");
            }else {
                p.sendMessage("Votre session a été chargée avec succès!");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "        Database connection offline".toUpperCase());
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "        Database connection offline".toUpperCase());
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "        Database connection offline".toUpperCase());
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "        Database connection offline".toUpperCase());
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "        Database connection offline".toUpperCase());
            p.kickPlayer("Database connection offline! Please enable connection");
        }*/


		if (p.getLastPlayed() == 0L) {
			p.getInventory().addItem(firstConnexionKit);
			p.sendMessage(ChatColor.DARK_PURPLE + kit);
			e.setJoinMessage(ChatColor.LIGHT_PURPLE + "Souhaitez la bienvenue à " + p.getName());

		}else if (p.isOp()){
            p.getInventory().setItem(8, compassItem);
            p.getInventory().setItem(7, clockItem);;
	    } else {
			e.setJoinMessage(ChatColor.GREEN + message);
		}

		/*final String pIP = p.getAddress().getHostName();
		if (pIP.contains("127.0.0.1") || pIP.contains("109-208-211-17"))
			return;
		else if (p.getName().equals("CacTt4ck"))
			return;
		else if (p.getUniqueId().equals("4ba987f7-0c42-44c5-9ded-ad1a6261921e"))
		    return;*/

		if (CacPlugin.auths.getConfigurationSection(p.getUniqueId().toString()) != null) {
			p.sendMessage(ChatColor.RED + "Veuillez utiliser /login <mot de passe> !");
		} else {
			p.sendMessage(ChatColor.RED + "Veuillez utiliser /register <mot de passe> <mot de passe> !");
		}

	}

	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		for (Performer<Player> perf : onQuitListeners){
			perf.perform(p);
		}

		String message = CacPlugin.config.getString("messages.deconnexion").replace("/player/", p.getName());
		e.setQuitMessage(ChatColor.GREEN + message);
	}

	@EventHandler
	private void onPlayerDie(PlayerDeathEvent e) {

		Player p = e.getEntity();

		CacPlugin.back.put(p.getName(), p.getLocation());
		try {
			Money.takeMoney(p, 100);
		} catch (NotEnoughMoneyException e1) {
			e1.printStackTrace();
		}
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();

		Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
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

		FireworkEffect spawn = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
		fwm.addEffect(spawn);

		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);

		fw.setFireworkMeta(fwm);
		e.setRespawnLocation(Spawn.spawn);
	}

	@EventHandler
	private void chatColor(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		if(msg.contains("http") || msg.contains("www")){
			e.setMessage(ChatColor.UNDERLINE + msg);
		}else {
			e.setMessage(ChatColor.translateAlternateColorCodes(ch, msg));
		}

	}
}