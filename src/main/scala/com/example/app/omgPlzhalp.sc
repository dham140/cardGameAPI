
import dispatch._, Defaults._

import org.json4s._, org.json4s.native.JsonMethods._

val request = dispatch.url(s"https://www.googleapis.com/language/translate/v2?key=AIzaSyAsC-FOmfHdkFP-B_xIOafh9PrP1QHx3_g&source=en&target=ru&q=hello")

val response = dispatch.Http(request OK dispatch.as.json4s.Json)

val responseJson = response()

val temp: List[String] = for{ JObject(data) <- responseJson
  JField("translatedText", JString(translatedText)) <- data
} yield translatedText

temp.length  match {
  case 1 => temp(0)
  case _ => "IT BROKE"
}


//responseJson: String = {
//  "data": {
//    "translations": [
//  {
//    "translatedText": "Здравствуйте"
//  }
//    ]
//  }
//}
