package com.demo.design.pattern.responsibilityChain.demo1.filter;

import com.demo.design.pattern.responsibilityChain.demo1.Filter;
import com.demo.design.pattern.responsibilityChain.demo1.Msg;

public class OneFilter implements Filter {

    @Override
    public boolean doFilter(Msg msg) {
        msg.setContent(msg.getContent().replace("1", "*"));
        return true;
    }
}
