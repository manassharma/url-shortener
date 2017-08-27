package cache;

import java.util.Optional;

/**
 * Created by manas on 8/26/2017.
 * Contract between custom simple capped
 * cache that lives in the jvm and
 * external cloud instance of distributed
 * cache.
 */
public interface CacheProvider {

    String put(String longUrl);

    Optional<String> get(String shortUrl);
}
