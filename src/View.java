import javafx.scene.control.RadioButton;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class View extends JFrame {
    Controller controller;
    protected JButton analyzeButton;
    protected JButton uploadButton;
    protected JLabel imageIcon;
    protected JLabel infoLabel;
    protected JRadioButton blackWhiteRadioButton;
    protected JRadioButton negativeRadioButton;
    protected JRadioButton noneRadioButton;
    protected JPanel radioPanel;
    JPanel centerPanel;

    //the color labels
    JLabel color1;

    public View(Controller con) {
        super("Welcome");
        this.controller = con;
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(1300, 1000));

        setUpUI();
        pack();
    }


    private void setUpUI(){
        JPanel contentPanel = (JPanel) getContentPane();

        //the image and save button are part of a vertical box layout
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5 ,5, 5));

        infoLabel = new JLabel("Upload an image. Please." +
                " It will be an adventure.");
        infoLabel.setFont(new Font("serif", Font.PLAIN, 24));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(infoLabel, BorderLayout.NORTH);

        imageIcon = new JLabel(new ImageIcon("images/blank-image.jpg"));
        //imageIcon.setPreferredSize(new Dimension(650, 500));
        uploadButton = new JButton(new ImageIcon("images/upload.png"));

        //create a new panel for the CENTER of our contentPanel
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(imageIcon);
        centerPanel.add(uploadButton);
        //centerPanel.setPreferredSize(new Dimension(900, 800));

        //add the center panel to the overall content panel
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        analyzeButton = new JButton("Most Common Color");
        analyzeButton.setVisible(false);
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(analyzeButton);

        JPanel colorViewOutput = new JPanel();
        colorViewOutput.setLayout(new GridLayout(1, 5, 2, 1));

        color1 = new JLabel("", SwingConstants.CENTER);
        color1.setOpaque(true);
        color1.setPreferredSize(new Dimension(100, 150));
        color1.setFont(new Font("default", Font.BOLD, 24));
        color1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        color1.setBackground(new Color(221, 221, 221));
        colorViewOutput.add(color1);

        ButtonGroup buttonGroup= new ButtonGroup();
        blackWhiteRadioButton = new JRadioButton("Black/White");
        negativeRadioButton = new JRadioButton("Negative");
        noneRadioButton = new JRadioButton("None");
        noneRadioButton.setSelected(true);
        buttonGroup.add(noneRadioButton);
        buttonGroup.add(blackWhiteRadioButton);
        buttonGroup.add(negativeRadioButton);

        radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3, 1));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Edit Options"));
        radioPanel.add(blackWhiteRadioButton);
        radioPanel.add(negativeRadioButton);
        radioPanel.add(noneRadioButton);
        radioPanel.setVisible(false);

        //add buttons to all canvas.
        contentPanel.add(radioPanel, BorderLayout.EAST);
        contentPanel.add(colorViewOutput, BorderLayout.SOUTH);
    }











}//end class
