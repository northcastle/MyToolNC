package com.northcastle;

import com.northcastle.ui.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class MyToolNcApp {
    public static void main( String[] args ) {
        Logger logger = LoggerFactory.getLogger(MyToolNcApp.class);
        logger.info("MyToolNcApp start success");

        MainFrame.init();

    }
}
