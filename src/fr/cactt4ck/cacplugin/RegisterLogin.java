package fr.cactt4ck.cacplugin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static fr.cactt4ck.cacplugin.LoginListener.spawnLocation;

public class RegisterLogin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final Player p = (Player)sender;

        // Si la commande entrée est /register
        if (cmd.getName().equalsIgnoreCase("register")) {

            // Si le joueur s'est déjà authentifié
            if (LoginListener.isPlayerAuthenticated(p)) {
                p.sendMessage(ChatColor.RED + "Vous êtes déjà connecté au serveur !");
                return true;
            }

            // Si le joueur est déjà enregistré sur le serveur alors qu'il fait /register
            if (CacPlugin.auths.getConfigurationSection(p.getUniqueId().toString()) != null) {
                p.sendMessage(ChatColor.RED + "Erreur ! Vous êtes déjà enregistré sur le serveur !");
                p.sendMessage(ChatColor.RED + "Faites /login <mdp> si vous voulez vous authentifier.");
                p.sendMessage(ChatColor.RED + "Ou bien faites /changepass <mdp> <mdp> si vous voulez changer votre mot de passe.");
                return true;
            }

            // Si le joueur a entré deux mots de passe identiques comme demandé
            if (args.length == 2 && args[0].equals(args[1])) {
                final String salt = this.generateSalt();
                CacPlugin.auths.set(p.getUniqueId().toString() + ".hash", this.hashPassword(args[0], salt));
                CacPlugin.auths.set(p.getUniqueId().toString() + ".salt", salt);
                CacPlugin.saveAuths();
                p.sendMessage(ChatColor.GOLD + "Vous avez bien été enregistré ! Bon jeu !");
                spawnLocation.remove(p, p.getLocation());
                LoginListener.doPlayerAuthenticated(p);
                return true;
                // Si le joueur a entré deux mots de passe différents ou un nombre incorrect d'arguments
            } else
                return false;

            // Si la commande entrée est /login
        } else if (cmd.getName().equalsIgnoreCase("login")) {

            // Si le joueur s'est déjà authentifié
            if (LoginListener.isPlayerAuthenticated(p)) {
                p.sendMessage(ChatColor.RED + "Vous êtes déjà connecté au serveur !");
                return true;
            }

            // Si le joueur n'est pas encore enregistré sur le serveur alors qu'il fait /login
            if (CacPlugin.auths.getConfigurationSection(p.getUniqueId().toString()) == null) {
                p.sendMessage(ChatColor.RED + "Erreur ! Vous n'êtes pas encore enregistré sur le serveur !");
                p.sendMessage(ChatColor.RED + "Faites /register <mdp> <mdp> pour vous enregistrer.");
                return true;
            }

            // Si le joueur a entré un mot de passe comme demandé
            if (args.length == 1) {
                // Si le mot de passe est correct
                if (this.hashPassword(args[0], CacPlugin.auths.getString(p.getUniqueId().toString() + ".salt")).equals(CacPlugin.auths.get(p.getUniqueId().toString() + ".hash"))) {
                    p.sendMessage(ChatColor.GOLD + "Vous avez bien été authentifié !");
                    spawnLocation.remove(p);
                    LoginListener.doPlayerAuthenticated(p);
                    return true;
                    // Si le mot de passe est incorrect
                } else {
                    p.sendMessage(ChatColor.RED + "Mauvais mot de passe ! Veuillez réessayer !");
                    return true;
                }
                // Si le joueur a entré un nombre incorrect d'arguments
            } else
                return false;

        } else if (cmd.getName().equalsIgnoreCase("changepass")) {

            // Si le joueur ne s'est pas encore authentifié
            if (!LoginListener.isPlayerAuthenticated(p)) {
                p.sendMessage(ChatColor.RED + "Veuillez vous authentifier avant de pouvoir changer votre mot de passe !");
                return true;
            }

            // Si le joueur n'est pas encore enregistré sur le serveur alors qu'il fait /changemdp
            if (CacPlugin.auths.getConfigurationSection(p.getUniqueId().toString()) == null) {
                p.sendMessage(ChatColor.RED + "Erreur ! Vous n'êtes pas encore enregistré sur le serveur !");
                p.sendMessage(ChatColor.RED + "Faites /register <mdp> <mdp> pour vous enregistrer.");
                return true;
            }

            // Si le joueur a entré deux mots de passe identiques comme demandé
            if (args.length == 2 && args[0].equals(args[1])) {
                final String salt = this.generateSalt();
                CacPlugin.auths.set(p.getUniqueId().toString() + ".hash", this.hashPassword(args[0], salt));
                CacPlugin.auths.set(p.getUniqueId().toString() + ".salt", salt);
                CacPlugin.saveAuths();
                p.sendMessage(ChatColor.GOLD + "Votre mot de passe a bien été changé !");
                return true;
                // Si le joueur a entré deux mots de passe différents ou un nombre incorrect d'arguments
            } else
                return false;

        }else if(cmd.getName().equalsIgnoreCase("remove")){
            if(args.length == 2){
                Player player = Bukkit.getServer().getPlayer(args[1]);
                String uuid = player.getUniqueId().toString();
                if(args[0].equalsIgnoreCase("player")){
                    CacPlugin.auths.set(uuid, "");
                    p.sendMessage("Resetting " + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RESET + " account...");
                    CacPlugin.saveAuths();
                    p.sendMessage("Account Reset!");
                    return true;
                }else{
                    p.sendMessage(ChatColor.RED + "Erreur! Faites /remove player <player>");
                    return true;
                }
            }else{
                p.sendMessage(ChatColor.RED + "Erreur! Faites /remove player <player>");
                return true;
            }
        }

        // Si le joueur n'a exécuté ni '/login' ni '/register' ni '/changemdp'
        return false;
    }





    private static final int HASH_SIZE = 32; // 32 bytes = 128 bits

    private String hashPassword(final String password, final String salt) {
        final byte[] passwordBytes = password.getBytes(),
                saltBytes = Base64.getUrlDecoder().decode(salt);

        final byte[] saltedPasswordBytes = new byte[HASH_SIZE];

        for (int i = 0, n = 0; i < saltedPasswordBytes.length; i++, n++) {
            if (n == passwordBytes.length)
                n = 0;
            saltedPasswordBytes[i] = (byte) (passwordBytes[n] ^ saltBytes[i]);
        }

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final byte[] finalBytes = digest.digest(saltedPasswordBytes);

        return Base64.getUrlEncoder().encodeToString(finalBytes);
    }


    private String generateSalt() {
        SecureRandom sRandom = new SecureRandom();
        final byte[] saltBytes = new byte[HASH_SIZE];
        sRandom.nextBytes(saltBytes);

        return Base64.getUrlEncoder().encodeToString(saltBytes);
    }

}
