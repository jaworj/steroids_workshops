package com.steroids.example.framework;

import com.stepstone.inbucket.InbucketClient;
import com.stepstone.inbucket.models.Message;
import com.stepstone.inbucket.models.MessageInfo;

import java.io.IOException;
import java.util.List;


public class EmailChecker {

    public void getMessages() throws IOException {
        InbucketClient client = new InbucketClient("");
        List<MessageInfo> mailbox =  client.getMailbox("test");
        for (MessageInfo item : mailbox) {
            Message message = client.getMessage("test",item.id);
            System.out.println(message.subject);
            System.out.println(message.body.html);
        }
    }

}
