package com.api_rate.limiter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateLimitInfo {
    private long windowStartTime;
    private int requestCount;

    public void increment(){
         this.requestCount++;
    }

    public void reset(long newWindowStartTime){
        this.requestCount=1;
        this.windowStartTime=newWindowStartTime;
    }
}
