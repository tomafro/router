package uk.gov.gds.router.controller

import org.scalatra.ScalatraFilter
import uk.gov.gds.router.util.Logging
import com.google.inject.Singleton
import javax.servlet.http.Cookie

@Singleton
class TestHarnessController extends ScalatraFilter with Logging {

  before(){
    response.setContentType("text/html")
  }

  get("/test/test-harness") {
    output(dumpParams)
  }

  get("/test/test-harness/") {
    output(dumpParams)
  }

  post("/test/test-harness") {
    output(dumpParams)
  }

  get("/foo") {
    output("fooOnly")
  }

  get("/foo/*") {
    output("fooOnly")
  }

  get("/football") {
    output("football")
  }

  get("/test/redirect") {
    redirect("http://www.alphagov.co.uk")
  }

  post("/test/redirect") {
    redirect("http://www.alphagov.co.uk")
  }

  get("/test/this-route-returns-an-error") {
    output("broken route")
    halt(500)
  }

  get("/test/incoming-cookies") {
    output(dumpCookies)
  }

  get("/test/outgoing-cookies") {
    response.addCookie(new Cookie("test-cookie", "this is a cookie"))
  }

  private def output(block: => String) = {
    val output =
      <html>
        <head>
          <title>Test harness</title>
        </head>
        <body>
          {block}
        </body>
      </html>

    logger.info("Response: {}", output)
    output
  }

  private def dumpCookies = request.multiCookies.map {
    case (name, values) => values.map(name + "=" + _)
  }.flatten.mkString("\n")

  private def dumpParams = multiParams.map {
    case (paramName, values) => values.map(paramName + "=" + _)
  }.flatten.mkString("\n")
}