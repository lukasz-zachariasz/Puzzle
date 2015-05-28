package puzzle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class Puzzle {

    private JLayeredPane contentPane;

    protected void initUI() throws MalformedURLException, IOException {
        JFrame frame = new JFrame(Puzzle.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JLayeredPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        //ImageIcon image = new ImageIcon(((new ImageIcon("red-05.jpg")).getImage()).getScaledInstance(1000, 500, java.awt.Image.SCALE_SMOOTH));
        BufferedImage image = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(ImageIO.read(new File("test.jpeg")), 0, 0, 1000, 500, null);
        g.dispose();
        //image = ImageIO.read(new File("test.jpeg"));
        MouseDragger dragger = new MouseDragger();
        Random random = new Random();
        BufferedImage temp = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        
        //Win conditionsy kurwa mać
        
        int[][] gamearray = new int[5][5];
        int ri;
        int rj;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                gamearray[i][j]=-1;
                }
        }
            
        
        
        
        for(int i = 1; i <= 5; i++)
        {
            for(int j = 1; j <= 5; j++)
            {
                for(;;)
                {
                    ri=random.nextInt(5);
                    rj=random.nextInt(5);
                    if(gamearray[ri][rj]==-1)
                    {
                        gamearray[ri][rj]=i*5+j;
                        break;
                    }
                }
                temp = image.getSubimage((i-1)*200, (j-1)*100, 200, 100);
                JLabel draggableImage = new JLabel(new ImageIcon(temp));
                draggableImage.setSize(draggableImage.getPreferredSize());
                draggableImage.setLocation((ri)*200, (rj)*100);                                                                    
                dragger.makeDraggable(draggableImage);
                contentPane.add(draggableImage);
            }
        }
        
        
        
        
        /*
          for(int i = 1; i < 5; i++)
        {
            for(int j = 1; j < 5; j++)
            {
                temp = image.getSubimage((i-1)*200, (j-1)*100, 200, 100);
                JLabel draggableImage = new JLabel(new ImageIcon(temp));
                draggableImage.setSize(draggableImage.getPreferredSize());
                draggableImage.setLocation();
                dragger.makeDraggable(draggableImage);
                contentPane.add(draggableImage);
            }
        }

        */
//        BufferedImage resizedImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB); 
//Graphics2D g = resizedImage.createGraphics();
//g.drawImage(image, 0, 0, new_width, new_height, null);
//g.dispose();
//BufferedImage bi = new BufferedImage(
//    icon.getIconWidth(),
//    icon.getIconHeight(),
//    BufferedImage.TYPE_INT_RGB);
//Graphics g = bi.createGraphics();
//// paint the Icon to the BufferedImage.
//icon.paintIcon(null, g, 0,0);
//g.dispose();
//        for (int i = 0; i < 25; i++) {
//            JLabel draggableImage = new JLabel(image);
//            draggableImage.setSize(draggableImage.getPreferredSize());
//            draggableImage.setLocation(random.nextInt(contentPane.getWidth() - draggableImage.getWidth()),
//                    random.nextInt(contentPane.getHeight() - draggableImage.getHeight()));
//
//            dragger.makeDraggable(draggableImage);
//            contentPane.add(draggableImage);
//        }
        
        contentPane.repaint();
    }

    public static class MouseDragger extends MouseAdapter {
        private Point lastLocation;
        private Component draggedComponent;

        @Override
        public void mousePressed(MouseEvent e) {
            draggedComponent = e.getComponent();
            lastLocation = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point location = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
            if (draggedComponent.getParent().getBounds().contains(location)) {
                Point newLocation = draggedComponent.getLocation();
                newLocation.translate(location.x - lastLocation.x, location.y - lastLocation.y);
                newLocation.x = Math.max(newLocation.x, 0);
                newLocation.x = Math.min(newLocation.x, draggedComponent.getParent().getWidth() - draggedComponent.getWidth());
                newLocation.y = Math.max(newLocation.y, 0);
                newLocation.y = Math.min(newLocation.y, draggedComponent.getParent().getHeight() - draggedComponent.getHeight());
                draggedComponent.setLocation(newLocation);
                lastLocation = location;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            lastLocation = null;
            draggedComponent = null;
        }

        public void makeDraggable(Component component) {
            component.addMouseListener(this);
            component.addMouseMotionListener(this);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new Puzzle().initUI();
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}