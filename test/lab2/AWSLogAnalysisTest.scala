package lab2

import scala.collection.mutable.ArrayBuffer

import org.junit.Test
import org.junit.Assert._

class AWSLogAnalysisTest {

  @Test
  def testBinsearch = {
    var list = new ArrayBuffer[Int]()
    list ++= List(0, 4, 5, 9, 13, 25, 33, 35, 53, 61, 91, 122, 310, 425);
    var intList = list.map { Integer.valueOf(_) }
    assertEquals(0, AWSLogAnalysis.binsearch[Integer](intList, -1))
    assertEquals(-1, AWSLogAnalysis.binsearch[Integer](intList, 999))
    assertEquals(1, AWSLogAnalysis.binsearch[Integer](intList, 4))
    assertEquals(3, AWSLogAnalysis.binsearch[Integer](intList, 6))
    assertEquals(11, AWSLogAnalysis.binsearch[Integer](intList, 95))
    assertEquals(7, AWSLogAnalysis.binsearch[Integer](intList, 34))
    assertEquals(8, AWSLogAnalysis.binsearch[Integer](intList, 52))
  }
}