package com.eimbee.ecommerce.user.domain.model.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class ErrorVO {
    private String message = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date date = null;

    public ErrorVO() {
        this.message = "Error desconocido";
        this.date = new Date();
    }

    public ErrorVO(String message) {
        this.message = StringUtils.trimToEmpty(message);
        this.date = new Date();
    }

    @Schema(name = "message", description = "Mensaje de la respuesta", required = true, example = "Error desconocido")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Schema(name = "date", description = "Fecha del message", required = true, example = "2018-11-21 13:21:52")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
