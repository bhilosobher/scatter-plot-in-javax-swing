package assessedExercise3;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class View extends JFrame {
	// constants
	private static final int FRAME_WIDTH = 1290;
	private static final int FRAME_HEIGHT = 690;

	// the Model component: the view has to know about the model, from which it data
	// to paint
	private Model bonds_model;

	// swing components (the protected ones need to be accessed by the controller
	// for GUI updates)
	private JButton closeButton = new JButton("Quit");
	private JButton openButton = new JButton("Open");
	private JLabel fileName = new JLabel("Please open a .csv file");
	protected JPanel bottomPanel = new JPanel();
	protected JTextArea transactionDetail = new JTextArea(
			"\tEach dot represents an exchange bond trade.\nAfter opening the .csv file, click on any dot to view full bond trade information. ");
	protected ScatterPlotComponent scatterPlot;
	protected JComboBox<String> select1;
	protected JComboBox<String> select2;

	// constructor: store the model data, then draw all the GUI that doesn't depend
	// on having read the csv file
	public View(Model bondsModel) {

		bonds_model = bondsModel;
		this.drawInitialGUI();

	}

	void drawInitialGUI() {
		// initialize panels; the main panel will contain the 3 sub-panels

		JPanel topPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		JPanel mainPanel = new JPanel(new BorderLayout());

		// set frame parameters
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Scatter plot");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add components to top and bottom panels
		topPanel.add(openButton);
		topPanel.add(fileName);
		topPanel.add(closeButton);
		bottomPanel.add(transactionDetail);

		// initialize and set up the scatter plot
		scatterPlot = new ScatterPlotComponent(bonds_model);
		scatterPlot.setPreferredSize(new Dimension((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.8)));
		middlePanel.add(scatterPlot);

		// put everything together
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.add(mainPanel);
		this.pack();
	}

	// initiates and draws the combo boxes, with information from the file opened by
	// the user
	void drawAdditionalGUI(String[] columns) {

		// the columns array contains the cokumn names from the csv's file header
		select1 = new JComboBox<String>(columns);
		select2 = new JComboBox<String>(columns);
		select2.setSelectedIndex(1);

		bottomPanel.add(select1);
		bottomPanel.add(select2);
		bottomPanel.setBackground(new Color(255, 128, 0));
	}

	// adding listeners to components

	void addQuitListener(ActionListener quit) {
		closeButton.addActionListener(quit);
	}

	void setLabelName(String name) {
		fileName.setText(name);
	}

	void addOpenFileListener(ActionListener open) {
		openButton.addActionListener(open);
	}

	void addComboBoxListerners(ActionListener combo) {
		select1.addActionListener(combo);
		select2.addActionListener(combo);
	}

	void addScatterPlotListener(MouseListener scatterPlotListener) {
		scatterPlot.addMouseListener(scatterPlotListener);
	}

}
