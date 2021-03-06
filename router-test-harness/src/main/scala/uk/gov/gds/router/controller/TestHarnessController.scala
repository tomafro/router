package uk.gov.gds.router.controller

import org.scalatra.ScalatraFilter
import javax.servlet.http.Cookie
import scala.collection.JavaConversions._

class TestHarnessController extends ScalatraFilter {

  before() {
    response.setContentType("text/html")
  }

  get("/test/test-harness") {
    output(dumpParams)
  }

  get("/someprefix/unregistered") {
    output("unregistered")
  }

  get("/someprefix") {
    output("prefix route")
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

  get("/test/timeout") {
    Thread.sleep(20000)
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
    halt(500)
  }

  get("/test/410") {
    halt(410)
  }
  
  get("/test/incoming-headers") {
    output(dumpHeaders)
  }

  get("/test/incoming-cookies") {
    output(dumpCookies)
  }

  get("/test/set-allowed-headers") {
    response.addHeader("Cache-Control", "cache-control-value")
    response.addHeader("Content-Type", "content-type-value")
    response.addHeader("ETag", "etag-value")
    response.addHeader("Expires", "expires-value")
    response.addHeader("Last-Modified", "last-modified-value")
    response.addHeader("Location", "location-value")
    response.addHeader("Set-Cookie", "set-cookie-value")
    response.addHeader("Vary", "vary-value")
    response.addHeader("WWW-Authenticate", "www-authenticate-value")
  }

  get("/test/set-forbidden-headers") {
    response.addHeader("X-Rack-Cache", "test")
  }

  get("/test/outgoing-cookies") {
    response.addCookie(new Cookie("test-cookie", "this is a cookie"))
  }

  get("/test/not-modified") {
    halt(304)
  }

  post("/test/not-modified") {
    halt(304)
  }

  get("/test/runtime-exception") {
    throw new RuntimeException("exception")
  }

  get("/test/exception") {
    throw new Exception("exception")
  }

  private def output(block: => String) =
    <html>
      <head>
        <title>Test harness</title>
      </head>
      <body>
        {block}
      </body>
    </html>

  private def dumpHeaders = request.getHeaderNames().toSeq.map {
    case headerName: String =>
      headerName + "=" + request.getHeader(headerName)
  }.mkString("\n")

  private def dumpCookies = request.multiCookies.map {
    case (name, values) => values.map(name + "=" + _)
  }.flatten.mkString("\n")

  private def dumpParams = multiParams.map {
    case (paramName, values) => values.map(paramName + "=" + _)
  }.flatten.mkString("\n")
}
