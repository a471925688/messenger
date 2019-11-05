package com.noah.solutions.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 2018 11 30 lijun
 * 存放常用的配置信息
 */
@Component
public class ProjectConfig {

    public static  String FILE_TEMPORARY_DIRECTORY;
    public static  String FILE_IMAGE_DIRECTORY;
    public static  String   PROJECT_PATH;

    @Value("${project.path}")
    public void setProjectPath(String projectPath) {
        PROJECT_PATH = projectPath;
    }

    @Value("${file.directory.temporary}")
    public  void setFileTemporaryDirectory(String fileTemporaryDirectory) {
        FILE_TEMPORARY_DIRECTORY = fileTemporaryDirectory;
    }

    @Value("${file.directory.image}")
    public  void setFileImageDirectory(String fileImageDirectory) {
        FILE_IMAGE_DIRECTORY = fileImageDirectory;
    }


    /**
     * 用户类型1.管理员.2.会员.3.站點工作人員.4.司機
     */
    public static enum UserType {
        ADMIN(1),  MEMBER(2),STAFF(3),DRIVER(4);
        private final Integer id;
        UserType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "管理员";
                case 2:return "会员";
                case 3:return "工作人員";
                case 4:return "司機";
            }
            return "异常";
        }
    }

    /**
     * 充值方式(1.操作员.2.派送员)
     */
    public static enum StaffType {
        OPERATOR(1),COURIER(2);
        private final Integer id;
        StaffType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "操作员";
                case 2:return "派送员";
            }
            return "异常";
        }
    }


    /**
     * 充值方式(1.银行转账.2.现金支付)
     */
    public static enum RechargeMode {
        BANKTRANSFER(1),CASHPAYMENT(2);
        private final Integer id;
        RechargeMode(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "银行转账";
                case 2:return "现金支付";
            }
            return "异常";
        }
    }

    /**
     * 收货方式(1.自提.2.送货上门)
     */
    public static enum ReceAddresMode {
        SELFMENTION(1),DELIVERYABOVE(2);
        private final Integer id;
        ReceAddresMode(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "自提";
                case 2:return "送货上门";
            }
            return "异常";
        }
    }

    /**
     * 消息類型(1.自提.2.送货上门)
     */
    public static enum MessageType {
        SYSTEM(1),ORDER(2);
        private final Integer id;
        MessageType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "系統消息";
                case 2:return "訂單狀態改變通知";
            }
            return "异常";
        }
    }



    /**
     * 收货方式(1.待出庫.2.運輸中,3.待領取(自提),4.待派送,5.派送中,6.已完成,7.異常)
     */
    public static enum OrderState {
        STOCK(1),TRANSIT(2),UNCLAIMED(3),NOTDISPATCHED(4),DISPATCH(5),COMPLETED(6),ABNORMAL(7);
        private final Integer id;
        OrderState(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "待出庫";
                case 2:return "運輸中";
                case 3:return "待領取(自提)";
                case 4:return "待派送";
                case 5:return "派送中";
                case 6:return "已完成";
                case 7:return "異常訂單";
            }
            return "系統异常";
        }
    }

    /**
     * 收货方式(1.待出庫.2.運輸中,3.待領取(自提),4.待派送,5.派送中,6.已完成,7.異常)
     */
    public static enum LabelStatus {
        STOCK(1),SEND(2);
        private final Integer id;
        LabelStatus(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "未出庫";
                case 2:return "已出庫";
            }
            return "系統异常";
        }
    }




}
