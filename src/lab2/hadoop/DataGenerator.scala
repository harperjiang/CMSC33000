package lab2.hadoop

import scala.util.Random
import java.io.FileOutputStream
import java.io.PrintWriter

object DataGenerator extends App {
  
  var rand = new Random(System.currentTimeMillis())
  
  var key = 50000
  var value = 100000000
  
  var output = new PrintWriter(new FileOutputStream("source"))
  
  for(i<-0 to value) {
    output.println("%d\t%d".format(rand.nextInt(key),rand.nextInt(value)))
  }
  
  output close
}