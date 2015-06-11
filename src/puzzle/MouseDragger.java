/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Skenior
 */
public class MouseDragger extends MouseAdapter {
        private Point lastLocation;
        private Component draggedComponent;
        private Point newLocation;
        private Point location;
        public Point prevLocation;

        //wciśnięcie klawisza myszki
        @Override
        public void mousePressed(MouseEvent e) {
            draggedComponent = e.getComponent();
            lastLocation = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
            prevLocation = lastLocation;
        }

        //przesuwanie wciśniętego klawisza myszki
        @Override
        public void mouseDragged(MouseEvent e) {
            location = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
            if (draggedComponent.getParent().getBounds().contains(location)) {
                newLocation = draggedComponent.getLocation();
                newLocation.translate(location.x - lastLocation.x, location.y - lastLocation.y);
                newLocation.x = Math.max(newLocation.x, 0);
                newLocation.x = Math.min(newLocation.x, 1000 - draggedComponent.getWidth());
                newLocation.y = Math.max(newLocation.y, 0);
                newLocation.y = Math.min(newLocation.y, 500 - draggedComponent.getHeight());
                draggedComponent.setLocation(newLocation);
                lastLocation = location;
            }
        }

        //puszczenie klawisza myszki
        @Override
        public void mouseReleased(MouseEvent e) {
            draggedComponent.setLocation(5000,5000);
            
            Component dragComp2 = Puzzle.contentPane.findComponentAt((newLocation.x + 100)-(newLocation.x + 100) % 200, (newLocation.y + 50)-(newLocation.y + 50) % 100);
            if(dragComp2 instanceof JLabel) dragComp2.setLocation((prevLocation.x) - (prevLocation.x)%200, (prevLocation.y)-(prevLocation.y) % 100);
            draggedComponent.setLocation((newLocation.x + 100)-(newLocation.x + 100) % 200, (newLocation.y + 50)-(newLocation.y + 50)%100);
            int tx;
            int ty;
            tx=(newLocation.x + 100) / 200;
            if(tx > 4)tx = 4;
            ty=(newLocation.y + 50) / 100;
            if(ty > 4)ty = 4;
            
            int temp = Puzzle.gamearray[prevLocation.x / 200][prevLocation.y / 100];
            Puzzle.gamearray[prevLocation.x / 200][prevLocation.y / 100] = Puzzle.gamearray[tx][ty];
            Puzzle.gamearray[tx][ty] = temp;
            try {
                Puzzle.check();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MouseDragger.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0; i < 5; i++)
        {
            for(int j = 0;j < 5 ; j++)
            {
                System.out.println(Puzzle.gamearray[i][j]);

        }
    }
            lastLocation = null;
            draggedComponent = null;
        }

        //ustawienie komponentów na reakcję na przycisk
        public void makeDraggable(Component component) {
            component.addMouseListener(this);
            component.addMouseMotionListener(this);
        }

    }
