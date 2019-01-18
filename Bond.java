package assessedExercise3;

//the Bond class is my data container. It has a dual function: FIRST, to store the CSV data neatly in a way that eases
//manipulation and SECOND to remember, for each bond, which values the user wants displayed (painted) on the X and Y axes.

class Bond {

	//
	private Integer yield;
	private Integer daysToMaturity;
	private Integer value;
	private Integer xCoordinate, yCoordinate;
	private static Integer maxX = 0, maxY = 0, minX = 100000, minY = 100000;

	public Bond(Integer yield, Integer daysToMaturity, Integer value) {
		this.yield = yield;
		this.daysToMaturity = daysToMaturity;
		this.value = value;
		this.xCoordinate = yield;
		this.yCoordinate = daysToMaturity;
	}

	public Integer getYield() {
		return this.yield;
	}

	public Integer getDaysToMaturity() {
		return this.daysToMaturity;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setYield(Integer yield) {
		this.yield = yield;
	}

	public void setDaysToMaturity(Integer daysToMaturity) {
		this.daysToMaturity = daysToMaturity;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setXCoordinate(Integer coordinate) {
		this.xCoordinate = coordinate;
	}

	public void setYCoordinate(Integer coordinate) {
		this.yCoordinate = coordinate;
	}

	public Integer getXCoordinate() {
		return this.xCoordinate;
	}

	public Integer getYCoordinate() {
		return this.yCoordinate;
	}

	public static void setMaxX(Integer coordinate) {
		maxX = coordinate;
	}

	public static void setMaxY(Integer coordinate) {
		maxY = coordinate;
	}

	public static Integer getMaxX() {
		return maxX;
	}

	public static Integer getMaxY() {
		return maxY;
	}

	public static void setMinX(Integer coordinate) {
		minX = coordinate;
	}

	public static void setMinY(Integer coordinate) {
		minY = coordinate;
	}

	public static Integer getMinX() {
		return minX;
	}

	public static Integer getMinY() {
		return minY;
	}

	// this method will be used for displaying Bond information when the user clicks
	// on a dot; it converts the
	// yield percentage from Integer back to double for display (and only for
	// display)
	public String toString() {
		return "Details of selected trade:\tYield: " + this.yield / 1000 + "." + this.yield % 1000
				+ "%\r\nDays to maturity: " + this.daysToMaturity + "\tBond value(chf): " + this.value;
	}

}
