package fr.cactt4ck.cacplugin.frame;

import fr.cactt4ck.cacplugin.CacPlugin;
import fr.cactt4ck.cacplugin.Listeners;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

public class PluginFrame extends JFrame {

    public PluginFrame(){
        super("Plugin Frame");
        this.init();
    }

    private void init(){
        this.setUndecorated(true);
        this.setSize(1280,720);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setContentPane(new PluginPanel());
        this.setVisible(true);
    }

}

class PluginPanel extends JPanel {

    private JLabel title, enterCommand;
    private JButton sendCommandButton, playerButton;
    private JTextField commandField;
    private JPanel textFieldPanel, playerFramePanel;

    public PluginPanel(){
        super(new BorderLayout());
        this.init();
    }

    private void init(){
        this.title();
        this.commandField();
        this.playerButton();
    }

    private void playerButton() {
        playerButton = new JButton("Open Player Control");
        playerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new PlayerFrame());
        });
        playerFramePanel = new JPanel();
        playerFramePanel.setLayout(new GridLayout(10,1));
        playerFramePanel.add(playerButton);
        this.add(playerFramePanel, BorderLayout.WEST);
    }

    private void title(){
        title = new JLabel("Serveur");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Font.MONOSPACED,Font.BOLD,40));
        title.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK));
        this.add(title, BorderLayout.NORTH);
    }

    private void commandField(){
        commandField = new JTextField();
        commandField.addActionListener(e -> this.sendCommandAction());
        commandField.setPreferredSize(new Dimension(200,20));

        enterCommand = new JLabel("Entrez une commande ici");
        enterCommand.setFont(new Font("Monaco", Font.PLAIN, 20));

        sendCommandButton = new JButton("Send Command!");
        sendCommandButton.addActionListener( e -> this.sendCommandAction());

        textFieldPanel = new JPanel();
        textFieldPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,10));
        textFieldPanel.setLayout(new GridLayout(20,1));
        textFieldPanel.add(enterCommand);
        textFieldPanel.add(commandField);
        textFieldPanel.add(sendCommandButton);
        this.add(textFieldPanel, BorderLayout.EAST);
    }

    //-----------------------------------------------------------------------//

    private void sendCommandAction(){
        CacPlugin.scheduler.callSyncMethod(CacPlugin.getPlugin(), () -> CacPlugin.consoleCommandSender.getServer().dispatchCommand(CacPlugin.consoleCommandSender, commandField.getText()));
    }

}























class PlayerFrame extends JDialog{

    public PlayerFrame(){
        super();
        this.init();
    }

    private void init() {
        this.setModal(true);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Player control");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(new PlayerPanel());
        this.setVisible(true);
    }

}

class PlayerPanel extends JPanel {

    private Image playerIcon;

    public PlayerPanel() {
        this.setLayout(null);
        Listeners.addOnJoinListener(p -> this.refresh());
        Listeners.addOnQuitListener(p -> this.refresh());
        this.init();
    }

    private void refresh() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.removeAll();
            this.init();
            this.revalidate();
            this.repaint();
        }).start();
    }

    private void init() {
        Collection<? extends Player> players = CacPlugin.getPlugin().getServer().getOnlinePlayers();
        int i = 0;
        for (Player p : players) {
            final JLabel pLabel = new JLabel();
            pLabel.setSize(64,64);
            pLabel.setLocation(((i)%12*64)+10*(i+1), ((i)/12*64)+10*((i/12)+1));
            try {
                pLabel.setIcon(new ImageIcon(ImageIO.read(new URL("https://minotar.net/avatar/" + p.getName() + ".png")).getScaledInstance(64,64,0)));
            } catch (IOException e) {e.printStackTrace();}
            JPopupMenu menu = this.getPlayerMenu(p);
            JPopupMenu playerName = this.getPlayerName(p);
            pLabel.setComponentPopupMenu(menu);
            pLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(e.getButton() == MouseEvent.BUTTON1){
                        menu.show(pLabel, e.getX(), e.getY());
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    playerName.show(pLabel, e.getX(), e.getY());
                }
            });
            this.add(pLabel);
            i++;
        }
    }

    private JPopupMenu getPlayerName(Player p){
        JPopupMenu menu = new JPopupMenu();
        JMenuItem name = new JMenuItem(p.getName());
        name.setEnabled(false);
        menu.add(name);
        menu.addSeparator();

        return menu;
    }

    private JPopupMenu getPlayerMenu(Player p){
        JPopupMenu menu = new JPopupMenu();
        JMenuItem name = new JMenuItem(p.getName());
        name.setEnabled(false);
        menu.add(name);
        menu.addSeparator();

        JMenuItem kick = new JMenuItem("Kick");
        kick.addActionListener(e -> Bukkit.getScheduler().runTask(CacPlugin.getPlugin(), () -> p.kickPlayer("Kicked by an operator!")));
        menu.add(kick);

        JMenuItem ban = new JMenuItem("Ban");
        ban.addActionListener(e -> Bukkit.getScheduler().runTask(CacPlugin.getPlugin(), () -> Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(),"You have been banned by an operator",
                new Date(Long.MAX_VALUE),null)));
        menu.add(ban);

        JMenuItem sendMessage = new JMenuItem("Send Message...");
        sendMessage.addActionListener(e -> {
            String message = JOptionPane.showInputDialog(this, "Write your message here :", "Send message to " + p.getName(), JOptionPane.QUESTION_MESSAGE);
            if(message != null && !message.trim().equals("")){
                Bukkit.getScheduler().runTask(CacPlugin.getPlugin(), () -> p.sendMessage(message));
            }
        });
        menu.add(sendMessage);

        JMenuItem op = new JMenuItem("Op");
        op.addActionListener(e -> Bukkit.getScheduler().runTask(CacPlugin.getPlugin(), () -> {
            p.setOp(true);
            p.sendMessage(ChatColor.GRAY + "You have been opped by an operator");
        }));
        menu.add(op);

        JMenuItem deop = new JMenuItem("Deop");
        deop.addActionListener(e -> Bukkit.getScheduler().runTask(CacPlugin.getPlugin(), () -> {
            p.setOp(false);
            p.sendMessage(ChatColor.GRAY + "You have been deopped by an operator");
        }));
        menu.add(deop);

        JMenuItem gamemode = new JMenuItem("Gamemode...");
        gamemode.addActionListener(e -> {
            GameMode[] gamemodes = {GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR};
            Object gm = JOptionPane.showInputDialog(this, "Choose " + p.getName() + "gamemode :", "Set gamemode of " + p.getName(),
                    JOptionPane.QUESTION_MESSAGE, null, gamemodes, p.getGameMode());
            if(gm != null){
                Bukkit.getScheduler().runTask(CacPlugin.getPlugin(), () -> p.setGameMode((GameMode) gm));
            }});
        menu.add(gamemode);

        return menu;
    }
}