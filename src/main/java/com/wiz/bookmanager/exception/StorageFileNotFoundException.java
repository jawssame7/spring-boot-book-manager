package com.wiz.bookmanager.exception;

/**
 * ストレージ内のファイルが見つからない場合のExceptionクラス
 */
public class StorageFileNotFoundException extends StorageException {

    /**
     * コンストラクター
     * @param message メッセージ
     */
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    /**
     * コンストラクター
     * @param message メッセージ
     * @param cause スタックトレース
     */
    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
