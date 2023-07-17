package ru;

public class MessageUtil {
    private String name;
    private String message;

    public static String ser(MessageUtil util) {
        var s = new StringBuilder();
        s.append("name=").append(util.getName()).append(";");
        s.append("message=").append(util.getMessage());

        return s.toString();
    }

    public static MessageUtil deser(String s) {
        var util = new MessageUtil();
        var arr = s.split(";");
        for (var arg : arr) {
            var params = arg.split("=");
            if (params[0].equals("name")) util.setName(params[1]);
            else if (params[0].equals("message")) util.setMessage(params[1]);
        }
        return util;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
