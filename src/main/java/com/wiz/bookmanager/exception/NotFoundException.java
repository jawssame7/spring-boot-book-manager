package com.wiz.bookmanager.exception;

public class NotFoundException extends RuntimeException {

    /**
     * コンストラクター
     * @param message メッセージ
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * コンストラクター
     * @param message メッセージ
     * @param cause スタックトレース
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
