package puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
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
        BufferedImage image = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(ImageIO.read(new File("test.jpeg")), 0, 0, 1000, 500, null);
        g.dispose();
        MouseDragger dragger = new MouseDragger();
        Random random = new Random();
        BufferedImage temp;
        
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
        contentPane.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new Puzzle().initUI();
                } catch (MalformedURLException e) {
                } catch (IOException ex) {
                }
            }
        });
    }
}
