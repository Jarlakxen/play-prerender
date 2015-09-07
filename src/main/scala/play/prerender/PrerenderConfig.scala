package play.prerender

case class PrerenderConfig(
  prerenderUrlEndpoint: String,
  token: Option[String] = None,
  socketTimeout: Long = 1000,
  forwardedURLHeader: Option[String] = None,
  crawlerUserAgents: Seq[String] = Seq("googlebot", "yahoo", "bingbot", "baiduspider",
    "facebookexternalhit", "twitterbot", "rogerbot", "linkedinbot", "embedly"),

  extensionsToIgnore: Seq[String] = Seq(".js", ".css", ".less", ".png", ".jpg", ".jpeg",
    ".gif", ".pdf", ".doc", ".txt", ".zip", ".mp3", ".rar", ".exe", ".wmv", ".doc", ".avi", ".ppt", ".mpg",
    ".mpeg", ".tif", ".wav", ".mov", ".psd", ".ai", ".xls", ".mp4", ".m4a", ".swf", ".dat", ".dmg",
    ".iso", ".flv", ".m4v", ".torrent"),
  whitelist: Seq[String] = Seq.empty,
  blacklist: Seq[String] = Seq.empty)
