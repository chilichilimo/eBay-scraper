package comp3013.group3.ebayscraper;

import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.annotation.*;

/**
 * Azure Functions with Queue Trigger.
 */
public class Queue {
    /**
     * This function gets executed every day at 2AM.
     */
    @FunctionName("Queue")
        public void functionHandler(@QueueTrigger(name = "myQueueItem", queueName = "ebayScraper", connection = "AzureWebJobsStorage") String myQueueItem, final ExecutionContext context) {
            context.getLogger().info("Queue trigger input: " + myQueueItem);
    }
}