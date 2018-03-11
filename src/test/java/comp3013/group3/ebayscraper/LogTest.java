package comp3013.group3.ebayscraper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LogTest {

	@Test
	public void logTest() throws Exception {
		Logger log = LogManager.getLogger(LogTest.class.getName());
		log.warn("Log Test");
	}
}
