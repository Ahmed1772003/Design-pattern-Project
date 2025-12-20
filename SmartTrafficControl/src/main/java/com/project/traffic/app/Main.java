package com.project.traffic.app;

import com.formdev.flatlaf.FlatDarkLaf;
import com.project.traffic.app.ui.TrafficControlUI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Start with FlatLaf Dark theme
        FlatDarkLaf.setup();

        // Global modern tweaks (work for both themes)
        UIManager.put("Component.arc", 18);
        UIManager.put("Button.arc", 18);
        UIManager.put("TextComponent.arc", 18);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("Table.rowHeight", 28);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines", false);

        SwingUtilities.invokeLater(TrafficControlUI::new);
    }
}
