package com.automation.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DataPojo {

    int year;
    float price;
    @JsonProperty("CPU model")
    String CPUModel;
    @JsonProperty("Hard disk size")
    String HardDiskSize;

}
