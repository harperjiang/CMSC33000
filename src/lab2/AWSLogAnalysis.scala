package lab2

import com.google.gson.JsonParser
import java.io.File
import java.io.InputStreamReader
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.text.ParsePosition
import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

object AWSLogAnalysis extends App {

  def parse: scala.collection.mutable.Map[String, Buffer[SpotPriceLog]] = {
    var json = new JsonParser().parse(new InputStreamReader(new FileInputStream("aws_price_log")))

    var datas = json.getAsJsonObject.get("SpotPriceHistory").getAsJsonArray

    var logs = new scala.collection.mutable.HashMap[String, Buffer[SpotPriceLog]]();

    datas.foreach { elem =>
      {
        if (elem.isJsonObject()) {
          var obj = elem.getAsJsonObject

          var ts = obj.get("Timestamp").getAsString
          var price = obj.get("SpotPrice").getAsString
          var az = obj.get("AvailabilityZone").getAsString

          logs.getOrElse(az, new ArrayBuffer[SpotPriceLog]()) += new SpotPriceLog(ts, price, az)
        }
      }
    }
    return logs
  }

  /**
   * Simulate running of a spot instance at the given time and price
   * @param date Start time of the instance
   * @param price Bidding price
   * @param az Availability Zone
   * @return (wait: how long the instance wait to start, last: how long the instance runs)
   *
   */
  def simulate(date: Date, price: Double, az: String): (Long, Long) = {
    null
  }
}

class SpotPriceLog(ts: String, priceStr: String, az: String) {
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

  var timestamp: Date = dateFormat.parse(ts, new ParsePosition(0))
  var price: Double = priceStr.toDouble

}