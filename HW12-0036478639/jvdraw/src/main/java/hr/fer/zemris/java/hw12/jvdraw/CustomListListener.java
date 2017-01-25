package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Class representing custom list listener.
 * Used mostly as listener on {@link JVDraw} list 
 * when selection was made by double clicking.
 * Adds possibility of changing current objects by
 * postion, color, etc.
 * @author Teo Toplak
 *
 */
public class CustomListListener extends MouseAdapter{

	/**
	 * Program using this listener
	 */
	private JVDraw jVDraw;
	/**
	 * list objects in {@link JVDraw}
	 */
	private JList<GeometricalObject> objectList;
	
	/**
	 * Simple constructor taking program using this listener and
	 * object's list as arguments
	 * @param jVDraw Program using this listener
	 * @param objectList list objects in {@link JVDraw}
	 */
	public CustomListListener(JVDraw jVDraw,JList<GeometricalObject> objectList) {
		this.jVDraw = jVDraw;
		this.objectList=objectList;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2) {
			int index = objectList.locationToIndex(e.getPoint());
			if(index>=0) {
				openDialogForObject(index);
			}
		}
	}
	
	/**
	 * Opens dialog when object is needed to changed by clicking
	 * on list.
	 * @param index index of clicked object in list
	 */
	private void openDialogForObject(int index) {
        GeometricalObject object = jVDraw.getModel().getObject(index);

        try {
            if (object instanceof Line) {
                openLineDialog((Line) object);
            } else if (object instanceof Circle) {
                openCircleDialog((Circle) object);
            } else if (object instanceof FCircle) {
                openFilledCircleDialog((FCircle) object);
            }

            jVDraw.getModel().fire();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(jVDraw, "Illegal input!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

	/**
	 * Opens dialog for selected line
	 * @param line line selected
	 */
    private void openLineDialog(Line line) {
        JTextArea startXArea = createTextArea(line.getStart().x);
        JTextArea startYArea = createTextArea(line.getStart().y);
        JTextArea endXArea = createTextArea(line.getEnd().x);
        JTextArea endYArea = createTextArea(line.getEnd().y);
        JTextArea colorRedArea = createTextArea(line.getColor().getRed());
        JTextArea colorGreenArea = createTextArea(line.getColor().getGreen());
        JTextArea colorBlueArea = createTextArea(line.getColor().getBlue());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(new JLabel("Start-X: "));
        panel.add(startXArea);
        panel.add(new JLabel("Start-Y: "));
        panel.add(startYArea);
        panel.add(new JLabel("End-X: "));
        panel.add(endXArea);
        panel.add(new JLabel("End-Y: "));
        panel.add(endYArea);
        panel.add(new JLabel("Red: "));
        panel.add(colorRedArea);
        panel.add(new JLabel("Green: "));
        panel.add(colorGreenArea);
        panel.add(new JLabel("Blue: "));
        panel.add(colorBlueArea);

        int result = JOptionPane.showConfirmDialog(jVDraw, panel,
                "Insert values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            line.setStart(new Point(Integer.parseInt(startXArea.getText()),
            		Integer.parseInt(startYArea.getText())) );
            line.setEnd(new Point(Integer.parseInt(endXArea.getText()),
            		Integer.parseInt(endYArea.getText())) );
            int r = Integer.parseInt(colorRedArea.getText());
            int g = Integer.parseInt(colorGreenArea.getText());
            int b = Integer.parseInt(colorBlueArea.getText());
            line.setColor(new Color(r, g, b));
        }
    }

    /**
     * Creates text area used in this listeners dialogs
     * @param x value
     * @return text area
     */
    private JTextArea createTextArea(int x) {
		JTextArea area = new JTextArea(String.valueOf(x));
		area.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		return area;
	}

    /**
     * Opens dialog for circle
     * @param circle circle
     */
	private void openCircleDialog(Circle circle) {
        JTextArea centerXArea = createTextArea(circle.getCenter().x);
        JTextArea centerYArea = createTextArea(circle.getCenter().y);
        JTextArea radiusArea = createTextArea((int)circle.getRadius());
        JTextArea colorRedArea = createTextArea(circle.getOutlineColor().getRed());
        JTextArea colorGreenArea = createTextArea(circle.getOutlineColor().getGreen());
        JTextArea colorBlueArea = createTextArea(circle.getOutlineColor().getBlue());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("Center-X: "));
        panel.add(centerXArea);
        panel.add(new JLabel("Center-Y: "));
        panel.add(centerYArea);
        panel.add(new JLabel("Radius: "));
        panel.add(radiusArea);
        panel.add(new JLabel("Red: "));
        panel.add(colorRedArea);
        panel.add(new JLabel("Green: "));
        panel.add(colorGreenArea);
        panel.add(new JLabel("Blue: "));
        panel.add(colorBlueArea);

        int result = JOptionPane.showConfirmDialog(jVDraw, panel,
                "Insert values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            circle.setCenter(new Point(Integer.parseInt(centerXArea.getText()),
            		Integer.parseInt(centerYArea.getText())) );
            circle.setRadius(Integer.parseInt(radiusArea.getText()));
            int r = Integer.parseInt(colorRedArea.getText());
            int g = Integer.parseInt(colorGreenArea.getText());
            int b = Integer.parseInt(colorBlueArea.getText());

            circle.setOutlineColor(new Color(r, g, b));
        }
    }

	/**
     * Opens dialog for filled circle
     * @param circle filled circle
     */
    private void openFilledCircleDialog(FCircle filledCircle) {
        JTextArea centerXArea = createTextArea(filledCircle.getCenter().x);
        JTextArea centerYArea = createTextArea(filledCircle.getCenter().y);
        JTextArea radiusArea = createTextArea((int)filledCircle.getRadius());
        JTextArea outlineColorRedArea = createTextArea(filledCircle
                .getOutlineColor().getRed());
        JTextArea outlineColorGreenArea = createTextArea(filledCircle
                .getOutlineColor().getGreen());
        JTextArea outlineColorBlueArea = createTextArea(filledCircle
                .getOutlineColor().getBlue());
        JTextArea areaColorRedArea = createTextArea(filledCircle.getAreaColor()
                .getRed());
        JTextArea areaColorGreenArea = createTextArea(filledCircle
                .getAreaColor().getGreen());
        JTextArea areaColorBlueArea = createTextArea(filledCircle
                .getAreaColor().getBlue());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
        panel.add(new JLabel("Center-X: "));
        panel.add(centerXArea);
        panel.add(new JLabel("Center-Y: "));
        panel.add(centerYArea);
        panel.add(new JLabel("Radius: "));
        panel.add(radiusArea);
        panel.add(new JLabel("Outline-Red: "));
        panel.add(outlineColorRedArea);
        panel.add(new JLabel("Outline-Green: "));
        panel.add(outlineColorGreenArea);
        panel.add(new JLabel("Outline-Blue: "));
        panel.add(outlineColorBlueArea);
        panel.add(new JLabel("Area-Red: "));
        panel.add(areaColorRedArea);
        panel.add(new JLabel("Area-Green: "));
        panel.add(areaColorGreenArea);
        panel.add(new JLabel("Area-Blue: "));
        panel.add(areaColorBlueArea);

        int result = JOptionPane.showConfirmDialog(jVDraw, panel,
                "Insert values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            filledCircle.setCenter(new Point(Integer.parseInt(centerXArea.getText()),
            		Integer.parseInt(centerYArea.getText())) );
            filledCircle.setRadius(Integer.parseInt(radiusArea.getText()));

            int outR = Integer.parseInt(outlineColorRedArea.getText());
            int outG = Integer.parseInt(outlineColorGreenArea.getText());
            int outB = Integer.parseInt(outlineColorBlueArea.getText());

            filledCircle.setOutlineColor(new Color(outR, outG, outB));

            int areaR = Integer.parseInt(areaColorRedArea.getText());
            int areaG = Integer.parseInt(areaColorGreenArea.getText());
            int areaB = Integer.parseInt(areaColorBlueArea.getText());

            filledCircle.setAreaColor(new Color(areaR, areaG, areaB));
        }
    }
}
