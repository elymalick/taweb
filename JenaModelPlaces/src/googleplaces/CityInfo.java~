package googleplaces;

import java.util.List;

import JenaUtils.DumpString;

public class CityInfo {
	
	private List<component> results;
	public List<component> getResults(){
		return results;
	}
	
	
	@Override
	public String toString() {
		return DumpString.dumpString(this);
	}
	public class component{
		private List<adr_comp> address_components;
		private String formatted_address;
		private Geometry geometry;
		
		public List<adr_comp> getAddress_components() {
			return address_components;
		}
		public void setAddress_components(List<adr_comp> address_components) {
			this.address_components = address_components;
		}
		public String getFormatted_address() {
			return formatted_address;
		}
		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}
		public Geometry getGeometry() {
			return geometry;
		}
		public void setGeometry(Geometry geometry) {
			this.geometry = geometry;
		}
		
		
		@Override
		public String toString() {
			return DumpString.dumpString(this);
		}
	}
	
	public class adr_comp{
		private String long_name;
		private String short_name;
		private List<String> types;
		

		public String getLong_name() {
			return long_name;
		}
		public void setLong_name(String long_name) {
			this.long_name = long_name;
		}
		public String getShort_name() {
			return short_name;
		}
		public void setShort_name(String short_name) {
			this.short_name = short_name;
		}
		public List<String> getTypes() {
			return types;
		}
		public void setTypes(List<String> types) {
			this.types = types;
		}
		
		@Override
		public String toString() {
			return DumpString.dumpString(this);
		}
	}
	
}
