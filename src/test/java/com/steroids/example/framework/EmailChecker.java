package com.steroids.example.framework;

import com.stepstone.inbucket.InbucketClient;
import com.stepstone.inbucket.models.Message;
import com.stepstone.inbucket.models.MessageInfo;

import java.io.IOException;
import java.util.List;


public class EmailChecker {

    public void getMessages(String mailboxName) throws IOException {
        InbucketClient client = new InbucketClient("http://ec2-34-244-6-12.eu-west-1.compute.amazonaws.com/");
        List<MessageInfo> mailbox =  client.getMailbox(mailboxName);
        for (MessageInfo item : mailbox) {
            Message message = client.getMessage(mailboxName,item.id);
            System.out.println(message.subject);
            System.out.println(message.body.html);
        }
    }

}
