package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HTMLExporterTest {
	private Table.Exporter html;
	private MockWriter writer;

	class MockWriter extends Writer {
		String resultString = "";

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			char[] arr = new char[len];
			for (int i = 0; i < len; ++i) {
				arr[i] = cbuf[off+i];
			}
			String line = new String(arr);
			resultString += line;
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void close() throws IOException {
		}
	}

	@BeforeEach
	public void setUp() {
		writer = new MockWriter();
		html = new HTMLExporter(writer);
	}
	
	@Test
	void testStartEndTable() throws IOException {
		html.startTable();
		html.endTable();
		String expected = "<html>\r\n"
				+ "<table border=1>\r\n"
				+ "</table>\r\n"
				+ "</html>";
		Assert.assertEquals(expected, writer.resultString);
	}

	void testMetadata() throws IOException {
		Object[] columns = new String[] {"last", "first", "addrId"};
		Iterator<Object> iterator = Arrays.asList(columns).iterator();
		html.storeMetadata(null, 0, 0, iterator);
		String expected = "<tr><th>last</th><th>first</th><th>addrId</th></tr>\r\n";
		Assert.assertEquals(expected, writer.resultString);
	}

	void testRows() throws IOException {
		html.storeRow(Arrays.asList(new Object[] {"Holub", "Allen", 1}).iterator());
		html.storeRow(Arrays.asList(new Object[] {"Flintstone","Wilma","2"}).iterator());
		html.storeRow(Arrays.asList(new Object[] {"Flintstone","Fred","2"}).iterator());
		String expected = "<tr><th>last</th><th>first</th><th>addrId</th></tr>\r\n"
				+ "<tr><td>Holub</td><td>Allen</td><td>1</td></tr>\r\n"
				+ "<tr><td>Flintstone</td><td>Wilma</td><td>2</td></tr>\r\n"
				+ "<tr><td>Flintstone</td><td>Fred</td><td>2</td></tr>\r\n";
		Assert.assertEquals(expected, writer.resultString);
	}
}
