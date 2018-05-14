package pl.sda.poznan;

import java.io.Serializable;

public class Message implements Serializable {

    private String header;
    private String data;
    private Character playerSign;

    public String getHeader() {
        return header;
    }

    public String getData() {
        return data;
    }

    public Character getPlayerSign() {
        return playerSign;
    }

    //step 5
    public static MessageBuilder builder(){
        return new MessageBuilder();
    }

    //DESIGN PATTERN - builder (steps 1-5)
    public static class MessageBuilder{
        //step 1 : same fields like in class Message
        private String header;
        private String data;
        private Character playerSign;

        //step 4 : private constructor
        private MessageBuilder(){
        }

        //step 2
        public MessageBuilder header (String header){
            this.header = header;
            return this;
        }

        public MessageBuilder data (String data){
            this.data = data;
            return this;
        }

        public MessageBuilder playerSign (Character playerSign){
            this.playerSign = playerSign;
            return this;
        }

        //step 3
        public Message build() {
            Message message = new Message();
            message.header = this.header;
            message.data = this.data;
            message.playerSign = this.playerSign;
            return message;
        }


    }
}
