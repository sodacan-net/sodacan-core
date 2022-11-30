package net.sodacan.rules;

public class State implements Element {

		private String name;
		private String value;
		private double level;
		
		public State(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public State(String name, double level) {
			this.name = name;
			this.level = level;
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof State) {
				State other = (State) obj;
				if (this.name.equals(other.name) ) return true;
			}
			return false;
		}

		public String getName() {
			return name;
		}
//		public void setName(String name) {
//			this.name = name;
//		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

		public double getLevel() {
			return level;
		}

		public void setLevel(double level) {
			this.level = level;
		}

		@Override
		public String toString() {
			return "State( " + getName() + ", " + getValue() + ", " + getLevel() + ")";
		}

}
