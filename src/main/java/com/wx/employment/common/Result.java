package com.wx.employment.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wbl
 * 数据封装对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    /**
     * 是否成功，即后端业务逻辑是否成功执行业务
     */
    public boolean success;
    /**
     * 自定义状态码
     */
    private int code;
    /**
     * message, 说明信息
     */
    private String msg;
    /**
     * 传递的数据
     */
    private T data;

    /**
     * 执行成功，状态码是200
     * @return
     */
    public static <T> Result<T> executeSuccess(String msg, T data) {
        return new Result<T>(true, 200, msg, data);
    }

    public static <T> Result<T> executeSuccess(String msg) {
        return new Result<T>(true, 200, msg, null);
    }

    public static <T> Result<T> executeSuccess() {
        return new Result<T>(true, 200, "", null);
    }

    public static  <T> Result<T> delSuccess(){
        return executeSuccess("删除成功");
    }
    public static  <T> Result<T> insertSuccess(){
        return executeSuccess("插入成功");
    }
    public static  <T> Result<T> updSuccess(){
        return executeSuccess("更新成功");
    }
    public static  <T> Result<T> getSuccess(T data){
        return executeSuccess("获取数据成功", data);
    }

    /**
     * 执行失败，返回状态码和msg
     */
    public static <T> Result<T>executeFailure(int code, String msg) {
        return new Result<T>(false, code, msg, null);
    }

    /**
     * 失败，默认400
     */
    public static <T> Result<T>executeFailure(String msg) {
        return new Result<T>(false, 400, msg, null);
    }

    public static <T> Result<T>updFailure() {
        return executeFailure("更新失败");
    }
    public static <T> Result<T>delFailure() {
        return executeFailure("删除失败");
    }
    public static <T> Result<T>insertFailure() {
        return executeFailure("插入失败");
    }
    public static <T> Result<T>getFailure() {
        return executeFailure("获取数据失败");
    }



    /**
     * 权限不足
     * */
    public static  <T> Result<T> noAuth(){
        return executeFailure(401, "权限不足");
    }

    /**
     * 未注册
     * */
    public static  <T> Result<T> noRegister(){
        return executeFailure(403, "未注册");
    }

    /**
     * 未登录
     * */
    public static  <T> Result<T> noLogin(){
        return executeFailure(401, "未登录");
    }

}
