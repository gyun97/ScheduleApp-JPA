package kdg.service;

import kdg.dto.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private static final String WEATHER_API_URL = "https://f-api.github.io/f-api/weather.json";


    public List<WeatherData> getWeatherData() {
        WeatherData[] weatherDataArray = restTemplate.getForObject(WEATHER_API_URL, WeatherData[].class);
        Arrays.stream(weatherDataArray).forEach(System.out::println);
        return List.of(weatherDataArray);
    }
}
