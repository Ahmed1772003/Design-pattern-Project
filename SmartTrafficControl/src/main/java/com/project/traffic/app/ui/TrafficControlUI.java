package com.project.traffic.app.ui;

import com.project.traffic.core.SimulationEngine;
import com.project.traffic.core.TrafficControlCenter;
import com.project.traffic.core.model.VehicleEvent;
import com.project.traffic.core.model.Violation;
import com.project.traffic.core.scenario.TrafficScenario;
import com.project.traffic.factory.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrafficControlUI extends JFrame {

    // ----- Icons -----
    private ImageIcon loadIcon(String name) {
        return loadIconScaled(name, 18);
    }

    private ImageIcon loadIconScaled(String name, int size) {
        java.net.URL url = getClass().getClassLoader().getResource("icons/" + name);
        if (url == null) return null;
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // deprecated
    private ImageIcon loadIconOld(String name) {

        java.net.URL url = getClass().getClassLoader().getResource("icons/" + name);
        if (url == null) return null;
        ImageIcon icon = new ImageIcon(url);
        // scale to 18x18 for toolbar buttons
        Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private final JComboBox<String> intersectionType = new JComboBox<>(new String[]{"Normal", "Highway", "SchoolZone"});
    private final JComboBox<String> scenarioBox = new JComboBox<>(new String[]{"Normal", "Rush Hour", "Accident Mode", "Emergency Mode"});
    private final JButton initBtn = new JButton("Initialize");
    private final JButton tickBtn = new JButton("Next Tick");
    private final JButton autoRunBtn = new JButton("Auto Run");
    private final JButton addVehicleBtn = new JButton("Add Vehicle");
    private final JButton clearBtn = new JButton("Clear Violations");
    private final JButton toggleThemeBtn = new JButton("Theme");

    private final JLabel statusLeft = new JLabel("Ready.");
    private final JLabel statusState = new JLabel("STATE: -");
    private final JLabel statusTick = new JLabel("TICK: -");
    private final JLabel statusLoad = new JLabel("LOAD: -");
    private final JLabel statusScenario = new JLabel("Scenario: -");
    private final JLabel statusViolations = new JLabel("Violations: 0");
    private final JLabel stateIconLabel = new JLabel();

    private final JLabel lastViolationLabel = new JLabel("Last: -");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Time", "Plate", "Type", "Fine"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };

    private SimulationEngine engine;
    private Timer autoTimer;
    private boolean autoRunning = false;

    public TrafficControlUI() {
        super("Smart Traffic Control & Violation Management");

        setButtonIcons();
        setAppIcon();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 580);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        add(buildToolbar(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);

        wireActions();
        setInitialState();
        getRootPane().setDefaultButton(initBtn);
        setVisible(true);
    }

    private JPanel buildToolbar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 6, 5, 6);
        c.gridy = 0;

        c.gridx = 0; c.anchor = GridBagConstraints.LINE_START;
        p.add(new JLabel("Intersection:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.25;
        p.add(intersectionType, c);

        c.gridx = 2; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        p.add(new JLabel("Scenario:"), c);
        c.gridx = 3; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.25;
        p.add(scenarioBox, c);

        c.gridx = 4; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        p.add(initBtn, c);
        c.gridx = 5; p.add(tickBtn, c);
        c.gridx = 6; p.add(autoRunBtn, c);
        c.gridx = 7; p.add(addVehicleBtn, c);
        c.gridx = 8; p.add(clearBtn, c);
        c.gridx = 9; p.add(toggleThemeBtn, c);

        return p;
    }

    
private JComponent buildTable() {
        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD, 13f));

        
// Color rows by violation type (adaptive for Dark/Light)
table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);

        boolean isDark = UIManager.getLookAndFeel().getName().toLowerCase().contains("dark");

        if (!isSelected) {
            String type = String.valueOf(t.getValueAt(row, 2));

            Color bg;
            Color fg;

            if (type.contains("RED_LIGHT") && type.contains("SPEEDING")) {
                bg = isDark ? new Color(90, 50, 120) : new Color(235, 225, 245);
            } else if (type.contains("RED_LIGHT")) {
                bg = isDark ? new Color(120, 45, 45) : new Color(250, 225, 225);
            } else if (type.contains("SPEEDING")) {
                bg = isDark ? new Color(45, 85, 120) : new Color(220, 235, 250);
            } else {
                bg = t.getBackground();
            }

            fg = isDark ? new Color(235, 240, 245) : new Color(25, 25, 25);

            c.setBackground(bg);
            c.setForeground(fg);
        }
        return c;
    }
});

        JScrollPane sp = new JScrollPane(table);

        // Header panel with title + last violation badge
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Violations Log");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 13f));

        styleBadge(lastViolationLabel);
        lastViolationLabel.setText("Last: -");
        lastViolationLabel.setForeground(new Color(220, 230, 240));

        header.add(title, BorderLayout.WEST);
        header.add(lastViolationLabel, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout(8, 8));
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(sp, BorderLayout.CENTER);

        return wrapper;
    }

    
