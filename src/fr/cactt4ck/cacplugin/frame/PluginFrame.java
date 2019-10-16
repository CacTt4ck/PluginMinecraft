package fr.cactt4ck.cacplugin.frame;

import fr.cactt4ck.cacplugin.CacPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
        title = new JLabel("Cacserve");
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
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(new PlayerPanel());
        this.setVisible(true);
    }

}

class PlayerPanel extends JPanel {

    private JButton button;
    private Image playerIcon;

    public PlayerPanel() {
        super(new BorderLayout());
        this.init();
    }

    private void init() {
        this.button();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Player p : CacPlugin.getPlugin().getServer().getOnlinePlayers()) {
            try {
                playerIcon = ImageIO.read(new URL("https://minotar.net/avatar/" + p.getName() + ".png")).getScaledInstance(64,64,0);
                g.drawImage(playerIcon, 100,100, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        repaint();
    }

    private void button(){
        button = new JButton("Test");
        button.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(button, BorderLayout.NORTH);
    }

}