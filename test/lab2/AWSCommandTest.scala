package lab2

import org.junit.Test
import org.junit.Assert._

class AWSCommandTest {

  @Test
  def testRun = {
    var cmd = AWSCommand(Array("aws", "ec2", "describe-spot-fleet-requests"),
      Map(("spot-fleet-request-ids", "sfr-b2f0adb2-4c55-4536-88fb-a27180c2852e")))
    var response = cmd.run
    assertEquals("sfr-b2f0adb2-4c55-4536-88fb-a27180c2852e", response.get("SpotFleetRequestConfigs.0.SpotFleetRequestId"))
    assertEquals("error", response.get("SpotFleetRequestConfigs.0.ActivityStatus"))
    assertEquals("active", response.get("SpotFleetRequestConfigs.0.SpotFleetRequestState"))
  }
}
