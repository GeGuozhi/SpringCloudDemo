package com.ggz.chat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.sf.cglib.core.Local;

import java.time.LocalDate;

/**
 * @author ggz on 2025/12/15
 */
@Data
public class requestVO {
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyyMMdd")
    private LocalDate startDate = LocalDate.now().minusDays(1);
}