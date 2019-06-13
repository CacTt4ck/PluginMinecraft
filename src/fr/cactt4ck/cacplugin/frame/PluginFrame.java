package fr.cactt4ck.cacplugin.frame;

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
        this.setContentPane(PluginPanel.instance);
    }

}

class PluginPanel extends JPanel {

    public static PluginPanel instance = null;

    public PluginPanel(){
        if(instance == null)
            instance = new PluginPanel();
        this.init();
    }

    private void init(){

    }

}
