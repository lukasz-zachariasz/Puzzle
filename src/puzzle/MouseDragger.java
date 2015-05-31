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
import javax.swing.SwingUtilities;

/**
 *
 * @author Skenior
 */
public class MouseDragger extends MouseAdapter {
        private Point lastLocation;
        private Component draggedComponent;

        //wciśnięcie klawisza myszki
        @Override
        public void mousePressed(MouseEvent e) {
            draggedComponent = e.getComponent();
            lastLocation = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
        }

        //przesuwanie wciśniętego klawisza myszki
        @Override
        public void mouseDragged(MouseEvent e) {
            Point location = SwingUtilities.convertPoint(draggedComponent, e.getPoint(), draggedComponent.getParent());
            if (draggedComponent.getParent().getBounds().contains(location)) {
                Point newLocation = draggedComponent.getLocation();
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
            lastLocation = null;
            draggedComponent = null;
        }

        //ustawienie komponentów na reakcję na przycisk
        public void makeDraggable(Component component) {
            component.addMouseListener(this);
            component.addMouseMotionListener(this);
        }

    }
