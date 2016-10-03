package lab1;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Random;

public class FileGen {

	public static void main(String[] args) throws Exception {
		Random rand = new Random(System.currentTimeMillis());

		int n = 100;

		for (int i = 0; i < n; i++) {
			PrintWriter pw = new PrintWriter(new FileOutputStream(MessageFormat.format("data/{0}", i)));
			// Determine num of chars
			int numChar = 100 + rand.nextInt(900);
			int numLine = 1 + rand.nextInt(49);

			int charPerLine = numChar / numLine;

			for (int l = 0; l < numLine - 1; l++) {
				for (int c = 0; c < charPerLine; c++) {
					pw.print((char) ('a' + rand.nextInt(26)));
				}
				pw.println();
			}

			for (int c = 0; c < charPerLine + (numChar % numLine); c++) {
				pw.print((char) ('a' + rand.nextInt(26)));
			}
			pw.println();

			pw.close();
		}
	}
}
