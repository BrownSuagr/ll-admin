/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.love.low.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @name: LoveLowException
 * @description: 统一异常处理
 * @author: BrownSugar
 * @date: 2024-04-02 03:52:18
 * @version: 1.0.0
 * @see RuntimeException
 **/
@Getter
public class LoveLowException extends RuntimeException{

    private Integer status = BAD_REQUEST.value();

    public LoveLowException(String msg){
        super(msg);
    }

    public LoveLowException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
