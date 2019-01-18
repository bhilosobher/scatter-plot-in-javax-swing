package assessedExercise3;

public class BondsMCV {

	public static void main (String[] args) {
		
		Model bondsModel = new Model();
		View scatterPlotView = new View(bondsModel); 
		Controller scatterPlotController = new Controller(bondsModel,scatterPlotView);
		scatterPlotView.setVisible(true);
		
	}
}
