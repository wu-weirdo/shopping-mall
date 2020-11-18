package com.edaochina.shopping.domain.constants;

/**
 * MessageConstants
 *
 * @author wangpenglei
 * @since 2019/9/18 10:54
 */
public interface MessageConstants {

    enum Stage {

        /**
         * 平台枚举
         */
        APP(0, "APP"),
        BACK(1, "后台"),
        MINI_PROGRAM(2, "小程序");

        /**
         * code
         */
        private Integer code;

        /**
         * 中文值
         */
        private String text;

        Stage(Integer code, String text) {
            this.code = code;
            this.text = text;
        }

        public static String getText(Integer code) {
            for (Stage stage : Stage.values()) {
                if (stage.code.equals(code)) {
                    return stage.text;
                }
            }
            throw new RuntimeException("平台枚举,无效的code：" + code);
        }
    }

    enum Kind {

        /**
         * 消息类型
         */
        ACTIVITY(1, "活动消息"),
        STAGE(2, "平台通告"),
        SYSTEM(0, "系统消息");
        /**
         * code
         */
        private Integer code;

        /**
         * 中文值
         */
        private String text;

        Kind(Integer code, String text) {
            this.code = code;
            this.text = text;
        }

        public static String getText(Integer code) {
            for (Kind kind : Kind.values()) {
                if (kind.code.equals(code)) {
                    return kind.text;
                }
            }
            throw new RuntimeException("消息类型,无效的code：" + code);
        }
    }

    enum Mode {
        /**
         * 推送方式
         */
        FULL(0, "全量推送"),
        RATION(1, "定量推送");
        /**
         * code
         */
        private Integer code;

        /**
         * 中文值
         */
        private String text;

        Mode(Integer code, String text) {
            this.code = code;
            this.text = text;
        }

        public static String getText(Integer code) {
            for (Mode mode : Mode.values()) {
                if (mode.code.equals(code)) {
                    return mode.text;
                }
            }
            throw new RuntimeException("推送方式,无效的code：" + code);
        }
    }

    interface Role extends RoleConstants {
        int ALL = 0;
    }

}
