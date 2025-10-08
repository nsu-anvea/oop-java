package ru.nsu.ccfit.veretennikov.factory.ui.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

import ru.nsu.ccfit.veretennikov.factory.*;
import ru.nsu.ccfit.veretennikov.factory.details.Accessory;
import ru.nsu.ccfit.veretennikov.factory.details.Auto;
import ru.nsu.ccfit.veretennikov.factory.details.Body;
import ru.nsu.ccfit.veretennikov.factory.details.Motor;
import ru.nsu.ccfit.veretennikov.factory.storage.Storage;

public class FactoryGUI extends JFrame {
    private final Factory factory;

    private JSlider bodySupplierSlider;
    private JSlider motorSupplierSlider;
    private JSlider accessorySupplierSlider;
    private JSlider dealerSlider;

    private JLabel bodyStorageLabel;
    private JLabel motorStorageLabel;
    private JLabel accessoryStorageLabel;
    private JLabel autoStorageLabel;
    private JLabel totalSoldCount;
    private JLabel totalProducedLabel;
    private JLabel tasksInQueueLabel;

    public FactoryGUI(Factory factory) {
        this.factory = factory;
        init();
    }

    private void init() {
        setTitle("Car Factory Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLayout(new BorderLayout(10, 10));

        JPanel controlPanel = createControlPanel();
        JPanel infoPanel = createInfoPanel();

        add(controlPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        Timer timer = new Timer(10, e -> update());
        timer.start();
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(createSupplierPanel());
        panel.add(createDealerPanel());
        panel.add(createStoragePanel());
        panel.add(createWorkerPanel());

        return panel;
    }

    private JPanel createSupplierPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Suppliers Control"));

        JPanel sliderPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        bodySupplierSlider = createSlider(
                1000, 10000, 1000, "Body Supplier Delay (ms)"
        );
        sliderPanel.add(bodySupplierSlider);

        motorSupplierSlider = createSlider(
                1000, 10000, 1000, "Motor Supplier Delay (ms)"
        );
        sliderPanel.add(motorSupplierSlider);

        accessorySupplierSlider = createSlider(
                1000, 10000, 1000, "Accessory Supplier Delay (ms)"
        );
        sliderPanel.add(accessorySupplierSlider);

        panel.add(sliderPanel, BorderLayout.CENTER);

        bodySupplierSlider.addChangeListener(e -> {
            int delay = bodySupplierSlider.getValue();
            factory.getBodySuppliers().forEach(s -> s.setDelay(delay));
        });

        motorSupplierSlider.addChangeListener(e -> {
            int delay = motorSupplierSlider.getValue();
            factory.getMotorSuppliers().forEach(s -> s.setDelay(delay));
        });

        accessorySupplierSlider.addChangeListener(e -> {
            int delay = accessorySupplierSlider.getValue();
            factory.getAccessorySuppliers().forEach(s -> s.setDelay(delay));
        });

        return panel;
    }

    private JSlider createSlider(int min, int max, int init, String title) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
        slider.setMajorTickSpacing(1000);
        slider.setMinorTickSpacing(500);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(title));
        return slider;
    }

    private JPanel createDealerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Dealers Control"));

        dealerSlider = createSlider(
                1000, 10000, 1000, "Dealer Request Delay (ms)"
        );

        dealerSlider.addChangeListener(e -> {
            int delay = dealerSlider.getValue();
            factory.getDealers().forEach(d -> d.setDelay(delay));
        });

        panel.add(dealerSlider, BorderLayout.CENTER);

        totalSoldCount = new JLabel("Total Sold Count: 0");
        panel.add(totalSoldCount, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStoragePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(new TitledBorder("Storage Status"));

        bodyStorageLabel = createStorageLabel("Body Storage: 0/0 (0 produced)");
        motorStorageLabel = createStorageLabel("Motor Storage: 0/0 (0 produced)");
        accessoryStorageLabel = createStorageLabel("Accessory Storage: 0/0 (0 produced)");
        autoStorageLabel = createStorageLabel("Auto Storage: 0/0 (0 produced)");

        panel.add(bodyStorageLabel);
        panel.add(motorStorageLabel);
        panel.add(accessoryStorageLabel);
        panel.add(autoStorageLabel);

        return panel;
    }

    private JLabel createStorageLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }

    private JPanel createWorkerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Workers Status"));

        tasksInQueueLabel = new JLabel("Tasks in queue: 0");
        tasksInQueueLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        totalProducedLabel = new JLabel("Total cars produced: 0");
        totalProducedLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(totalProducedLabel);
        infoPanel.add(tasksInQueueLabel);

        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEtchedBorder());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            factory.stop();
            System.exit(0);
        });

        panel.add(exitButton);

        return panel;
    }

    private void update() {
        updateStorageInfo();
        updateDealerInfo();
        updateWorkerInfo();
    }

    private void updateStorageInfo() {
        Storage<Body> bodyStorage = factory.getBodyStorage();
        bodyStorageLabel.setText(String.format(
                "Body Storage: %d/%d (%d produced)",
                bodyStorage.getSize(),
                bodyStorage.getCapacity(),
                bodyStorage.getProducedCount()
        ));

        Storage<Motor> motorStorage = factory.getMotorStorage();
        motorStorageLabel.setText(String.format(
                "Motor Storage: %d/%d (%d produced)",
                motorStorage.getSize(),
                motorStorage.getCapacity(),
                motorStorage.getProducedCount()
        ));

        Storage<Accessory> accessoryStorage = factory.getAccessoryStorage();
        accessoryStorageLabel.setText(String.format(
                "Accessory Storage: %d/%d (%d produced)",
                accessoryStorage.getSize(),
                accessoryStorage.getCapacity(),
                accessoryStorage.getProducedCount()
        ));

        Storage<Auto> autoStorage = factory.getAutoStorage();
        autoStorageLabel.setText(String.format(
                "Auto Storage: %d/%d (%d produced)",
                autoStorage.getSize(),
                autoStorage.getCapacity(),
                autoStorage.getProducedCount()
        ));
    }

    private void updateDealerInfo() {
        totalSoldCount.setText(String.format(
                "Total Sold Count: %d",
                factory.getAutoStorage().getSoldCount()
        ));
    }

    private void updateWorkerInfo() {
        totalProducedLabel.setText(String.format(
                "Total cars produced: %d",
                factory.getAutoStorage().getProducedCount()
        ));

        tasksInQueueLabel.setText(String.format(
                "Tasks in queue: %d",
                factory.getThreadPool().getTaskCount()
        ));
    }
}