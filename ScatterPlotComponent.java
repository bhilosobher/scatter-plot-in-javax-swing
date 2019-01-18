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

/**
 * A component that draws scatter plots.
 */
public class ScatterPlotComponent extends JComponent {
	private ArrayList<Bond> values;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	public ScatterPlotComponent(Model bondModel) {
		values = bondModel.getBonds();
	}

	public String selectDot(int x, int y) {
		String description = "";
		for (int i = 0; i <= shapes.size() - 1; i++) {
			if (shapes.get(i).contains(x, y)) {
				description = values.get(i).toString();
				break;
			}
		}
		if (description.equals("")) {
			description = "No dot here!";
		}
		return description;
	}

	public void drawAxes(Graphics g) {
		// define a buffer area in which not to paint but draw the axes, labels and tick
		// marks instead
		float heightBuffer = this.getHeight() / 20;
		float widthBuffer = this.getWidth() / 20;

		// draw a white canvas to plot the scatter plot on
		g.setColor(Color.WHITE);
		Shape square = new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight());
		((Graphics2D) g).fill(square);

		// draw x-axis (leaving 10% of the canvas blank beneath the axis, to add tick
		// marks and labels)
		g.setColor(Color.BLACK);
		g.drawLine((int) widthBuffer, (int) (this.getHeight() - heightBuffer), this.getWidth(),
				(int) (this.getHeight() - heightBuffer));

		/*
		 * draw the y-axis (leaving 10% of the canvas blank left of the axis, to add
		 * tick marks and labels)
		 */
		g.drawLine((int) widthBuffer - 1, 0, (int) widthBuffer - 1, (int) (this.getHeight() - heightBuffer));

	}

	public void drawHatchMarksAndLabels(Graphics g) {

		if (values.isEmpty() == false) {
			float heightBuffer = this.getHeight() / 20;
			float widthBuffer = this.getWidth() / 20;
			Integer markSize = this.getHeight() / 50;
			Integer numberOfMarks = this.getWidth() / 100;
			Integer startX = Bond.getMinX(), endX = Bond.getMaxX();
			Integer startY = Bond.getMinY(), endY = Bond.getMaxY();

			for (int i = 0; i <= numberOfMarks; i++) {
				g.drawLine((int) (widthBuffer + ((float) i / numberOfMarks) * (this.getWidth() - widthBuffer) - 1),
						this.getHeight() - (int) heightBuffer,
						(int) (widthBuffer + (float) i / numberOfMarks * (this.getWidth() - widthBuffer) - 1),
						(int) (this.getHeight() - heightBuffer - markSize));

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
		drawAxes(g);
		drawHatchMarksAndLabels(g);

		Integer shiftX = 0;
		Integer shiftY = 0;

		if (Bond.getMinX() < 0) {
			shiftX = 0 - Bond.getMinX();
		}
		if (Bond.getMinY() < 0) {
			shiftY = 0 - Bond.getMinY();
		}

		float heightBuffer = this.getHeight() / 20;
		float widthBuffer = this.getWidth() / 20;
		float dotDiameter = this.getWidth() / 110;

		// remove any previously stored dots (otherwise the array list would grow on
		// every repaint)
		shapes.clear();
		g2.setColor(new Color(255, 128, 0));
		for (Bond b : values) {

			float dotWidth = widthBuffer - 1
					+ (float) (b.getXCoordinate() + shiftX) / (Bond.getMaxX() + shiftX) * 19 / 20 * this.getWidth()
					- dotDiameter / 2;
			float dotHeight = this.getHeight() - (heightBuffer
					+ (float) (b.getYCoordinate() + shiftY) / (Bond.getMaxY() + shiftY) * 19 / 20 * this.getHeight()
					+ dotDiameter / 2);

			Shape dot = new Ellipse2D.Float(dotWidth, dotHeight, dotDiameter, dotDiameter);

			g2.fill(dot);
			shapes.add(dot);
		}

		System.out.println();
		System.out.println();
	}
}
