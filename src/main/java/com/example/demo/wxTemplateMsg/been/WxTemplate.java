package com.example.demo.wxTemplateMsg.been;


public class WxTemplate{
        private String blockUp;
        private String id;

        private String eventName;

        private String template;

        private String templateName;

        private String aimUser;//针对用户

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlockUp() {
        return blockUp;
    }

    public void setBlockUp(String blockUp) {
        this.blockUp = blockUp;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAimUser() {

        return aimUser;
    }

    public void setAimUser(String aimUser) {
        this.aimUser = aimUser;
    }
}
