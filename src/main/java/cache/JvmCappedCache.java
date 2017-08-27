package cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by manas on 8/26/2017.
 * Capped cache that lives in the
 * JVM.
 */
public class JvmCappedCache implements CacheProvider{

    private static JvmCappedCache jvmCache;
    private Map<String, String> cacheMap = new LinkedHashMap<>();
    int capSize = 500;

    /**
     * @return Singleton instance
     */
    public static JvmCappedCache getInstance(){
        if(jvmCache == null){
            synchronized (JvmCappedCache.class){
                jvmCache = new JvmCappedCache();
            }
        }
        return jvmCache;
    }

    /**
     * Put item in the capped cache
     * that lives in the JVM. This
     * evicts the last element when
     * the capped size is reached.
     * @param longUrl
     * @return
     */
    public String put(String longUrl) {
        String shortUrl = getShortUrl(longUrl);
        if(this.capSize > 0){
            // TODO: replace with custom hashing algorithm
            this.cacheMap.put(shortUrl, longUrl);
        }
        else{
            // Evict last element and insert new at the beginning
            String currLast = "";
            for(String key: this.cacheMap.keySet()){
                currLast = key;
            }
            this.cacheMap.remove(currLast);
            this.cacheMap.put(shortUrl, longUrl);
        }
        return shortUrl;
    }

    /**
     * Get item from the capped cache
     * that lives in the JVM
     * @return
     */
    public Optional<String> get(String shortUrl) {
        return Optional.ofNullable(this.cacheMap.get(shortUrl));
    }

    private synchronized String getShortUrl(String longUrl){
        StringBuilder builder = new StringBuilder("http://urlshortener/");
        builder.append(System.currentTimeMillis());
        return builder.toString();
    }
}
