package assessedExercise3;

import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//the Model class has the function of reading (and then storing) data from a CSV file as Bonds (of the Bond class) 

public class Model {
	private ArrayList<Bond> bonds = new ArrayList<Bond>();
	private String[] columnNames = new String[3];

	public Model() {
		ArrayList<Bond> bonds = new ArrayList<Bond>();
	}

	public String[] getColumnNames() {
		return this.columnNames;
	}

	public ArrayList<Bond> getBonds() {
		return this.bonds;
	}

	// finds the min and max value in each of the 3 columns from the input file and
	// returns an array containing them
	public Integer[] findMaxMin() {
		Integer maxY = 0;
		Integer minY = 99999;
		Integer maxD = 0;
		Integer minD = 99999;
		Integer maxV = 0;
		Integer minV = 99999;

		for (Bond b : bonds) {
			if (b.getDaysToMaturity() > maxD)
				maxD = b.getDaysToMaturity();
			if (b.getValue() > maxV)
				maxV = b.getValue();
			if (b.getYield() > maxY)
				maxY = b.getYield();
			if (b.getDaysToMaturity() < minD)
				minD = b.getDaysToMaturity();
			if (b.getValue() < minV)
				minV = b.getValue();
			if (b.getYield() < minY)
				minY = b.getYield();
		}
		Integer[] maxMin = { maxY, minY, maxD, minD, maxV, minV };
		return maxMin;
	}

	// given the name of a CSV file, parses each line of the file and returns an
	// ArrayList of Bond objects each corresponding to a line
	public ArrayList<Bond> readBondData(String fileName) {

		// remove any data left from a previous reading
		bonds.clear();

		FileReader fr = null;
		Scanner s = null;
		try {
			fr = new FileReader(fileName);
			s = new Scanner(fr);
			columnNames = s.nextLine().split(",");
			String line = "";
			while (s.hasNextLine()) {
				line = s.nextLine();
				String[] attributes = line.split(",");
				Bond bond = createBond(attributes);
				bonds.add(bond);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Error reading file");
		}
		return bonds;
	}

	// parse each of the lines read as a String from the file into a Bond object,
	// then return that Bond
	// cast the double "yield" value to int after multiplying by 1000 and then to
	// Integer (Integer is more efficient)
	// I do this because when drawing coordinates have to be integers; i will still
	// display "yield" as a double.
	private static Bond createBond(String[] CSVdata) {

		int preliminaryYield = (int) (Double.parseDouble(CSVdata[0]) * 1000);
		Integer yield = preliminaryYield;
		Integer daysToMaturity = Integer.parseInt(CSVdata[1]);
		Integer value = Integer.parseInt(CSVdata[2]);

		return new Bond(yield, daysToMaturity, value);
	}

}
