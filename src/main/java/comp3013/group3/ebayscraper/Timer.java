package comp3013.group3.ebayscraper;

import java.util.*;
import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.annotation.*;

/**
 * Azure Functions with Timer Trigger.
 */
public class Timer {
    /**
     * This function gets executed every 30 seconds.
     */
    @FunctionName("Timer")
    @QueueOutput(name = "myQueueItem", queueName = "ebayScraper", connection = "AzureWebJobsStorage")
        public String functionHandler(@TimerTrigger(name = "timerInfo", schedule = "*/30 * * * * *") String timerInfo, final ExecutionContext context) {
            context.getLogger().info("Timer trigger input: " + timerInfo);
            return "From timer: \"" + timerInfo + "\"";
    }
}