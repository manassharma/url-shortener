package controller;

import cache.JvmCappedCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by manas on 8/26/2017.
 * Handles client calls for saving
 * and retrieving shortened urls
 */
@RestController
@RequestMapping("/shorten")
@EnableSwagger2
public class UrlShortenerController {

    //private Logger log = LoggerFactory.getLogger(UrlShortenerController.class);

    @RequestMapping(value = "/getshort", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateShortUrl(
            @RequestHeader(name = "longurl") String longUrl) {
        if (longUrl == null || longUrl.length() < 1) {
            throw new IllegalArgumentException("long-url provided cannot be null or empty");
        }
        // FIXME: Use custom hashing algorithm rather than this simple timestamp based approach
        // TODO: Use an actual in memory database store rather than this simple JVM based map

        JvmCappedCache instance = JvmCappedCache.getInstance();
        String shortenedUrl = instance.put(longUrl);
        Map<String, String> response = new HashMap<>();
        response.put("shortened.url", shortenedUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getlong", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateLongUrl(
            @RequestHeader(name = "shorturl") String shortUrl) {
        if (shortUrl == null || shortUrl.length() < 1) {
            throw new IllegalArgumentException("long-url provided cannot be null or empty");
        }

        JvmCappedCache instance = JvmCappedCache.getInstance();
        Optional<String> longUrl = instance.get(shortUrl);
        Map<String, String> response = new HashMap<>();
        if (longUrl.isPresent()) {
            response.put("long.url", longUrl.get());
        } else {
            // FIXME: check the distrubuted key-value store (provide implementation)
            throw new IllegalArgumentException("long url not found");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
