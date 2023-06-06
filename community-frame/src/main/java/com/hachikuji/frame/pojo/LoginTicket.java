package com.hachikuji.frame.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

/**
 *
 * @author hachikuji
 * @since 2021-11-29
 */
@Data
@Accessors(chain = true)
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class LoginTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String ticket;

    /**
     * 0-有效; 1-无效;
     */
    private Integer status;

    private LocalDateTime expired;


}
