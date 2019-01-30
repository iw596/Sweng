package tests;
import dataStructure.DataStructure;
import static org.junit.Assert.*;
import org.junit.Test;

public class DataStructureTest {
	@Test
	public void oneStructure() throws Exception{
		DataStructure single_string_structure = new DataStructure("Cat");
		assertEquals("Cat",single_string_structure.getData());
	}

}
