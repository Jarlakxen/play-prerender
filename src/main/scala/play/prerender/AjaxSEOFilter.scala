package play.prerender

import play.api.http._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.Play.current
import play.api.libs.ws._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import java.util.regex.Pattern

class AjaxSEOFilter(config: PrerenderConfig) extends Filter {

 def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    if (isCrawler(request)) {
      getFromPrerender(request).map(Ok(_).as("text/html"))
    } else {
      next(request)
    }
  }

  def isCrawler(request: RequestHeader): Boolean = {
    val userAgent = request.headers.get(HeaderNames.USER_AGENT)
    val url = getRequestURL(request);

    if (!request.method.equals(HttpVerbs.GET)) {
      return false;
    }

    if (isInResources(url)) {
      return false;
    }

    if (!isInWhiteList(url)) {
      return false;
    }

    if (isInBlackList(url)) {
      return false;
    }

    if (hasEscapedFragment(request)) {
      return true;
    }

    userAgent match {
      case Some(userAgent) if userAgent.nonEmpty => isCrawlerUserAgent(userAgent)
      case _ => false
    }
  }

  def currentURL(request: RequestHeader) =  "http" + (if (request.secure) "s" else "") + "://" + request.host + request.uri

  def getFromPrerender(request: RequestHeader) = {
    val req = WS.url(config.prerenderUrlEndpoint + currentURL(request))
    config.token.foreach(token => req.withHeaders("X-Prerender-Token" -> token))
    req.get().map(_.body)
  }

  def getRequestURL(request: RequestHeader) = config.forwardedURLHeader.map(request.headers.get(_).get).getOrElse(request.path)

  def hasEscapedFragment(request: RequestHeader) = request.getQueryString("_escaped_fragment_").isDefined

  def isInBlackList(url: String) = config.blacklist.nonEmpty && config.blacklist.exists { regex =>
    val pattern = Pattern.compile(regex);
    pattern.matcher(url).matches()
  }

  def isInWhiteList(url: String) = config.whitelist.isEmpty || config.whitelist.exists { regex =>
    val pattern = Pattern.compile(regex);
    pattern.matcher(url).matches()
  }

  def isCrawlerUserAgent(userAgent: String) = config.crawlerUserAgents.exists(item => userAgent.toLowerCase().contains(item.toLowerCase()))

  def isInResources(url: String) = config.extensionsToIgnore.exists(item => url.contains(item.toLowerCase()))
}

object AjaxSEOFilter {
  def apply(config: PrerenderConfig) = new AjaxSEOFilter(config)
  def apply(prerenderUrlEndpoint: String, token: Option[String] = None, socketTimeout: Long = 1000) = new AjaxSEOFilter(PrerenderConfig(prerenderUrlEndpoint, token, socketTimeout))
}
