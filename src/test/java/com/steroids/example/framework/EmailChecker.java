package com.steroids.example.framework;

import com.stepstone.inbucket.InbucketClient;
import com.stepstone.inbucket.models.Message;
import com.stepstone.inbucket.models.MessageInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class EmailChecker {

  private static final String HOST = "http://ec2-34-244-6-12.eu-west-1.compute.amazonaws.com/";
  public final InbucketClient client = new InbucketClient(HOST);
  private Logger log = LogManager.getLogger(this);
  private static final String NEW_LINE_REPLACEMENT_REGEX = "(\\r|\\n)";

  public void getMessages(String mailboxName) throws IOException {
    InbucketClient
        client =
        new InbucketClient(HOST);
    List<MessageInfo> mailbox = client.getMailbox(mailboxName);
    for (MessageInfo item : mailbox) {
      Message message = client.getMessage(mailboxName, item.id);
      System.out.println(message.subject);
      System.out.println(message.body.html);
    }
  }

  public Message getMessage(MessageInfo messageInfo) {
    try {
      return client.getMessage(messageInfo.mailbox, messageInfo.id);
    } catch (IOException e) {
      log.error("Inbucket expection while connecting", e);
    }
    return null;
  }

  public String getMessageUrl(String recipientAddress, String subject)
      throws MailNotFoundException {
    try {
      Message message = getMessage(getMessageInfo(recipientAddress, subject));
      return HOST + "mailbox/" + message.mailbox + "/" + message.id + "/html";
    } catch (IOException e) {
      log.error("Could not retrieve mailbox from Inbucket", e);
    }
    return "";
  }

  private MessageInfo getMessageInfo(String recipientAddress, String subject)
      throws IOException, MailNotFoundException {
    String mailboxName = recipientAddress.split("@")[0];
    List<MessageInfo> mailboxItems = client.getMailbox(mailboxName);

    Collections.reverse(mailboxItems);
    Optional<MessageInfo> foundMessage = mailboxItems.stream()
        .filter(messageInfo ->
                    subject.replaceAll(NEW_LINE_REPLACEMENT_REGEX, "")
                        .equals(messageInfo.subject.replaceAll(NEW_LINE_REPLACEMENT_REGEX, "")))
        .findFirst();

    if (foundMessage.isPresent()) {
      return foundMessage.get();
    } else {
      throw new MailNotFoundException(
          String.format("Mail to '%s', with subject '%s' not found", recipientAddress, subject));
    }
  }

}
