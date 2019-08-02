package com.zsr.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Demo class
 * 用于作为json数据返回的类
 * 封装了：状态信息
 *        提示信息
 *        要传输的数据
 * @author shourenzhang
 * @date 2019/7/28 13:47
 */
public class AjaxMessage {
    /**
     * 状态码
     * 成功：100
     * 失败：200
     * */
    private Integer code;

    /**
     * 提示信息
     * 成功或失败所要提示的信息
     * */
    private String message;

    /**
     * 要传输的数据，以map的形式存储
     */
    private Map<String,Object> info = new HashMap<>();

    public static AjaxMessage success(){
        AjaxMessage result = new AjaxMessage();
        result.setCode(100);
        result.setMessage("成功");
        return result;
    }

    public static AjaxMessage fail(){
        AjaxMessage result = new AjaxMessage();
        result.setCode(200);
        result.setMessage("失败！");
        return result;
    }

    public static AjaxMessage success(String successMessage){
        AjaxMessage result = new AjaxMessage();
        result.setCode(100);
        result.setMessage(successMessage);
        return result;
    }

    public static AjaxMessage fail(String failMessage){
        AjaxMessage result = new AjaxMessage();
        result.setCode(200);
        result.setMessage(failMessage);
        return result;
    }

    public AjaxMessage addInfo(String key, Object value){
        this.getInfo().put(key,value);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
