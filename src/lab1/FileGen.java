package lab1;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Random;

/**
 * Generate JSON input for the OpenWhisk action
 * 
 * @author harper
 *
 */
public class FileGen {

	public static void main(String[] args) throws Exception {
		Random rand = new Random(System.currentTimeMillis());

		int n = 10;

		PrintWriter pw = new PrintWriter(new FileOutputStream(MessageFormat.format("data/{0}", n)));

		pw.println("{\"files\":[");
		
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
			
			pw.println(MessageFormat.format("'{'\"name\":{0}, \"content\":\"{1}\"'}'",i,content.toString()));

			if(i != n-1) {
				pw.println(",");
			}
		}
		pw.println("]}");
		pw.close();
	}
}
