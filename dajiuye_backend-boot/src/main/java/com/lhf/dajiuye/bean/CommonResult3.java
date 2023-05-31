package com.lhf.dajiuye.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult3<T> implements Serializable {
        public Map message;
        public Meta meta;
}
