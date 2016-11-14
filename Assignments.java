public class Assignments {
	private String assignment;
	private int percentage;

	public Assignments(String assignment, int percentage) {
		this.assignment = assignment;
		this.percentage = percentage;
	}

	public String getAssignment() {
		return assignment;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setAssignment (String assignment) {
		this.assignment = assignment;
	}

	public void setPercentage (int percentage) {
		this.percentage = percentage;
	}
}