private JPanel buildStatusBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(6, 2, 2, 2));

        // Left status text
        p.add(statusLeft, BorderLayout.WEST);

        // Right status badges
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        styleBadge(statusState);
        styleBadge(statusTick);
        styleBadge(statusLoad);
        styleBadge(statusScenario);
        styleBadge(statusViolations);

        right.add(stateIconLabel);
        right.add(statusState);
        right.add(statusTick);
        right.add(statusLoad);
        right.add(statusScenario);
        right.add(statusViolations);

        p.add(right, BorderLayout.EAST);
        return p;
    }

    private void wireActions() {
        initBtn.addActionListener(e -> initEngine());
        tickBtn.addActionListener(e -> onTick());
        clearBtn.addActionListener(e -> {
            stopAutoRunIfNeeded();
            TrafficControlCenter.getInstance().clearViolations();
            refreshTable();
            setStatus("Violations cleared.");
        });

        autoRunBtn.addActionListener(e -> toggleAutoRun());
        addVehicleBtn.addActionListener(e -> openAddVehicleDialog());
        toggleThemeBtn.addActionListener(e -> toggleTheme());

        scenarioBox.addActionListener(e -> {
            if (engine != null) {
                engine.applyScenario(getScenarioFromUI());
                updateStatusRight();
                setStatus("Scenario applied: " + engine.getScenario().name);
            }
        });
    }

    private void setInitialState() {
        tickBtn.setEnabled(false);
        autoRunBtn.setEnabled(false);
        addVehicleBtn.setEnabled(false);
    }

    private void setStatus(String msg) {
        statusLeft.setText(msg);
    }

    private void styleBadge(JLabel label) {
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 80, 95), 1, true),
                BorderFactory.createEmptyBorder(3, 10, 3, 10)
        ));
        label.setOpaque(false);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 12f));
        label.setForeground(new Color(220, 230, 240));
    }

        private void applyLastViolationStyle(String type) {
        if (type == null) type = "";
        boolean isDark = UIManager.getLookAndFeel().getName().toLowerCase().contains("dark");
        if (type.contains("RED_LIGHT") && type.contains("SPEEDING")) {
            lastViolationLabel.setForeground(isDark ? new Color(210, 170, 255) : new Color(120, 60, 170));
        } else if (type.contains("RED_LIGHT")) {
            lastViolationLabel.setForeground(isDark ? new Color(255, 140, 140) : new Color(170, 60, 60));
        } else if (type.contains("SPEEDING")) {
            lastViolationLabel.setForeground(isDark ? new Color(140, 190, 255) : new Color(35, 90, 150));
        } else {
            lastViolationLabel.setForeground(isDark ? new Color(220, 230, 240) : new Color(40, 40, 40));
        }
    }


