package com.qfedu.entity;/*
 *@ClassName:ClickNumber
 *@Author:lg
 *@Description:
 *@Date:2020/6/721:02
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("click")
public class ClickNumber {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer number;
}
