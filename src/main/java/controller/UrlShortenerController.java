package controller;

import cache.JvmCappedCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by manas on 8/26/2017.
 * Handles client calls for saving
 * and retrieving shortened urls
 */
@RestController
@RequestMapping("shorten")
public class UrlShortenerController {

    private Logger log = LoggerFactory.getLogger(UrlShortenerController.class);

    @RequestMapping(value = "{longUrl}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateShortUrl(
            @PathVariable(name = "{longUrl}") String longUrl) {
        if (longUrl == null || longUrl.length() < 1) {
            throw new IllegalArgumentException("long-url provided cannot be null or empty");
        }
        // FIXME: Use custom hasing algorithm rather than this simple timestamp based approach
        // TODO: Use an actual in memory database store rather than this simple JVM based map

        JvmCappedCache instance = JvmCappedCache.getInstance();
        String shortenedUrl = instance.put(longUrl);
        Map<String, String> response = new HashMap<>();
        response.put("shortened.url", shortenedUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "{shortUrl}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateLongUrl(
            @PathVariable(name = "{shortUrl}") String shortUrl) {
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
