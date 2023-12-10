package com.group.ProjectB;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;


@Route(value = "")
@CssImport("./styles.css")

@Push
public class MainView extends VerticalLayout {

    Div chatLog;
    HorizontalLayout items;
    Player plr;

    MessageService msg;
    private final Sinks.Many<Message> publisher;
    private final Flux<Message> messages;
    public MainView(Sinks.Many<Message> publisher, Flux<Message> messages) {

        this.publisher = publisher;
        this.messages = messages;
        msg = new MessageService(this.publisher);
        Game game = new Game(msg);
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
        msg.setWidth("100%");
        msg.setHeight("40%");
        msg.addClassName("chatLog");
        section1.add(new H3("Chat"));
//        Scroller logs = new Scroller(chatLog);
//        logs.addClassName("chatLog");
//        logs.setWidth("100%");
//        logs.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
//        section1.add(logs);
        section1.add(msg);
        TextField messageBox = new TextField();
        messageBox.setWidth("100%");
        section1.add(messageBox);


        //Begin
        askName();
        //Messaging

        messageBox.addKeyPressListener(Key.ENTER, key -> {

            Message m = new Message(plr, messageBox.getValue());
            msg.sendMessage(m);
            messageBox.clear();
            messageBox.focus();
        });


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
                        msg.printMessage(message);
                        ui.push();
                    }
            ));

        });

    }


}
