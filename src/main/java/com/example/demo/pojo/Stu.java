package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "stu")
public class Stu {

    @TableId(type = IdType.AUTO)
    private int id;

    private String name;

    private int classId;
}
