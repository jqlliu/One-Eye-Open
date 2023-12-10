package com.group.ProjectB;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;


@Route(value = "")
@CssImport("./styles.css")
public class MainView extends VerticalLayout {

    Div chatLog;
    HorizontalLayout items;
    Player plr;
    private final Sinks.Many<Message> publisher;
    private final Flux<Message> messages;
    public MainView(Sinks.Many<Message> publisher, Flux<Message> messages) {

        Game game = new Game();

        this.publisher = publisher;
        this.messages = messages;

        addClassName("mainView");
        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);

        H1 header = new H1("Application");
        header.getElement().getThemeList().add("dark");

        add(header);
        //main layout
        items = new HorizontalLayout();
        items.setWidth("100%");
        items.setHeight("100%");
        add(items);
        items.setVisible(false);
        //sections
        VerticalLayout section1 = new VerticalLayout();
        section1.setWidth("40%");
        section1.setHeight("100%");
        items.add(section1);
        section1.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        //Section 1
        chatLog = new Div();
        chatLog.setWidth("100%");
        chatLog.setHeight("100%");
        chatLog.addClassName("chatLog");
        section1.add(new H3("Chat"));
//        Scroller logs = new Scroller(chatLog);
//        logs.addClassName("chatLog");
//        logs.setWidth("100%");
//        logs.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
//        section1.add(logs);
        section1.add(chatLog);
        TextField messageBox = new TextField();
        messageBox.setWidth("100%");
        section1.add(messageBox);


        //Begin
        askName();
        //Messaging

        messageBox.addKeyPressListener(Key.ENTER, key -> {
            Message msg = new Message(plr, messageBox.getValue());
            publisher.tryEmitNext(msg);
            messageBox.clear();
            messageBox.focus();
        });


    }

    void printMessage(Message msg){
        Paragraph newMsg = new Paragraph(msg.from.name + ": " + msg.content);

        chatLog.add(newMsg);
        newMsg.scrollIntoView();
        System.out.println(plr.name);
    }
    void sendMessage(String content){

    }
    Sinks.EmitFailureHandler handle = new Sinks.EmitFailureHandler() {
        @Override
        public boolean onEmitFailure(SignalType signalType, Sinks.EmitResult emitResult) {
            return false;
        }
    };
    void askName(){
        TextField fld = new TextField();

        HorizontalLayout username = new HorizontalLayout(new H4("Name: "), fld);

        fld.addKeyPressListener(Key.ENTER, key -> {
            plr = new Player(fld.getValue());
            start();
            username.setVisible(false);
            System.out.println("?");
        });
        add(username);

    }
    void start(){

        items.setVisible(true);
        messages.subscribe(message -> {
            getUI().ifPresent( ui -> ui.access(
                    () -> {
                        printMessage(message);
                    }
            ));

        });

    }
}
