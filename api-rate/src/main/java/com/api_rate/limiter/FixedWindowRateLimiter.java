package com.api_rate.limiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FixedWindowRateLimiter {
    private final int maxRequest;
    private final long windowSizeInMiliSecond;

    private final Map<String,RateLimitInfo> userLimit= new ConcurrentHashMap<>() ;

    public FixedWindowRateLimiter(int maxRequest, long windowSizeInMiliSecond) {
        this.maxRequest = maxRequest;
        this.windowSizeInMiliSecond = windowSizeInMiliSecond;
    }

    public boolean allowedRequest(String userId){
        long currentTime =System.currentTimeMillis();

        RateLimitInfo info =userLimit.compute(userId,(key,existing)->{
            if (existing==null){
                return new RateLimitInfo(currentTime,1);
            }
            long windowStart= existing.getWindowStartTime();

            if (currentTime-windowStart>= windowSizeInMiliSecond){
                existing.reset(currentTime);
                return existing;
            }

            if (existing.getRequestCount() <= maxRequest){
                existing.increment();
                return existing;
            }

            return existing;
        });
        return info.getRequestCount() < maxRequest;

    }
}
