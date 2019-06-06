package fr.cactt4ck.cacplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class CacPlugin extends JavaPlugin {
	
	public static FileConfiguration config, auths, money, spawn, home, bank;
	public static File authsfile, moneyFile, cfile, spawnFile, homeFile, bankFile;
	public static ItemMeta goldenAppleMeta;
	public static ItemStack goldenApple;
	public static AutoSmelt autoSmelt = new AutoSmelt(555);

	public static HashMap<String, Location> back = new HashMap<String, Location>();

	@Override
	public void onEnable() {

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin Enabled!");

        config = getConfig();
        config.options().copyDefaults(true);
        cfile = new File(getDataFolder(), "config.yml");
        saveDefaultConfig();

        config = YamlConfiguration.loadConfiguration(cfile);

        //--------------------------------------------------------//

		spawnFile = new File(getDataFolder(), "spawn.yml");

		if(!spawnFile.exists()){
			try {
				spawnFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		spawn = YamlConfiguration.loadConfiguration(spawnFile);

		CacPlugin.saveSpawn();

		//--------------------------------------------------------//

		bankFile = new File(getDataFolder(), "bank.yml");

		if(!bankFile.exists()){
			try {
				bankFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		bank = YamlConfiguration.loadConfiguration(bankFile);
		if(bankFile.length() == 0D){
			bank.set("interests.grade.basic", 10);
			bank.set("interests.grade.vip", 7.5);
			bank.set("interests.grade.vipplus", 5);
			bank.set("interests.grade.heroe", 2.5);
			bank.set("interests.interval", 1);
		}

		CacPlugin.saveBank();

		//--------------------------------------------------------//

		authsfile = new File(getDataFolder(), "auths.yml");

        if(!authsfile.exists()){
			try {
				authsfile.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		auths = YamlConfiguration.loadConfiguration(authsfile);

		CacPlugin.saveAuths();

        //--------------------------------------------------------//

		moneyFile = new File(getDataFolder(), "money.yml");

		if(!moneyFile.exists()){
			try {
				moneyFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		money = YamlConfiguration.loadConfiguration(moneyFile);

		CacPlugin.saveMoney();

		//--------------------------------------------------------//

		homeFile = new File(getDataFolder(), "home.yml");
		if(!homeFile.exists()){
			try {
				homeFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		home = YamlConfiguration.loadConfiguration(homeFile);

		CacPlugin.saveHome();

        //--------------------------------------------------------//

        this.loadEnchantments();
        this.registerAll();
        this.craftsRegister();
	}

	private void databaseConnection(){
        try {
            Class.forName("java.sql.Driver");
            final String dbusername = config.getString("dbusername");
            final String dbpassword = config.getString("dbpassword");
            final String dbip = config.getString("dbip");
            final String dbname = config.getString("dbname");
            final String dburl = "jdbc:mysql://" + dbip + "/" + dbname;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

	private void loadEnchantments(){
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Enchantment.registerEnchantment(autoSmelt);
    }

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "#####################");
	    saveDefaultConfig();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "#Config File Saved !#");
		try {
			auths.save(authsfile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "#Auths File Saved ! #");
			money.save(moneyFile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "#Money File Saved ! #");
            spawn.save(spawnFile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "#Spawn File Saved ! #");
			home.save(homeFile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "#Home File Saved !  #");
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
            Field byIdField = Enchantment.class.getDeclaredField("byId");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byIdField.setAccessible(true);
            byNameField.setAccessible(true);

            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
            HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);

            if(byId.containsKey(autoSmelt.getId())){
                byId.remove(autoSmelt.getId());
            }
            if(byName.containsKey(autoSmelt.getName())){
                byName.remove(autoSmelt.getName());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "#####################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin Disabled!");
	}

    private void craftsRegister(){

        goldenApple = new ItemStack(Material.GOLDEN_APPLE);
		goldenAppleMeta = goldenApple.getItemMeta();
		goldenAppleMeta.setDisplayName(ChatColor.BLUE + "Diamond Apple");
		goldenAppleMeta.setLore(Arrays.asList(ChatColor.DARK_PURPLE + "Mangez cette pomme", ChatColor.DARK_PURPLE
                + "et vous obtiendrez", ChatColor.DARK_PURPLE + "des très bons effets!"));

		goldenAppleMeta.addEnchant(Enchantment.DURABILITY, 10, true);
		goldenApple.setItemMeta(goldenAppleMeta);

        ShapedRecipe diamondApple = new ShapedRecipe(goldenApple);
        diamondApple.shape("***",
                           "*!*",
                           "***");
        diamondApple.setIngredient('*', Material.DIAMOND_BLOCK);
        diamondApple.setIngredient('!', Material.GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(diamondApple);
	}

    private void registerAll(){

		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		Bukkit.getPluginManager().registerEvents(new LoginListener(this), this);
		Bukkit.getPluginManager().registerEvents(new KillerMoney(), this);
		Bukkit.getPluginManager().registerEvents(new SignListeners(), this);
		Bukkit.getPluginManager().registerEvents(new LobbyGestion(), this);
		Bukkit.getPluginManager().registerEvents(new ServerGestion(), this);
		Bukkit.getPluginManager().registerEvents(autoSmelt, this);

		CommandExecutor command1 = new Broadcast();
			getCommand("bc").setExecutor(command1);
			getCommand("clearchat").setExecutor(command1);
		CommandExecutor command2 = new Teleport();
			getCommand("tp").setExecutor(command2);
			getCommand("tph").setExecutor(command2);
			getCommand("tpall").setExecutor(command2);
			getCommand("back").setExecutor(command2);
			getCommand("randomtp").setExecutor(command2);
		CommandExecutor command3 = new Gamemode();
			getCommand("gm").setExecutor(command3);
		CommandExecutor command7 = new RegisterLogin();
			getCommand("register").setExecutor(command7);
			getCommand("login").setExecutor(command7);
			getCommand("changepass").setExecutor(command7);
			getCommand("remove").setExecutor(command7);
		CommandExecutor command8 = new Money();
			getCommand("money").setExecutor(command8);
			getCommand("pay").setExecutor(command8);
        CommandExecutor command9 = new Spawn();
            getCommand("setspawn").setExecutor(command9);
            getCommand("spawn").setExecutor(command9);
		CommandExecutor command10 = new HealFeed();
			getCommand("heal").setExecutor(command10);
			getCommand("feed").setExecutor(command10);
		CommandExecutor command12 = new Spotter();
			getCommand("spotter").setExecutor(command12);
		CommandExecutor command13 = new Alert();
			getCommand("alert").setExecutor(command13);
        CommandExecutor command14 = new WeatherTime();
            getCommand("sun").setExecutor(command14);
            getCommand("night").setExecutor(command14);
            getCommand("rain").setExecutor(command14);
            getCommand("day").setExecutor(command14);
            getCommand("midday").setExecutor(command14);
            getCommand("thunder").setExecutor(command14);
		CommandExecutor command15 = new Home();
			getCommand("home").setExecutor(command15);
			getCommand("sethome").setExecutor(command15);
			getCommand("delhome").setExecutor(command15);
		CommandExecutor command16 = new Msg();
			getCommand("msg").setExecutor(command16);
		CommandExecutor command17 = new GetPos();
			getCommand("getpos").setExecutor(command17);
		CommandExecutor command18 = new Invsee();
			getCommand("invsee").setExecutor(command18);
        CommandExecutor command19 = new PotionEffectToPlayer();
            getCommand("popo").setExecutor(command19);
        CommandExecutor command20 = new Furnace();
            getCommand("furnace").setExecutor(command20);
	}

	public static void saveAuths(){
		try {
			auths.save(authsfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveMoney() {
		try {
			money.save(moneyFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveSpawn() {
		try {
			spawn.save(spawnFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveHome() {
		try {
			home.save(homeFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveBank() {
		try{
			bank.save(bankFile);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}