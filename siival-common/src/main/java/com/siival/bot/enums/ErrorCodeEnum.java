/*
 * *********************************************************************************************
 *    Copyright (c) 2019. HJZD CO., LTD. All Rights Reserved.
 *    FileName    : ErrorCodeEnum.java
 *    Date        : 2019-4-8
 *    Author      : softy
 *    Module      : ErrorCodeEnum
 *    Function    :
 *  --------------------------------------------------------------------------------------------
 *    Modify History
 *    NO        Date        Modifier        Modified Content
 * *********************************************************************************************
 */

package com.siival.bot.enums;

/**
 * 通用的错误状态码
 *
 * @author
 * @date 2019/4/8
 */
public enum ErrorCodeEnum {

    /**
     * api valid error code start
     */
    API_CODE_HEADER_BLANK(200001,"token为空"),
    API_CODE_HEADER_NOT_EXISTS(200002,"token不存在"),
    API_CODE_HEADER_EXPIRED(200003,"token已过期"),
    API_CODE_HEADER_REQUEST_LIMIT(200004,"token请求太频繁请稍候重试"),

    API_CODE_HEADER_REQUEST_MAX_LIMIT(200005,"token请求次数已到当天最大次数"),
    API_CODE_HEADER_USER_BLANK(200006,"您的小程序版本太低，请清空缓存之后再次进入"),
    API_CODE_HEADER_USER_NO_RIGHTS(200007,"您无权操作此用户"),
    /**
     * Gl 9999100 error code enum.
     */
    GL9999100(9999100, "参数异常"),
    /**
     * Gl 9999101 error code enum.
     */
    GL9999101(9999101, "长时间未操作"),
    /**
     * Gl 9999102 error code enum.
     */
    GL9999102(9999102, "接口调用异常"),
    /**
     * Gl 9999103 error code enum.
     */
    GL9999103(9999103, "文件创建失败"),
    /**
     * Gl 9999104 error code enum.
     */
    GL9999104(9999104, "文件保存失败"),
    /**
     * Gl 9999105 error code enum.
     */
    GL9999105(9999105, "文件上传失败"),
    /**
     * Gl 9999106 error code enum.
     */
    GL9999106(9999106, "文件类型错误"),
    /**
     * Gl 9999107 error code enum.
     */
    GL9999107(9999107, "文件解析失败"),
    /**
     * Gl 9999108 error code enum.
     */
    GL9999108(9999108, "文件下载失败"),
    /**
     * Gl 9999109 error code enum.
     */
    GL9999109(9999109, "文件不存在"),
    /**
     * Gl 9999110 error code enum.
     */
    GL9999110(9999110, "文件缺失"),
    /**
     * Gl 9999201 error code enum.
     */
    GL9999201(9999201, "配置错误"),
    /**
     * Gl 9999202 error code enum.
     */
    GL9999202(9999202, "节点过多，切换到局部视图"),
    /**
     * Gl 9999401 error code enum.
     */
    GL9999401(9999401, "无访问权限"),
    /**
     * Gl 000500 error code enum.
     */
    GL9999500(9999500, "未知异常"),
    /**
     * Gl 000403 error code enum.
     */
    GL9999403(9999403, "无操作权限"),
    /**
     * Gl 000404 error code enum.
     */
    GL9999404(9999404, "非法操作"),
    /**
     * Gl 9999001 error code enum.
     */
    GL9999001(9999001, "注解使用错误"),
    /**
     * Gl 9999002 error code enum.
     */
    GL9999002(9999002, "微服务不在线,或者网络超时"),
    /**
     * Uac 10010001 error code enum.
     */
//	 1001 用户中心
    UAC10010001(10010001, "会话超时,请刷新页面重试"),
    /**
     * Uac 10010002 error code enum.
     */
    UAC10010002(10010002, "TOKEN解析失败"),
    /**
     * Uac 10010003 error code enum.
     */
    UAC10010003(10010003, "TOKEN生成失败"),
    /**
     * Uac 10010004 error code enum.
     */
    UAC10010004(10010004, "TOKEN数量达到上限"),
    /**
     * Uac 10011001 error code enum.
     */
    UAC10011001(10011001, "认证失败，请重新登录"),
    /**
     * Uac 10011002 error code enum.
     */
    UAC10011002(10011002, "请先登录后访问"),
    /**
     * Uac 10011003 error code enum.
     */
    UAC10011003(10011003, "找不到用户,loginName=%s"),
    /**
     * Uac 10011004 error code enum.
     */
    UAC10011004(10011004, "找不到用户,email=%s"),
    /**
     * Uac 10011006 error code enum.
     */
    UAC10011006(10012006, "手机号不能为空"),
    /**
     * Uac 10011007 error code enum.
     */
    UAC10011007(10011007, "登录名不能为空"),
    /**
     * Uac 10011008 error code enum.
     */
    UAC10011008(10011008, "新密码不能为空"),
    /**
     * Uac 10011009 error code enum.
     */
    UAC10011009(10011009, "确认密码不能为空"),
    /**
     * Uac 10011010 error code enum.
     */
    UAC10011010(10011010, "两次密码不一致"),
    /**
     * Uac 10011011 error code enum.
     */
    UAC10011011(10011011, "用户不存在"),
    /**
     * Uac 10011012 error code enum.
     */
    UAC10011012(10011012, "登录名已存在"),
    /**
     * Uac 10011013 error code enum.
     */
    UAC10011013(10011013, "手机号已存在"),
    /**
     * Uac 10011014 error code enum.
     */
    UAC10011014(10011014, "密码不能为空"),
    /**
     * Uac 10011016 error code enum.
     */
    UAC10011016(10011016, "用户名或密码错误"),
    /**
     * Uac 10011017 error code enum.
     */
    UAC10011017(10011017, "验证类型错误"),
    /**
     * Uac 10011018 error code enum.
     */
    UAC10011018(10011018, "邮箱不能为空"),
    /**
     * Uac 10011019 error code enum.
     */
    UAC10011019(10011019, "邮箱已存在"),
    /**
     * Uac 10011020 error code enum.
     */
    UAC10011020(10011020, "该账户被禁用"),
    /**
     * Uac 10011021 error code enum.
     */
    UAC10011021(10011021, "登录已过期，请重新登录"),
    /**
     * Uac 10011022 error code enum.
     */
    UAC10011022(10011022, "账号已过期"),

