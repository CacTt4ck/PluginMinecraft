package fr.cactt4ck.cacplugin.frame;

import fr.cactt4ck.cacplugin.CacPlugin;
import org.bukkit.Bukkit;

import javax.swing.*;
import java.awt.*;

public class PluginFrame extends JFrame {

    public PluginFrame(){
        super("Plugin Frame");
        this.init();
    }

    private void init(){
        this.setVisible(true);
        this.setSize(1280,720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setContentPane(new PluginPanel());
    }

}

class PluginPanel extends JPanel {

    private JLabel title;
    private JButton sendCommandButton;
    private JTextArea commandField;

    public PluginPanel(){
        this.setLayout(new BorderLayout());
        this.init();
    }

    private void init(){
        this.title();
        this.sendCommand();
        this.commandField();
    }

    private void title(){
        title = new JLabel("Cacserve");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Font.MONOSPACED,Font.BOLD,40));
        title.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK));
        this.add(title, BorderLayout.NORTH);
    }

    private void sendCommand(){
        sendCommandButton = new JButton("Send Command!");
        sendCommandButton.addActionListener( e -> {
            CacPlugin.scheduler.callSyncMethod(CacPlugin.getPlugin(), () -> CacPlugin.consoleCommandSender.getServer().dispatchCommand(CacPlugin.consoleCommandSender, commandField.getText()));
        });
        this.add(sendCommandButton, BorderLayout.SOUTH);
    }

    private void commandField(){
        commandField = new JTextArea();
        this.add(commandField, BorderLayout.CENTER);
    }

}
