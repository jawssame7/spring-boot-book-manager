package com.wiz.bookmanager.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DB共通Entityクラス
 * modelの共通項目が定義してあるのでmodelを作成する場合には必ずこのクラスを継承する
 */
@MappedSuperclass
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作成日
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    /**
     * 更新日
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /**
     * 削除日
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    /**
     * 作成日を返す
     * @return 作成日
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 作成日を設定する
     * @param createdAt 作成日
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 更新日を返す
     * @return 更新日
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 更新日を設定する
     * @param updatedAt 更新日
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 削除日を返す
     * @return 削除日
     */
    public Date getDeletedAt() {
        return deletedAt;
    }

    /**
     * 削除日を設定する
     * @param deletedAt　削除日
     */
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * データ作成時　作成日に現在日付を設定
     * `@PrePersist` を使うことにより作成処理前に自動でコールされる
     */
    @PrePersist
    public void addCreatedAt() {
        Date now = new Date();
        this.setCreatedAt(now);
        this.setUpdatedAt(now);
    }

    /**
     * データ更新時　更新日に現在日付を設定
     * `@PreUpdate` を使うことにより作成処理前に自動でコールされる
     */
    @PreUpdate
    public void addUpdatedAt() {
        Date now = new Date();
        this.setUpdatedAt(now);
    }

    /**
     * データ論理削除時　削除日に現在日付を設定
     */
    public void addDeletedAt() {
        Date now = new Date();
        this.setDeletedAt(now);
    }
}
