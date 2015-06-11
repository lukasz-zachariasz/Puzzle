package puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Puzzle extends JFrame {

    public static JLayeredPane contentPane;
    public static int[][] gamearray;
    private static Long czas;

    static int check() throws FileNotFoundException {
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                if(!(gamearray[i][j] == i*5 + j))
                {
                    return 0;
                }
        }
    }
                czas = System.currentTimeMillis() - czas;
                
                                File file = new File("rank.txt");
                                Scanner in = new Scanner(file);
                                long tab[] = new long[3];
                                long temp=0;
                                tab[0]=in.nextLong();
                                tab[1]=in.nextLong();
                                tab[2]=in.nextLong();
                                in.close();
                                if(czas > tab[0] && czas > tab[1] && czas < tab[2])
                                {
                                    tab[2] = czas;
                                }
                                if(czas > tab[0] && czas < tab[1])
                                {
                                    tab[2] = tab[1];
                                    tab[1] = czas;
                                }
                                if( czas < tab[0]) {
                                    temp = tab[0];
                                    tab[0] = czas;
                                    tab[2] = tab[1];
                                    tab[1] = temp;
                                }
                
                 PrintWriter zapis = new PrintWriter("rank.txt");
                    zapis.println(tab[0]);
                    zapis.println(tab[1]);
                    zapis.println(tab[2]);
                    zapis.close();
                    System.exit(0);
        return 0;
    }
    
    private File selectedFile;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    public static JFrame frame;
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
        String[] EXTENSION=new String[]{"jpeg", "jpg", "png", "bmp"};
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("Pliki obrazów", EXTENSION);
            fileChooser.setFileFilter(xmlfilter);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
           czas = System.currentTimeMillis();
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
        
        
        //przetasowanie kawałków
        gamearray = new int[5][5];
        int ri;
        int rj;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                gamearray[i][j] = -1;
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
                    if(gamearray[ri][rj] == -1)
                    {
                        gamearray[ri][rj] = (i-1) * 5 + (j - 1);
                        break;
                    }
                }
                temp = image.getSubimage((i - 1) * 200, (j - 1) * 100, 200, 100);
                JLabel draggableImage = new JLabel(new ImageIcon(temp));
                draggableImage.setSize(draggableImage.getPreferredSize());
                draggableImage.setLocation((ri) * 200, (rj) * 100);                                                                    
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
                if(!file.exists())
                {
                    try {
                        file.createNewFile();
                        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
                        writer.println(101000);
                        writer.println(102000);
                        writer.println(103000);
                        }  
                    } catch (IOException ex) {
                    }
                }
                
                Scanner in = new Scanner(file);
                jLabel1.setText("1." + in.nextLine());
                jLabel2.setText("2." + in.nextLine());
                jLabel3.setText("3." + in.nextLine());
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
        contentPane.add(buttonStart);  
    }
}
