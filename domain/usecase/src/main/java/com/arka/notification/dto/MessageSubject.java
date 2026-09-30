package com.arka.notification.dto;

public enum MessageSubject {
    LOW_STOCK_REPORT("Low stock report"),
    WEEK_SALES_REPORT("Weekly sales report"),
    ORDER_STATUS_UPDATED("Order: %s, Status update: %s");

    private final String template;

    MessageSubject(String template){
        this.template = template;
    }

    public String resolve(Object... args){

        if(args == null || args.length == 0)
            return template;

        return String.format(this.template, args);
    }
}
