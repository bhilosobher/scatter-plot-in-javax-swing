package assessedExercise3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import assessedExercise3.Bond;
import javax.swing.JComponent;

// A component that draws a scatter plot, based on arrayList of Bond objects.

public class ScatterPlotComponent extends JComponent {
	//the 'values' ArrayList contains the data on bonds which will be manipulated for painting
	//the 'shapes' ArrayList contains all the ovals(dots) painted based on 'values' above
	private ArrayList<Bond> values;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	public ScatterPlotComponent(Model bondModel) {
		values = bondModel.getBonds();
	}
	
	//a method that checks, when given a point, whether the point is contained
	//by any of the Shapes painted by the paintComponent method
	public String selectDot(int x, int y) {
		String description = "";
		for (int i = 0; i <= shapes.size() - 1; i++) {
			if (shapes.get(i).contains(x, y)) {
				//if the point is contained by one of the Shapes, get information from the corresponding Bond
				description = values.get(i).toString();
				break;
			}
		}
		//if the point is not contained by any of the Shapes, let the user know:
		if (description.equals("")) {
			description = "No dot here!";
		}
		return description;
	}

	public void drawAxes(Graphics g) {
		
		if (values.isEmpty() == false) {
		// define a buffer area in which not to paint but draw the axes, labels and tick marks instead
		float heightBuffer = this.getHeight() / 20;
		float widthBuffer = this.getWidth() / 20;

		// draw a white canvas to plot the scatter plot on
		g.setColor(Color.WHITE);
		Shape square = new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight());
		((Graphics2D) g).fill(square);

		g.setColor(Color.BLACK);
		
		// draw x-axis (leaving 10% of the canvas blank beneath the axis, to add tick marks and labels)
			g.drawLine((int) widthBuffer, (int) (this.getHeight() - heightBuffer), this.getWidth(), (int) (this.getHeight() - heightBuffer));

		// draw the y-axis (leaving 10% of the canvas blank left of the axis, to add tick marks and labels)
		g.drawLine((int) widthBuffer - 1, 0, (int) widthBuffer - 1, (int) (this.getHeight() - heightBuffer));
		}
	}
	
	//it's necessary to let this method know what number the origin represents on each axis; this is what startX and startY
	//signify - they tell us from where we start counting, what value corresponds to origin on each axis 
	
	public void drawHatchMarksAndLabels(Graphics g, Integer startX, Integer startY) {
		//the if statement ensure that the this method will be effective only when data has been loaded in the model
		if (values.isEmpty() == false) {
			
			//set an empty buffer area in which to later draw the labels 
			float heightBuffer = this.getHeight() / 20;
			float widthBuffer = this.getWidth() / 20;
			
			//set parameters for the hatch marks, based on current size
			Integer markSize = this.getHeight() / 50;
			Integer numberOfMarks = this.getWidth() / 100;
			
			//these variables will be used for drawing the labels, labels which range the min to the max value
			Integer endX = Bond.getMaxX();
			Integer endY = Bond.getMaxY();
			
			
			
			for (int i = 0; i <= numberOfMarks; i++) {
				//draw hatch marks on x axis
				g.drawLine((int) (widthBuffer + ((float) i / numberOfMarks) * (this.getWidth() - widthBuffer) - 1),
						this.getHeight() - (int) heightBuffer,
						(int) (widthBuffer + (float) i / numberOfMarks * (this.getWidth() - widthBuffer) - 1),
						(int) (this.getHeight() - heightBuffer - markSize));
				//draw hatch marks on y axis
				g.drawLine((int) widthBuffer,
						(int) (this.getHeight()
								- (heightBuffer + ((float) i / numberOfMarks) * (this.getHeight() - heightBuffer))),
						(int) (widthBuffer + markSize), (int) (this.getHeight()
								- (heightBuffer + ((float) i / numberOfMarks) * (this.getHeight() - heightBuffer))));

				// draw x-axis labels
				g.drawString(Integer.toString((int) (startX + (float) i / numberOfMarks * (endX - startX))),
						(int) (widthBuffer / 2 - 10 + (float) i / 10 * (this.getWidth() - widthBuffer)),
						(int) (this.getHeight() - heightBuffer / 2) + 10);
				// draw y-axis labels
				g.drawString(Integer.toString((int) (endY - (float) i / numberOfMarks * (endY - startY))), 10,
						(int) ((float) i / 10 * (this.getHeight() - heightBuffer)) + 10);

			}
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		Integer labelStartX = 0;
		Integer labelStartY = 0;
		//this section deals with any negative values in our data set (which would otherwise spoil my painting 
		//by "shifting" all numbers to the right of 0;
		Integer shiftX = 0;
		Integer shiftY = 0;
		if (Bond.getMinX() < 0) {
			shiftX = 0 - Bond.getMinX();
			labelStartX = Bond.getMinX();
		}
		if (Bond.getMinY() < 0) {
			shiftY = 0 - Bond.getMinY();
			labelStartY = Bond.getMinY();
		}

		drawAxes(g);
		drawHatchMarksAndLabels(g,labelStartX, labelStartY);
		
		//create an empty area between the axes and the bottom and left side of the axes, in which to draw the labels
		float heightBuffer = this.getHeight() / 20;
		float widthBuffer = this.getWidth() / 20;
		float dotDiameter = this.getWidth() / 110;

		// remove any previously stored dots (otherwise the array list would grow on every repaint)
		shapes.clear();
		
		g2.setColor(new Color(255, 128, 0));
		
		//for each bond, paint a dot based on the values pointed to by the combo boxes
		for (Bond b : values) {

			float dotWidth = widthBuffer - 1
					+ (float) (b.getXCoordinate() + shiftX) / (Bond.getMaxX() + shiftX) * 19 / 20 * this.getWidth()
					- dotDiameter / 2;
			float dotHeight = this.getHeight() - (heightBuffer
					+ (float) (b.getYCoordinate() + shiftY) / (Bond.getMaxY() + shiftY) * 19 / 20 * this.getHeight()
					+ dotDiameter / 2);

			Shape dot = new Ellipse2D.Float(dotWidth, dotHeight, dotDiameter, dotDiameter);

			g2.fill(dot);
			//store a reference to each dot in the array of Shapes 
			shapes.add(dot);
		}
	}
}
