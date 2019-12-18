# xatkit Rest Platform

Provides actions to send REST requests



## Usage example

```
intent HowIsTheWeather {
	inputs {
		"How is the weather today in XXX?"
		"What is the forecast for today in XXX?"
	}
	creates context Weather {
		sets parameter "cityName" from fragment "XXX" (entity any)
	}
}

on intent HowIsTheWeather do
	val city = context.get("Weather").get("cityName") as String
	val queryParamters = newHashMap
	queryParamters.put("q", city)
	val response = RestPlatform.GetJsonRequest("http://api.openweathermap.org/data/2.5/weather", queryParamters, emptyMap, emptyMap)
	if(response.status === 200){
		val temp = Math.round(response.body.asJsonObject?.get("main").asJsonObject.get("temp").asDouble)
		val temp_min = Math.round(response.body.asJsonObject.get("main").asJsonObject.get("temp_min").asDouble)
		val temp_max = Math.round(response.body.asJsonObject.get("main").asJsonObject.get("temp_max").asDouble)
		val weather =  response.body.asJsonObject.get("weather").asJsonArray.get(0).asJsonObject.get("description").asString
		val weather_icon =  "http://openweathermap.org/img/wn/" +  response.body.asJsonObject.get("weather").asJsonArray.get(0).asJsonObject.asJsonObject.get("icon").asString+".png"	
		ReactPlatform.Reply("The current weather is  "+temp+" &deg;C with "+weather+" !["+weather+"]("+weather_icon+") with a high of "+temp_max+" &deg;C and a low of "+temp_min+" &deg;C ")
	
		}
		else if(response.status === 404) {
			ReactPlatform.Reply("Oops, I could not find this city")
		}
```
![Screenshot](https://raw.githubusercontent.com/xatkit-bot-platform/xatkit-rest-platform/master/example/weatherbot.png)