    /**
     * Uac 10011022 error code enum.
     */
    UAC10011023(10011023, "原密码错误"),

    /**
     * Uac 10011013 error code enum.
     */
    UAC10011024(10011024, "身份证号已绑定"),


    // ----------------------------- 业务异常 ------------------------------
    /**
     * BIZ 30010001 error code enum.
     */
    BIZ30010001(30010001, "频繁调用"),
    /**
     * BIZ 30010002 error code enum.
     */
    BIZ30010002(30010002, "重复调用"),
    /**
     * BIZ 30010003 error code enum.
     */
    BIZ30010003(30010003, "状态非法"),
    /**
     * BIZ 30010004 error code enum.
     */
    BIZ30010004(30010004, "资源不足"),
    /**
     * BIZ 30010005 error code enum.
     */
    BIZ30010005(30010005, "资源已加载"),
    /**
     * BIZ 30010006 error code enum.
     */
    BIZ30010006(30010006, "资源未加载"),
    /**
     * BIZ 30010007 error code enum.
     */
    BIZ30010007(30010007, "资源未找到"),
    /**
     * BIZ 30010008 error code enum.
     */
    BIZ30010008(30010008, "资源加载失败"),

    BIZ30010009(30010009, "用户未绑定机构"),

    BIZ30010010(30010010, "用户未绑定车辆"),
    /**
     * BIZ 30010020 error code enum.
     */
    BIZ30010020(30010020, "内部连接失败"),


    ;


    private int code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     *
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }

}
