package comp3013.group3.ebayscraper;

import java.util.*;
import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.annotation.*;

/**
 * Azure Functions with Queue Trigger.
 */
public class Queue {
    /**
     * This function gets executed every 30 seconds.
     */
    @FunctionName("Queue")
        public void functionHandler(@QueueTrigger(name = "myQueueItem", queueName = "ebayScraper", connection = "AzureWebJobsStorage") String myQueueItem, final ExecutionContext executionContext) {
            executionContext.getLogger().info("Queue trigger input: " + myQueueItem);
    }
}