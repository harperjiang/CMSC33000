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
import java.io.FileOutputStream
import java.io.PrintWriter

object AWSLogAnalysis {
  val DAY = 86400000
  val HOUR = 3600000

  var logs: scala.collection.Map[String, Buffer[SpotPriceLog]] = null;

  def main(args: Array[String]) = {
    parse("aws_price_log_t1micro")

    var prices = Array(0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009, 0.01)
    //var prices = Array(0.005)

    var azs = Array("us-west-2a", "us-west-2b", "us-west-2c")
    var starttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-10-12 00:00:00")

    var printFormat = new SimpleDateFormat("HH")
    azs.foreach(az => {
      var outfile = new PrintWriter(new FileOutputStream("result-%s".format(az)))
      outfile.println("time\tprice\twait\tlast")
      for (i <- 0 to 23) {
        var time = new Date(starttime.getTime + i * HOUR)

        prices.foreach(price => {
          var simu = simulate(time, price, az)
          outfile println ("%s\t%.3f\t%.2f\t%.2f"
            .format(printFormat.format(time),
              price,
              simu._1.toDouble / HOUR,
              simu._2.toDouble / HOUR))
        })
      }
      outfile close
    })

    var outfile = new PrintWriter(new FileOutputStream("result-dynsimu"))
    outfile.println("time\tprice\twait\tlast")
    for (i <- 0 to 23) {
      var time = new Date(starttime.getTime + i * HOUR)

      prices.foreach(price => {
        var simus = new ArrayBuffer[(Long, Long)]();
        azs.foreach(az => {
          simus += simulate(time, price, az)
        })

        // Look for the best region
        var simu = simus.maxBy(f => f._2 -> f._1)

        outfile println ("%s\t%.3f\t%.2f\t%.2f"
          .format(printFormat.format(time),
            price,
            simu._1.toDouble / HOUR,
            simu._2.toDouble / HOUR))
      })
    }
    outfile close
  }
  /**
   * Simulate running of a spot instance at the given time and price
   * @param date Start time of the instance
   * @param price Bidding price
   * @param az Availability Zone
   * @return (wait: how long the instance wait to start, last: how long the instance runs)
   *
   */
  def simulate(date: Date, bidprice: Double, az: String): (Long, Long) = {

    var data = logs.getOrElse(az, Buffer.empty[SpotPriceLog])

    var index = binsearch(data.map { _.timestamp }, date)

    if (index == -1)
      throw new IllegalArgumentException(date.toString())

    var wait = 0L
    var last = 0L

    var startTime = date;
    var price = data(index - 1).price;

    if (price > bidprice) {
      // wait
      wait = data(index).timestamp.getTime - date.getTime
      while (price > bidprice && wait <= HOUR) {
        price = data(index).price;
        wait = data(index).timestamp.getTime - date.getTime
        index += 1
      }
      if (wait > HOUR) {
        return (HOUR, 0)
      }
    }

    while (price <= bidprice && last <= DAY) {
      price = data(index).price
      last = data(index).timestamp.getTime - startTime.getTime
      index += 1
    }
    if (last > DAY) {
      last = DAY
    } else {

    }
    return (wait, last)
  }

  def binsearch[T <: Comparable[T]](data: Buffer[T], target: T): Int = {
    if (data(0).compareTo(target) > 0)
      return 0
    if (data.last.compareTo(target) < 0)
      return -1
    return binsearch(data, target, 0, data.length)
  }

  private def binsearch[T <: Comparable[T]](data: Buffer[T], target: T, start: Int, end: Int): Int = {
    if (start >= end)
      return start
    if (end == start + 1)
      return end
    var half = (start + end) / 2

    target.compareTo(data(half)) match {
      case x if x < 0 => { binsearch(data, target, start, half) }
      case x if x > 0 => { binsearch(data, target, half, end) }
      case _ => { half }
    }
  }

  /**
   *
   */
  def parse(fname: String) = {
    var json = new JsonParser().parse(new InputStreamReader(new FileInputStream(fname)))

    var datas = json.getAsJsonObject.get("SpotPriceHistory").getAsJsonArray

    var logs = new scala.collection.mutable.HashMap[String, Buffer[SpotPriceLog]]();

    datas.foreach { elem =>
      {
        if (elem.isJsonObject()) {
          var obj = elem.getAsJsonObject

          var ts = obj.get("Timestamp").getAsString
          var price = obj.get("SpotPrice").getAsString
          var az = obj.get("AvailabilityZone").getAsString

          logs.getOrElseUpdate(az, new ArrayBuffer[SpotPriceLog]()) += new SpotPriceLog(ts, price, az)
        }
      }
    }

    this.logs = logs.mapValues { _.sortBy(_.timestamp) }
  }

}

class SpotPriceLog(ts: String, priceStr: String, az: String) {
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

  var timestamp: Date = dateFormat.parse(ts, new ParsePosition(0))
  var price: Double = priceStr.toDouble
}