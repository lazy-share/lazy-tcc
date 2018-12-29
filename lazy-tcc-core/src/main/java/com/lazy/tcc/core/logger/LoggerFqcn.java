/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lazy.tcc.core.logger;


/**
 *
 *  Logger FQCN
 * @author laizhiyuan
 */
public class LoggerFqcn implements Logger {

    private Logger logger;

    public LoggerFqcn(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private String formatLoggerMsg(String msg) {
        return " [lazy-tcc] " + msg;
    }

    @Override
    public void trace(String msg, Throwable e) {
        try {
            logger.trace(formatLoggerMsg(msg), e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void trace(Throwable e) {
        try {
            logger.trace(e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void trace(String msg) {
        try {
            logger.trace(formatLoggerMsg(msg));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void debug(String msg, Throwable e) {
        try {
            logger.debug(formatLoggerMsg(msg), e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void debug(Throwable e) {
        try {
            logger.debug(e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void debug(String msg) {
        try {
            logger.debug(formatLoggerMsg(msg));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void info(String msg, Throwable e) {
        try {
            logger.info(formatLoggerMsg(msg), e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void info(String msg) {
        try {
            logger.info(formatLoggerMsg(msg));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void warn(String msg, Throwable e) {
        try {
            logger.warn(formatLoggerMsg(msg), e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void warn(String msg) {
        try {
            logger.warn(formatLoggerMsg(msg));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void error(String msg, Throwable e) {
        try {
            logger.error(formatLoggerMsg(msg), e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void error(String msg) {
        try {
            logger.error(formatLoggerMsg(msg));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void error(Throwable e) {
        try {
            logger.error(e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void info(Throwable e) {
        try {
            logger.info(e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void warn(Throwable e) {
        try {
            logger.warn(e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public boolean isTraceEnabled() {
        try {
            return logger.isTraceEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean isDebugEnabled() {
        try {
            return logger.isDebugEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean isInfoEnabled() {
        try {
            return logger.isInfoEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean isWarnEnabled() {
        try {
            return logger.isWarnEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean isErrorEnabled() {
        try {
            return logger.isErrorEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

}
