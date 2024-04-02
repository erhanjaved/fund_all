package ui;

import model.Charity;
import model.CharityManager;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CharityAppGUI extends JFrame implements ActionListener {

    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;

    private static final String JSON_STORE_LOCATION = "./data/charities.json";
    private static final String BACKGROUND_IMAGE_SRC = "./data/charity_bg.png";
    private static final String saveMessage = "Saved charities to " + JSON_STORE_LOCATION;
    private static final String loadMessage = "Loaded charities from " + JSON_STORE_LOCATION;
    private static final Font titleFont = new Font("Georgia", Font.BOLD, 25);

    private JFrame guiFrame;

    private JPanel welcomePanel;
    private JPanel mainMenuPanel;
    private JPanel addCharityPanel;
    private JPanel removeCharityPanel;
    private JPanel fundPanel;
    private JPanel viewCharityPanel;
    private JPanel viewReachedGoalsPanel;

    private JButton addCharityButton;
    private JButton removeCharityButton;
    private JButton viewAllButton;
    private JButton fundButton;
    private JButton viewReachedGoalsButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton enterButton;
    private JButton mainMenuButton;
    private JButton addCharityFinalButton;
    private JButton removeCharityFinalButton;
    private JButton fundCharityButton;

    private JTextField charityNameField;
    private JTextField charityGoalField;
    private JTextField charityRemoveField;
    private JTextField charityNumberField;
    private JTextField charityFundField;

    private JLabel welcomeLabel;

    private GridBagConstraints gridBagConstraints;
    private GridBagConstraints labelConstraints;
    private GridBagConstraints fieldConstraints;

    private CharityManager charityManager;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private EventLog eventLog = EventLog.getInstance();


    // Constructs GUI
    CharityAppGUI() {
        guiFrame = new JFrame("Fund All");
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(WIDTH, HEIGHT);
        guiFrame.setLayout(new BorderLayout());

        jsonWriter = new JsonWriter(JSON_STORE_LOCATION);
        jsonReader = new JsonReader(JSON_STORE_LOCATION);

        charityManager = new CharityManager();

        welcomeScreen();
        guiFrame.add(welcomePanel);
        guiFrame.setVisible(true);

        guiFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                printEventLog();
            }
        });
    }

    // EFFECTS: print eventLog
    private void printEventLog() {
        System.out.println("Event log:");
        for (Event e : eventLog) {
            System.out.println(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates welcome panel
    private void welcomeScreen() {
        welcomePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintBackgroundImage(this, g);
            }
        };

        welcomePanel.setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Welcome to Erhan's Charity App, 'Fund All'!");
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        enterButton = new JButton("Enter Main Menu");
        enterButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(enterButton);

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Creates main menu panel
    private void mainMenu() {
        guiFrame.getContentPane().removeAll();
        mainMenuPanel = createPanel("Fund All Main Menu");
        addButtonsToMenuPanel();
        addSaveLoadPanelToFrame();
        guiFrame.revalidate();
    }

    // MODIFIES: this, mainMenuPanel
    // EFFECTS: Add buttons to the main menu panel
    private void addButtonsToMenuPanel() {
        GridBagConstraints buttonConstraints = createGridBagConstraints(0, 1, 1);

        addCharityButton = createButton("Add new charity");
        removeCharityButton = createButton("Remove a charity");
        viewAllButton = createButton("View all charities");
        fundButton = createButton("Fund a charity");
        viewReachedGoalsButton = createButton("View charities with reached goals");

        mainMenuPanel.add(addCharityButton, buttonConstraints);
        buttonConstraints.gridy++;
        mainMenuPanel.add(removeCharityButton, buttonConstraints);
        buttonConstraints.gridy++;
        mainMenuPanel.add(viewAllButton, buttonConstraints);
        buttonConstraints.gridy++;
        mainMenuPanel.add(fundButton, buttonConstraints);
        buttonConstraints.gridy++;
        mainMenuPanel.add(viewReachedGoalsButton, buttonConstraints);
    }

    // MODIFIES: this
    // EFFECTS: GUI page for adding a new charity
    private void addCharityToCM() {
        guiFrame.getContentPane().removeAll();

        addCharityPanel = createPanel("Add a new charity");

        gridBagConstraints.gridy++;
        addLabelAndTextField(addCharityPanel, "Charity name:", charityNameField = new JTextField());
        addLabelAndTextField(addCharityPanel, "Funding goal ($):", charityGoalField = new JTextField());


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addCharityFinalButton = createButton("Add Charity");
        mainMenuButton = createButton("Main Menu");
        buttonPanel.add(addCharityFinalButton);
        buttonPanel.add(mainMenuButton);

        guiFrame.add(addCharityPanel, BorderLayout.CENTER);
        guiFrame.add(buttonPanel, BorderLayout.SOUTH);
        guiFrame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: GUI page for removing a charity
    private void removeCharityFromCM() {
        guiFrame.getContentPane().removeAll();

        removeCharityPanel = createPanel("Remove a charity");

        gridBagConstraints.gridy++;
        addLabelAndTextField(removeCharityPanel, "# of charity to remove:",
                charityRemoveField = new JTextField());
        charityRemoveField.setBounds(0, 0, 30, 20);

        JPanel removeButtonPanel = new JPanel();
        removeCharityFinalButton = createButton("Remove charity");
        mainMenuButton = createButton("Main Menu");
        removeButtonPanel.add(removeCharityFinalButton);
        removeButtonPanel.add(mainMenuButton);

        guiFrame.add(removeCharityPanel, BorderLayout.CENTER);
        guiFrame.add(removeButtonPanel, BorderLayout.SOUTH);
        guiFrame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: GUI page for showing all charities in charityManager
    private void viewAllCharitiesInCM() {
        guiFrame.getContentPane().removeAll();

        viewCharityPanel = createPanel("All Charities");

        gridBagConstraints.gridy++;
        if (charityManager.isEmpty()) {
            JLabel noCharities = new JLabel("There are currently no charities");
            viewCharityPanel.add(noCharities, gridBagConstraints);
        } else {
            generateTableForCharityManager(gridBagConstraints);
        }

        JPanel buttonPanel = new JPanel();
        mainMenuButton = createButton("Main Menu");
        buttonPanel.add(mainMenuButton);

        guiFrame.add(viewCharityPanel, BorderLayout.CENTER);
        guiFrame.add(buttonPanel, BorderLayout.SOUTH);
        guiFrame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Generates a table with the charities
    private void generateTableForCharityManager(GridBagConstraints gbc) {
        String[] columnNames = {"#", "Name", "Goal ($)", "Current Funds ($)"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
        JTable listings = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(listings);
        scrollPane.setBounds(10, 90, 800, 200);
        for (int i = 0; i < charityManager.getCharities().size(); i++) {
            String name = charityManager.getCharities().get(i).getName();
            double goal = charityManager.getCharities().get(i).getFundingGoal();
            double funds = charityManager.getCharities().get(i).getCurrentFunds();

            Object[] data = {i + 1, name, goal, funds};
            tableModel.addRow(data);
        }
        viewCharityPanel.add(scrollPane, gbc);
    }

    // MODIFIES: this
    // EFFECTS: GUI page for all charities that have reached their goals
    private void viewReachedGoalsInCM() {
        guiFrame.getContentPane().removeAll();

        viewReachedGoalsPanel = createPanel("Charities with reached goals");

        gridBagConstraints.gridy++;
        if (charityManager.isEmpty()) {
            JLabel noCharities = new JLabel("There are currently no charities.");
            viewReachedGoalsPanel.add(noCharities, gridBagConstraints);
        } else if (charityManager.countCharitiesWithReachedGoals() == 0) {
            JLabel noReachedGoals = new JLabel("There are currently no charities that have reached their goals");
            viewReachedGoalsPanel.add(noReachedGoals, gridBagConstraints);
        } else {
            generateReachedGoalsTableForCM(gridBagConstraints);
        }

        JPanel buttonPanel = new JPanel();
        mainMenuButton = createButton("Main Menu");
        buttonPanel.add(mainMenuButton);

        guiFrame.add(viewReachedGoalsPanel, BorderLayout.CENTER);
        guiFrame.add(buttonPanel, BorderLayout.SOUTH);
        guiFrame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Generates table of all charities that have reached their goals
    private void generateReachedGoalsTableForCM(GridBagConstraints gbc) {
        //JLabel countCharities = new JLabel("Currently, " + charityManager.countCharitiesWithReachedGoals()
        //        + " charity/charities have reached their funding goals!");
        //viewReachedGoalsPanel.add(countCharities, gridBagConstraints);
        gridBagConstraints.gridy++;
        String[] columnNames = {"#", "Name", "Goal ($)", "Current Funds ($)"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
        JTable listings = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(listings);
        scrollPane.setBounds(10, 90, 800, 200);

        int i = 0;
        for (Charity charity: charityManager.getCharities()) {
            if (charity.hasReachedGoal()) {
                i++;
                String name = charity.getName();
                double goal = charity.getFundingGoal();
                double funds = charity.getCurrentFunds();
                Object[] data = {i, name, goal, funds};
                tableModel.addRow(data);
            }
        }
        viewReachedGoalsPanel.add(scrollPane, gbc);
    }

    // EFFECTS: GUI page for funding a charity
    private void viewFundCharity() {
        guiFrame.getContentPane().removeAll();

        fundPanel = createPanel("Fund a Charity");

        gridBagConstraints.gridy++;
        addLabelAndTextField(fundPanel, "Charity #:", charityNumberField = new JTextField());
        addLabelAndTextField(fundPanel, "Amount ($):", charityFundField = new JTextField());

        JPanel fundButtonPanel = new JPanel();
        fundCharityButton = createButton("Fund charity");
        mainMenuButton = createButton("Main Menu");
        fundButtonPanel.add(fundCharityButton);
        fundButtonPanel.add(mainMenuButton);

        guiFrame.add(fundPanel, BorderLayout.CENTER);
        guiFrame.add(fundButtonPanel, BorderLayout.SOUTH);
        guiFrame.revalidate();
    }

    // EFFECTS: maps buttons to their actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCharityButton) {
            addCharityToCM();
        } else if (e.getSource() == removeCharityButton) {
            removeCharityFromCM();
        } else if (e.getSource() == viewAllButton) {
            viewAllCharitiesInCM();
        } else if (e.getSource() == fundButton) {
            viewFundCharity();
        } else if (e.getSource() == viewReachedGoalsButton) {
            viewReachedGoalsInCM();
        } else {
            actionPerformedContinuation(e);
        }
    }

    // EFFECTS: maps buttons to their actions
    public void actionPerformedContinuation(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveCharities();
        } else if (e.getSource() == loadButton) {
            loadCharities();
        } else if (e.getSource() == mainMenuButton) {
            mainMenu();
        } else if (e.getSource() == enterButton) {
            mainMenu();
        } else if (e.getSource() == addCharityFinalButton) {
            createCharity();
        } else if (e.getSource() == removeCharityFinalButton) {
            removeCharity();
        } else if (e.getSource() == fundCharityButton) {
            fundCharity();
        }
    }

    // MODIFIES: this, charityManager
    // EFFECTS: adds new charity from user to charityManager
    private void createCharity() {
        String name = charityNameField.getText();
        double goal = Double.parseDouble(charityGoalField.getText());

        Charity charityToAdd = new Charity(name, goal);
        charityManager.addCharity(charityToAdd);
        JOptionPane.showMessageDialog(null, "Charity " + name + " added successfully!");
    }

    // MODIFIES: this, charityManager
    // EFFECTS: removes charity from charityManager
    private void removeCharity() {
        int choice = Integer.parseInt(charityRemoveField.getText());

        if (charityManager.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No charities!");
        } else if (choice < 1 || choice > charityManager.getCharitiesSize()) {
            System.out.println("Invalid choice!");
            JOptionPane.showMessageDialog(null, "Invalid choice!");
        } else {
            charityManager.removeCharity(choice);
            JOptionPane.showMessageDialog(null, "Charity # " + choice + " removed successfully");
        }
    }

    // MODIFIES: this, charityManager
    // EFFECTS: funds given charity in charityManager if present
    private void fundCharity() {
        int number = Integer.parseInt(charityNumberField.getText());
        int fundsToAdd = Integer.parseInt(charityFundField.getText());

        if (charityManager.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No charities!");
        } else if (number > charityManager.getCharitiesSize()) {
            JOptionPane.showMessageDialog(null, "Invalid choice!");
        } else {
            charityManager.getCharities().get(number - 1).addFunds(fundsToAdd);
            JOptionPane.showMessageDialog(null, "Charity '"
                    + charityManager.getCharities().get(number - 1).getName() + "' successfully funded with $"
                    + fundsToAdd);
        }
    }

    // MODIFIES: this
    // EFFECTS: Saves charities to the file JSON_STORE; throws exception if unable to save to file.
    private void saveCharities() {
        try {
            jsonWriter.open();
            jsonWriter.write(charityManager);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, saveMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_LOCATION);
        }
    }

    // MODIFIES: this, charityManager
    // EFFECTS: loads charityManager from file; throws exception if file to load from cannot be found.
    private void loadCharities() {
        try {
            charityManager = jsonReader.read();
            JOptionPane.showMessageDialog(null, loadMessage);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_LOCATION);
        }
    }

    // MODIFIES: mainMenuPanel, this
    // EFFECTS: helper method to create the panel with given title and background image
    private JPanel createPanel(String panelTitle) {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintBackgroundImage(this, g);
            }
        };

        gridBagConstraints = createGridBagConstraints(0, 0, 2);
        JLabel titleLabel = new JLabel(panelTitle);
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, gridBagConstraints);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: Add the save/load portfolio panel to the frame
    private void addSaveLoadPanelToFrame() {
        JPanel saveLoadPanel = new JPanel();
        saveButton = createButton("Save data to file");
        loadButton = createButton("Load data from file");

        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(loadButton);

        guiFrame.add(mainMenuPanel, BorderLayout.CENTER);
        guiFrame.add(saveLoadPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Creates a button with the given label
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: Helper method to add label and text field to panel
    private void addLabelAndTextField(JPanel panel, String labelText, JComponent textField) {
        labelConstraints = createGridBagConstraints(0,GridBagConstraints.RELATIVE,1);
        fieldConstraints = createGridBagConstraints(1,GridBagConstraints.RELATIVE,1);

        panel.add(new JLabel(labelText), labelConstraints);
        panel.add(textField, fieldConstraints);
    }

    // MODIFIES: this
    // EFFECTS: sets background image
    private void paintBackgroundImage(JPanel panel, Graphics g) {
        ImageIcon imageIcon = new ImageIcon(BACKGROUND_IMAGE_SRC);
        Image image = imageIcon.getImage();

        int x = (panel.getWidth() - image.getWidth(null)) / 2;
        int y = (panel.getHeight() - image.getHeight(null)) / 2;
        g.drawImage(image, x, y, panel);
    }

    // MODIFIES: this
    // EFFECTS: sets gridBag constraints
    private GridBagConstraints createGridBagConstraints(int x, int y, int gridWidth) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gridWidth;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }
}