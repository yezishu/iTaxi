package yzs.commonlibrary.data.model;

/**
 * Des：注册model
 * create by Zishu.Ye on 2017/11/7  20:06
 */
public class RegisterModel {

    /**
     * target : {"telno":null,"driverid":"402881e45f8c74ee015f8c75846d0000"}
     * token : b36249ec698e4c67b400721494534f7a
     */

    private TargetBean target;
    private String token;

    public TargetBean getTarget() {
        return target;
    }

    public void setTarget(TargetBean target) {
        this.target = target;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class TargetBean {
        /**
         * telno : null
         * driverid : 402881e45f8c74ee015f8c75846d0000
         */

        private Object telno;
        private String driverid;

        public Object getTelno() {
            return telno;
        }

        public void setTelno(Object telno) {
            this.telno = telno;
        }

        public String getDriverid() {
            return driverid;
        }

        public void setDriverid(String driverid) {
            this.driverid = driverid;
        }
    }
}
