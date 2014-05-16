package com.ncut.reg.utils;

/**
 * Created by IntelliJ IDEA.
 * User: DELL
 * Date: 12-9-19
 * Time: 下午7:27
 * To change this template use File | Settings | File Templates.
 */
public class ColumnAttrObj {
    private String column_name;
    private String data_type;
    private String column_type;
    private String column_comment;

    private int varchardata_length=0;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getColumn_type() {
        return column_type;
    }

    public void setColumn_type(String column_type) {
        this.column_type = column_type;
    }

    public String getColumn_comment() {
        return column_comment;
    }

    public void setColumn_comment(String column_comment) {
        this.column_comment = column_comment;
    }

    public int getVarchardata_length() {
        if(varchardata_length==0){
            if(this.column_type.indexOf('(')!=-1){
                varchardata_length = Integer.parseInt(this.column_type.substring(column_type.indexOf('(')+1,column_type.indexOf(')')));
            }
        }
        return varchardata_length;
    }

//    public void setVarchardata_length(int varchardata_length) {
//        this.varchardata_length = varchardata_length;
//    }
}
