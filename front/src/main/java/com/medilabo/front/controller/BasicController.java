package com.medilabo.front.controller;

import com.medilabo.front.util.HeadersUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasicController {

    public final HeadersUtil headersUtil;

    @Autowired
    protected BasicController(HeadersUtil headersUtil) {
        this.headersUtil = new HeadersUtil();
    }

}
