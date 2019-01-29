package assessedExercise3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import assessedExercise3.Bond;

public class Controller {
	private View bond_view;
	private Model bond_model;
	private ScatterPlotComponent scatterPlot;

	public Controller(Model financial_model, View financial_view) {
		bond_view = financial_view;
		bond_model = financial_model;
		scatterPlot = financial_view.scatterPlot;

		// add action listeners to the view's components
		bond_view.addQuitListener(new QuitListener());
		bond_view.addOpenFileListener(new OpenListener());
		bond_view.addScatterPlotListener(new ScatterPlotListener());
	}

	class OpenListener implements ActionListener {

		String currentDirectory = System.getProperty("user.dir");
		String selectedFileName;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser j = new JFileChooser(currentDirectory);
			int returnValue = j.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {

				File selectedFile = j.getSelectedFile();
				selectedFileName = selectedFile.getName();
				bond_view.setLabelName(selectedFileName);

				// read transaction data from file then store it
				bond_model.readBondData(selectedFile.getAbsolutePath());
				if(bond_model.getBonds()!=null) {
					
				}
				String[] columns = bond_model.getColumnNames();
				
				if (bond_view.select1 != null) {
					bond_view.bottomPanel.remove(bond_view.select1);
					bond_view.bottomPanel.remove(bond_view.select2);
					bond_view.bottomPanel.revalidate();
					bond_view.bottomPanel.repaint();
				}

				bond_view.drawAdditionalGUI(columns);
				bond_view.addComboBoxListerners(new ComboBoxListener());

				// set the max and min X and Y values to those in the YIELD and DAYS_TO_MATURITY
				Integer[] values = bond_model.findMaxMin();
				Bond.setMaxX(values[0]);
				Bond.setMinX(values[1]);
				Bond.setMaxY(values[2]);
				Bond.setMinY(values[3]);

				// once the data is loaded, update the view to display the scatter plot
				bond_view.repaint();
			}
		}
	}

	class ScatterPlotListener implements MouseListener {
		@Override
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			//when the user clicks somewhere, see if they clicked a dot; if they did, update
			//label with bond information 
			String description = scatterPlot.selectDot(x, y);
			bond_view.transactionDetail.setText(description);
		}

		// Do-nothing methods
		public void mouseReleased(MouseEvent event) {
		}

		public void mouseClicked(MouseEvent event) {
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}
	}

	class QuitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	class ComboBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//see what the user selected in the combo voxes
			String selectedItem1 = (String) bond_view.select1.getSelectedItem();
			String selectedItem2 = (String) bond_view.select2.getSelectedItem();
			//see what the names of the columns are
			String[] columns = bond_model.getColumnNames();
			Integer[] values = bond_model.findMaxMin();
			
			//compare selected item with name of column; set value from selected column
			//as the value to be painted in the scatterplot
			if (selectedItem1.equals(columns[0])) {
				for (Bond b : bond_model.getBonds()) {
					b.setXCoordinate(b.getYield());
				}
				Bond.setMaxX(values[0]);
				Bond.setMinX(values[1]);
				
			} else if (selectedItem1.equals(columns[1])) {
				for (Bond b : bond_model.getBonds()) {
					b.setXCoordinate(b.getDaysToMaturity());
				}
				Bond.setMaxX(values[2]);
				Bond.setMinX(values[3]);
			} else if (selectedItem1.equals(columns[2])) {
				for (Bond b : bond_model.getBonds()) {
					b.setXCoordinate(b.getValue());
				}
				Bond.setMaxX(values[4]);
				Bond.setMinX(values[5]);
			}

			if (selectedItem2.equals(columns[0])) {
				for (Bond b : bond_model.getBonds()) {
					b.setYCoordinate(b.getYield());
				}
				Bond.setMaxY(values[0]);
				Bond.setMinY(values[1]);
			} else if (selectedItem2.equals(columns[1])) {
				for (Bond b : bond_model.getBonds()) {
					b.setYCoordinate(b.getDaysToMaturity());
				}
				Bond.setMaxY(values[2]);
				Bond.setMinY(values[3]);
			} else if (selectedItem2.equals(columns[2])) {
				for (Bond b : bond_model.getBonds()) {
					b.setYCoordinate(b.getValue());
				}
				Bond.setMaxY(values[4]);
				Bond.setMinY(values[5]);
			}

			// when a new item is selected, update the graphics (scatter plot) based on the new data
			bond_view.scatterPlot.repaint();
		}
	}
}
