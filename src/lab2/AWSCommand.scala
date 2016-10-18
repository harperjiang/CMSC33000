package lab2

import scala.sys.process._
import com.google.gson._
import java.io.File
import java.text.DateFormat
import java.util.Date
import java.text.SimpleDateFormat

class AWSCommand(cmd: Array[String], params: Map[String, String]) {

  def run: AWSResponse = {
    var plist = params.flatMap(f => { List("--" + f._1, f._2) })
    var cmdline = cmd.toSeq ++ plist
    return new AWSResponse(new JsonParser().parse(cmdline !!))
  }
}

object AWSCommand {
  def apply(cmd: Array[String], params: Map[String, String]) = new AWSCommand(cmd, params)

  def createSpotReq(price: Double) = {
    new AWSCommand(Array("aws", "ec2", "request-spot-instances"),
      Map(("instance-count", "1"),
        ("type", "one-time"),
        ("launch-specification", "file:///home/harper/aws_workspace/micro.json"),
        ("spot-price", price.toString())))
  }

  def checkSpotReqAll(id: String) = {
    if (null == id || id.isEmpty)
      new AWSCommand(Array("aws", "ec2", "describe-spot-instance-requests"),
        Map.empty[String, String])
    else
      new AWSCommand(Array("aws", "ec2", "describe-spot-instance-requests"),
        Map(("spot-instance-request-ids", id)))
  }

  def getSpotPriceHist(start: Date, end: Date) = {
    var dateFormat = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ")
    var startTs = dateFormat.format(start)
    var endTs = dateFormat.format(end)
    new AWSCommand(Array("aws", "ec2", "describe-spot-price-history"),
      Map(("instance-types", "t1.micro"),
        ("product-description", "Linux/UNIX"),
        ("start-time", startTs),
        ("end-time", endTs)))
  }
}

class AWSResponse(json: JsonElement) {

  def get(path: String): Any = {
    var steps = path.split("\\.")
    var obj = json
    steps.foreach(step => {
      try {
        var i = Integer.parseInt(step);
        obj = obj.getAsJsonArray.get(i)
      } catch {
        case e: NumberFormatException => {
          obj = obj.getAsJsonObject.get(step)
        }
      }
    })
    try {
      obj.getAsInt
    } catch {
      case ex: Exception => obj.getAsString
    }
  }
}