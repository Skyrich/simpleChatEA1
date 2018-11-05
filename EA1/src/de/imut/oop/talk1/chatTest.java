package de.imut.oop.talk1;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class chatTest {
	
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	private final ByteArrayOutputStream error = new ByteArrayOutputStream();
	private PrintStream originalOut = System.out;
	private PrintStream originalErr = System.err;
	
	@Before
	void before() {
		System.setOut(new PrintStream(output));
		System.setErr(new PrintStream(error));
	}
	
	@After
	void after() {
		System.setOut(originalOut);
		System.setErr(originalOut);
	}
	

	@Test
	void testInOut() throws InterruptedException {
		//Sender instance = new Sender("localhost",2050,"unitTest");
		String args[] = null;
		
		String input = "This is a Test sentence!";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		
		
		
		
		Sender.main(args);
		System.setIn(in);
		output.toString();
		Receiver.main(args);
		Thread.sleep(1000);

	}

}
