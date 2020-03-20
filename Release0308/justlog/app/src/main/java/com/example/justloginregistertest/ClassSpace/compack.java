package com.example.justloginregistertest.ClassSpace;

import java.io.Serializable;

public class compack extends Object implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String type;                             //标明传输包群的请求类型或者返回类型
    public int total_num;                           //标明包群的总数量
    public int id;                                  //标明当前包的id
    public Object data;                             //标明当前包的数据对象（只要是继承Object的子对象都可以
    public boolean is_last_pack;                    //标明是否为包群的最后一个包

    public compack(){}

    public compack(String type,int total_num,int id,Object data,boolean is_last_pack){              //生成一个包
        this.type = type;
        this.total_num = total_num;
        this.id = id;
        this.data = data;
        this.is_last_pack = is_last_pack;
    }

    static void make_compacks(compack []arg,String type,int total_num,Object []x){                  //打包一个包群
        int i;
        for(i = 0;i<total_num-1;i++){
            arg[i] = new compack(type, total_num, i, x[i], false);
        }
        arg[total_num - 1] = new compack(type, total_num, i, x[i], true);
    }


}