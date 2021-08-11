package com.wiz.bookmanager.exception;

public class IdNotFoundException extends NotFoundException {

    /**
     * コンストラクター
     * @param message メッセージ
     */
    public IdNotFoundException(String message) {
        super(message);
    }

    /**
     * コンストラクター
     * @param message メッセージ
     * @param cause スタックトレース
     */
    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
