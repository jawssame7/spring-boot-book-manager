package com.wiz.bookmanager.exception;

/**
 * ストレージのExceptionクラス
 */
public class StorageException extends RuntimeException {

    /**
     * コンストラクター
     * @param message メッセージ
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * コンストラクター
     * @param message メッセージ
     * @param cause スタックトレース
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
