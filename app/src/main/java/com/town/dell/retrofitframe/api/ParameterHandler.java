package com.town.dell.retrofitframe.api;

/**
 * Created by dell on 2019/4/5.
 */
// 处理请求参数
public abstract class ParameterHandler {
    abstract void apply(ServiceMethod method, String value);

    static class QUERY extends ParameterHandler {

        private final String name;
        public QUERY(String name) {
            this.name = name;
        }

        @Override
        void apply(ServiceMethod method, String value) {
            method.addQueryParams(name,value);
        }
    }

    static class FIELD extends ParameterHandler {

        private final String name;
        public FIELD(String name) {
            this.name =name;
        }

        @Override
        void apply(ServiceMethod method, String value) {
            method.addFormFiled(name,value);
        }
    }

    static class PUT extends ParameterHandler {

        private final String value;
        public PUT(String value) {
            this.value =value;
        }

        @Override
        void apply(ServiceMethod method, String value) {

        }
    }

    static class GET extends ParameterHandler {

        private final String value;
        public GET(String value) {
            this.value =value;
        }

        @Override
        void apply(ServiceMethod method, String value) {

        }
    }

    static class POST extends ParameterHandler {

        private final String value;
        public POST(String value) {
            this.value =value;
        }

        @Override
        void apply(ServiceMethod method, String value) {

        }
    }

    static class DELETE extends ParameterHandler {

        private final String value;
        public DELETE(String value) {
            this.value =value;
        }

        @Override
        void apply(ServiceMethod method, String value) {

        }
    }

}
