import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class RoundButton extends JButton {
  // class for round buttoh for traffic light
  public RoundButton(String textLabel) {
    // call the superclass constructor
    super(textLabel);
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, 
      size.height);
    setPreferredSize(size);
    setContentAreaFilled(false);
  }

  protected void paintComponent(Graphics g) {
    // paint the button
    if (getModel().isArmed()) {
      g.setColor(Color.lightGray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width-1, 
      getSize().height-1);

    super.paintComponent(g);
  }

  protected void paintBorder(Graphics g) {
    // paint borders for light.
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width-1, 
      getSize().height-1);
  }
  
  Shape shape;
  public boolean contains(int x, int y) {
    // return if shape contains cordinates x,y;
    if (shape == null || 
      !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, 
        getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
}