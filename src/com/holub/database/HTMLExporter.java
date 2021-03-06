package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {
	private Writer writer;

	public HTMLExporter(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void startTable() throws IOException {
		writer.append("<html>\r\n<table border=1>\r\n");
	}

	@Override
	public void storeMetadata(String tableName, int width, int height, Iterator<Object> columnNames)
			throws IOException {
		createRowHtml(columnNames, "th");
	}

	private void createRowHtml(Iterator<Object> columnNames, String tag) throws IOException {
		writer.append("<tr>");
		while (columnNames.hasNext()) {
			Object c = columnNames.next();
			writer.append("<" + tag + ">");
			writer.append(c.toString());
			writer.append("</" + tag + ">");
		}
		writer.append("</tr>\r\n");
	}

	@Override
	public void storeRow(Iterator<Object> data) throws IOException {
		createRowHtml(data, "td");
	}

	@Override
	public void endTable() throws IOException {
		writer.append("</table>\r\n</html>");
	}
}
