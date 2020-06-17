package com.goblincwl.dragontwilight.entity.slave;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-06-17 17:52
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mailbox_player", schema = "mc_base")
public class MailboxPlayer {
    private Integer mail;
    private String sender;
    private String recipient;
    private String topic;
    private String text;
    private Timestamp sendtime;
    private String filename;

    @Id
    @Column(name = "MAIL", nullable = false)
    public Integer getMail() {
        return mail;
    }

    public void setMail(Integer mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "SENDER", nullable = true, length = 32)
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "RECIPIENT", nullable = true, length = 255)
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Basic
    @Column(name = "TOPIC", nullable = true, length = 32)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Basic
    @Column(name = "TEXT", nullable = true, length = 255)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "SENDTIME", nullable = true)
    public Timestamp getSendtime() {
        return sendtime;
    }

    public void setSendtime(Timestamp sendtime) {
        this.sendtime = sendtime;
    }

    @Basic
    @Column(name = "FILENAME", nullable = true, length = 32)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailboxPlayer that = (MailboxPlayer) o;
        return Objects.equals(mail, that.mail) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(topic, that.topic) &&
                Objects.equals(text, that.text) &&
                Objects.equals(sendtime, that.sendtime) &&
                Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, sender, recipient, topic, text, sendtime, filename);
    }
}
