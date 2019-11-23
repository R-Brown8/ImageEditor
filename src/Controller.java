import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Controller extends JPanel {
    private View view;
    private Model model;
    protected Image image;
    protected BufferedImage bufferedImage = null;
    private Point start = null;
    private boolean canClickBool = false;

    public Controller() {
        this.view = new View(this);
        view.setExtendedState(Frame.MAXIMIZED_BOTH);
        //anon class to allow th user to upload an image
        view.uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bufferedImage = null;
                image = null;
                uploadImage();
            }
        });

        view.analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = new Model(bufferedImage);
                view.analyzeButton.setVisible(false);
                setViewColor();
                canClickBool = true;
                view.radioPanel.setVisible(true);
            }
        });

        view.imageIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(canClickBool) {
                    start = e.getPoint();
                    int x = start.x;
                    int y = start.y;

                    int colorAtClick = model.getRGBatPixel(x, y);
                    int[] rgbColors = model.getRGBArray(colorAtClick);

                    view.color1.setBackground(new Color(rgbColors[0], rgbColors[1], rgbColors[2]));
                    view.color1.setText("R: " + rgbColors[0] + " G: " + rgbColors[1] + " B: " + rgbColors[2]);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e){
                if(canClickBool){
                    view.imageIcon.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }else{
                    view.imageIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        view.negativeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BufferedImage negativeBufferedImage = bufferedImage;
                negativeBufferedImage = model.makeImageNegative(negativeBufferedImage);

                Image negativeImage = negativeBufferedImage.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);

                ImageIcon icon = new ImageIcon(negativeImage);
                view.imageIcon.setIcon(icon);
                canClickBool = false;
            }
        });

        view.blackWhiteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage blackWhiteBufferedImage = bufferedImage;
                blackWhiteBufferedImage = model.makeImageGrayscale(blackWhiteBufferedImage);

                Image blackWhiteImage = blackWhiteBufferedImage.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);

                ImageIcon icon = new ImageIcon(blackWhiteImage);
                view.imageIcon.setIcon(icon);
                canClickBool = false;
            }
        });

        view.noneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon(image);
                view.imageIcon.setIcon(icon);
            }
        });

    }//end constructor

    //helper function to allow user to upload a new picture
    private void uploadImage(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG",
                "jpg", "png");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(null);
        final File file = fileChooser.getSelectedFile();

        //do this stuff if the image is legit
        if(returnVal == JFileChooser.APPROVE_OPTION){
            //trying to resize the image
            try {
                bufferedImage = ImageIO.read(fileChooser.getSelectedFile());
            }catch(IOException m){
                m.printStackTrace();
            }
            //setting image to size of JPanel
            image = bufferedImage.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);
            bufferedImage = resize(bufferedImage, view.imageIcon.getWidth(), view.imageIcon.getHeight());

            ImageIcon icon = new ImageIcon(image);
            view.imageIcon.setIcon(icon);
            view.analyzeButton.setVisible(true);
            //do we want to display the file name or the file path?
            view.infoLabel.setText("You chose: " + fileChooser.getSelectedFile().getName());
        }else if(returnVal == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "Invalid file type. Cmon.", "Nope", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setViewColor(){
        int[] rgb = model.colorHex1;
        view.color1.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
        view.color1.setText("R: " + rgb[0] + " G: " + rgb[1] + " B: " + rgb[2]);
    }

    private BufferedImage resize(BufferedImage image, int w, int h) {
        Image temp = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(temp, 0, 0, w, h,null);

        return newImage;
    }


}//end class