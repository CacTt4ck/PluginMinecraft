package fr.cactt4ck.cacplugin.frame;

import org.bukkit.Bukkit;

import javax.swing.*;

public class PluginFrame extends JFrame {

    public PluginFrame(){
        super("Plugin Frame");
        this.init();
    }

    private void init(){
        this.setVisible(true);
        this.setSize(1280,720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(new PluginPanel());
    }

}

class PluginPanel extends JPanel {

    public PluginPanel(){
        this.init();
    }

    private void init(){

    }















}
