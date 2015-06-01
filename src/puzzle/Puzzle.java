package puzzle;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.MalformedURLException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Puzzle extends JFrame {

    private static JLayeredPane contentPane;
    public int[][] gamearray;
    private File selectedFile;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JFrame frame;
    private JButton jButton4;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    
        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
    }  
        
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){      
            
            
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        String[] EXTENSION=new String[]{"jpeg","jpg","png","bmp"};
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("Pliki obrazów", EXTENSION);
            fileChooser.setFileFilter(xmlfilter);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        else if (result == JFileChooser.CANCEL_OPTION) {
            System.exit(0);
        }
        
        //wczytanie obrazu z wybranego pliku
        BufferedImage image = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        try {
            g.drawImage(ImageIO.read(selectedFile), 0, 0, 1000, 500, null);
        } catch (IOException ex) {
            Logger.getLogger(Puzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.dispose();
       MouseDragger dragger = new MouseDragger();
        Random random = new Random();
        BufferedImage temp;
        
        //Win conditionsy kurwa mać
        
        //przetasowanie kawałków
        gamearray = new int[5][5];
        int ri;
        int rj;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                gamearray[i][j]=-1;
            }
        }
        
        //pocięcie wybranego obrazu i wyświetlenie w tablicy przetasowań
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
        }

    protected void initUI() throws MalformedURLException, IOException {
        //załadowanie okna gry
        frame = new JFrame(Puzzle.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JLayeredPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);
        
        
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setSize(1005, 525);
        frame.setExtendedState(frame.getExtendedState());// | JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        
        
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();
                jButton3 = new javax.swing.JButton();
                jButton4 = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
        
                jLabel1.setText("1.");
                jLabel1.setVisible(false);
                jLabel2.setText("2.");
                jLabel2.setVisible(false);
                jLabel3.setText("3.");
                jLabel3.setVisible(false);

        jButton1.setText("Zacznij");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton2.setText("Ranking");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButton2ActionPerformed(evt);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Puzzle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void jButton2ActionPerformed(ActionEvent evt) throws FileNotFoundException {

                File file = new File("rank.txt");
                Scanner in = new Scanner(file);
 
                jLabel1.setText(in.nextLine());
                jLabel2.setText(in.nextLine());
                jLabel3.setText(in.nextLine());
                jButton1.setVisible(false);
                jButton2.setVisible(false);
                jButton3.setVisible(false);
                jButton4.setVisible(true);
                jLabel1.setVisible(true);
                jLabel2.setVisible(true);
                jLabel3.setVisible(true);
            }
        });
        jButton3.setText("Wyjście");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        
                jButton4.setText("Powrót");
                jButton4.setVisible(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }

            private void jButton4ActionPerformed(ActionEvent evt) {
                jButton1.setVisible(true);
                jButton2.setVisible(true);
                jButton3.setVisible(true);
                jButton4.setVisible(false);
                jLabel1.setVisible(false);
                jLabel2.setVisible(false);
                jLabel3.setVisible(false);
            }
        });
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(450, 450, 450)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                        .addComponent(jLabel1)
                    .addComponent(jButton2)
                        .addComponent(jLabel2)
                    .addComponent(jButton1)
                        .addComponent(jLabel3)
                    .addComponent(jButton4))
                .addContainerGap(450, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addComponent(jLabel2)
                    .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addComponent(jLabel3)
                    .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(230, Short.MAX_VALUE))
        );

        
        frame.setVisible(true);          
        
/*
        
        //wczytywanie plików
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        String[] EXTENSION=new String[]{"jpeg","jpg","png","bmp"};
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("Pliki obrazów", EXTENSION);
            fileChooser.setFileFilter(xmlfilter);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        else if (result == JFileChooser.CANCEL_OPTION) {
            System.exit(0);
        }
        
        //wczytanie obrazu z wybranego pliku
        BufferedImage image = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(ImageIO.read(selectedFile), 0, 0, 1000, 500, null);
        g.dispose();
       MouseDragger dragger = new MouseDragger();
        Random random = new Random();
        BufferedImage temp;
        
        //Win conditionsy kurwa mać
        
        //przetasowanie kawałków
        int[][] gamearray = new int[5][5];
        int ri;
        int rj;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                gamearray[i][j]=-1;
            }
        }
        
        //pocięcie wybranego obrazu i wyświetlenie w tablicy przetasowań
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
        }*/
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
    
    public static void menu()
    {
                JButton buttonStart = new JButton("Start");
        buttonStart.setLocation(100, 100);
        buttonStart.setVisible(true);
        //buttonStart.addActionListener((ActionListener) this);
        contentPane.add(buttonStart);  
    }
}
