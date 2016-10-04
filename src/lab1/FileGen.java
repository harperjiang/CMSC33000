package lab1;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Random;
import java.util.UUID;

/**
 * Generate JSON input for the OpenWhisk action
 * 
 * @author harper
 *
 */
public class FileGen {

	public static void main(String[] args) throws Exception {
		genFile(10);
		genFile(100);
		genFile(110);
		genFile(120);
		genFile(130);
		genFile(140);
		genFile(150);
		genFile(160);
		genFile(170);
		genFile(180);
		genFile(190);
	}

	protected static void genFile(int n) throws Exception {
		Random rand = new Random(System.currentTimeMillis());

		PrintWriter pw = new PrintWriter(new FileOutputStream(
				MessageFormat.format("data/{0}", String.valueOf(n))));

		pw.println(MessageFormat.format("'{'\"_id\":\"{0}\",\"files\":[",UUID.randomUUID().toString()));

		for (int i = 0; i < n; i++) {
			// Determine num of chars
			int numChar = 100 + rand.nextInt(900);
			int numLine = 1 + rand.nextInt(49);

			int charPerLine = numChar / numLine;

			StringBuffer content = new StringBuffer();
			for (int l = 0; l < numLine - 1; l++) {
				for (int c = 0; c < charPerLine; c++) {
					content.append((char) ('a' + rand.nextInt(26)));
				}
				content.append("\\n");
			}

			for (int c = 0; c < charPerLine + (numChar % numLine); c++) {
				content.append((char) ('a' + rand.nextInt(26)));
			}

			pw.println(MessageFormat.format(
					"'{'\"name\":{0}, \"content\":\"{1}\"'}'", String.valueOf(i),
					content.toString()));

			if (i != n - 1) {
				pw.println(",");
			}
		}
		pw.println("]}");
		pw.close();
	}
}