private void applyStateColor(String state) {
        switch (state) {
            case "RED" -> {
                statusState.setForeground(new Color(255, 90, 90));
                ImageIcon ic = loadIconScaled("state_red.png", 20);
                if (ic != null) stateIconLabel.setIcon(ic);
            }
            case "YELLOW" -> {
                statusState.setForeground(new Color(255, 210, 90));
                ImageIcon ic = loadIconScaled("state_yellow.png", 20);
                if (ic != null) stateIconLabel.setIcon(ic);
            }
            case "GREEN" -> {
                statusState.setForeground(new Color(90, 220, 140));
                ImageIcon ic = loadIconScaled("state_green.png", 20);
                if (ic != null) stateIconLabel.setIcon(ic);
            }
            default -> {
                statusState.setForeground(new Color(220, 230, 240));
                stateIconLabel.setIcon(null);
            }
        }
    }

    
    private void updateStatusRight() {
        if (engine == null) {
            statusState.setText("STATE: -");
            statusTick.setText("TICK: -");
            statusLoad.setText("LOAD: -");
            statusScenario.setText("Scenario: -");
            statusViolations.setText("Violations: 0");
            applyStateColor("-");
            return;
        }

        String state = engine.getLight().getState().toString();
        statusState.setText("STATE: " + state);
        statusTick.setText("TICK: " + engine.getLight().getTick());
        statusLoad.setText("LOAD: " + engine.getTrafficLoad());
        statusScenario.setText("Scenario: " + engine.getScenario().name);
        int vCount = TrafficControlCenter.getInstance().getViolationsSnapshot().size();
        statusViolations.setText("Violations: " + vCount);
        boolean isDark = UIManager.getLookAndFeel().getName().toLowerCase().contains("dark");
        if (vCount == 0) statusViolations.setForeground(isDark ? new Color(120, 220, 160) : new Color(40, 140, 90));
        else statusViolations.setForeground(isDark ? new Color(255, 120, 120) : new Color(180, 60, 60));

        applyStateColor(state);
    }

    private void initEngine() {
        stopAutoRunIfNeeded();

        IntersectionFactory factory = switch (intersectionType.getSelectedItem().toString()) {
            case "Highway" -> new HighwayIntersectionFactory();
            case "SchoolZone" -> new SchoolZoneIntersectionFactory();
            default -> new NormalIntersectionFactory();
        };

        TrafficControlCenter.getInstance().clearViolations();
        engine = new SimulationEngine(factory.createTrafficLight(), factory.createViolationChain());
        engine.applyScenario(getScenarioFromUI());

        tickBtn.setEnabled(true);
        autoRunBtn.setEnabled(true);
        addVehicleBtn.setEnabled(true);

        refreshTable();
        updateStatusRight();
        setStatus("Initialized: " + factory.getName() + " | Speed Limit: " + factory.getSpeedLimitKmh() + " km/h");
    }

    private void onTick() {
        if (engine == null) return;
        engine.tick();
        updateStatusRight();
        refreshTable();
    }

    private void toggleAutoRun() {
        if (engine == null) return;

        if (!autoRunning) {
            autoTimer = new Timer(1000, e -> onTick());
            autoTimer.start();
            autoRunning = true;
            autoRunBtn.setText("Stop Auto");
            ImageIcon stopI = loadIcon("stop.png");
            if (stopI != null) autoRunBtn.setIcon(stopI);
            setStatus("Auto Run started (1 tick / second).");
        } else {
            stopAutoRunIfNeeded();
            setStatus("Auto Run stopped.");
        }
    }

    private void stopAutoRunIfNeeded() {
        if (autoTimer != null) autoTimer.stop();
        autoTimer = null;
        autoRunning = false;
        autoRunBtn.setText("Auto Run");
        ImageIcon autoI = loadIcon("autorun.png");
        if (autoI != null) autoRunBtn.setIcon(autoI);
    }

    private TrafficScenario getScenarioFromUI() {
        return switch (scenarioBox.getSelectedItem().toString()) {
            case "Rush Hour" -> TrafficScenario.rushHour();
            case "Accident Mode" -> TrafficScenario.accident();
            case "Emergency Mode" -> TrafficScenario.emergency();
            default -> TrafficScenario.normal();
        };
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        for (Violation v : TrafficControlCenter.getInstance().getViolationsSnapshot()) {
            tableModel.addRow(new Object[]{fmt.format(new Date(v.timestamp)), v.plate, v.type, v.fine});
        }
        // keep badges in sync
        updateStatusRight();
    
        // last violation badge
        java.util.List<Violation> list = TrafficControlCenter.getInstance().getViolationsSnapshot();
        if (list.isEmpty()) {
            lastViolationLabel.setText("Last: -");
            applyLastViolationStyle("");
        } else {
            Violation last = list.get(list.size() - 1);
            lastViolationLabel.setText("Last: " + last.plate + " • " + last.type + " • " + last.fine);
            applyLastViolationStyle(last.type);
        }
    }

    private void openAddVehicleDialog() {
        if (engine == null) return;

        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField plateField = new JTextField("EG-9999");
        JSpinner speedSpinner = new JSpinner(new SpinnerNumberModel(60, 0, 240, 1));
        JCheckBox crossedBox = new JCheckBox("Crossed Stop Line");
        JCheckBox emergencyBox = new JCheckBox("Emergency Vehicle");

        panel.add(new JLabel("Plate:"));
        panel.add(plateField);
        panel.add(new JLabel("Speed (km/h):"));
        panel.add(speedSpinner);
        panel.add(new JLabel(" "));
        panel.add(crossedBox);
        panel.add(new JLabel(" "));
        panel.add(emergencyBox);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Vehicle (Manual Event)",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String plate = plateField.getText().trim();
            if (plate.isEmpty()) plate = "EG-0000";
            double speed = ((Number) speedSpinner.getValue()).doubleValue();

            VehicleEvent ev = new VehicleEvent(plate, speed, crossedBox.isSelected(), emergencyBox.isSelected());
            engine.processVehicleEvent(ev);

            refreshTable();
            updateStatusRight();
            setStatus("Vehicle processed: " + plate + " | speed=" + speed + " | crossed=" + crossedBox.isSelected());
        }
    }


    private void setButtonIcons() {
        ImageIcon initI = loadIcon("initialize.png");
        ImageIcon tickI = loadIcon("tick.png");
        ImageIcon autoI = loadIcon("autorun.png");
        ImageIcon vehI = loadIcon("vehicle.png");
        ImageIcon clrI = loadIcon("clear.png");

        if (initI != null) initBtn.setIcon(initI);
        if (tickI != null) tickBtn.setIcon(tickI);
        if (autoI != null) autoRunBtn.setIcon(autoI);
        if (vehI != null) addVehicleBtn.setIcon(vehI);
        if (clrI != null) clearBtn.setIcon(clrI);

        // Theme icon
        boolean isDark = UIManager.getLookAndFeel().getName().toLowerCase().contains("dark");
        ImageIcon themeI = loadIcon(isDark ? "sun.png" : "moon.png");
        if (themeI != null) toggleThemeBtn.setIcon(themeI);
        toggleThemeBtn.setToolTipText(isDark ? "Switch to Light" : "Switch to Dark");

        // Make Initialize the default "primary" action
        getRootPane().setDefaultButton(initBtn);
    }

    private void setAppIcon() {
        try {
            java.net.URL url = getClass().getClassLoader().getResource("icons/app.png");
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                setIconImage(icon.getImage());
            }
        } catch (Exception ignored) {}
    }



private void toggleTheme() {
    try {
        boolean isDark = UIManager.getLookAndFeel().getName().toLowerCase().contains("dark");
        if (isDark) {
            FlatLightLaf.setup();
        } else {
            FlatDarkLaf.setup();
        }
        FlatLaf.updateUI();
        SwingUtilities.updateComponentTreeUI(this);
        // refresh icons scaling / badges after LAF update
        setButtonIcons();
        updateStatusRight();
        repaint();
        setStatus("Theme changed: " + UIManager.getLookAndFeel().getName());
    } catch (Exception ex) {
        setStatus("Theme toggle failed: " + ex.getMessage());
    }
}
}
