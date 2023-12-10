package com.group.ProjectB;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import reactor.core.publisher.Sinks;

public class MessageService extends Div {

    Sinks .Many<Message> publisher;
    MessageService( Sinks .Many<Message> publisher){

        this.publisher = publisher;
    }
    public void printMessage(Message msg){
        Paragraph newMsg = new Paragraph(msg.from.name + ": " + msg.content);
        add(newMsg);
        //newMsg.scrollIntoView();
    }

    public void sendMessage(Message msg){
        publisher.tryEmitNext(msg);
    }
    @Override
    public void add(Component... components){
        super.add(components);

        //components[components.length-1].getElement().callJsFunction("scrollIntoView");
    }

}
