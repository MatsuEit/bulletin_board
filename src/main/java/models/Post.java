package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 利用者データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_POS)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_POS_GET_ALL,
            query = JpaConst.Q_POS_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_POS_COUNT,
            query = JpaConst.Q_POS_COUNT_DEF),
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Post {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.POS_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名前
     */
    @Column(name = JpaConst.POS_COL_NAME, nullable = false, unique = true)
    private String name;

    /**
     * パスワード
     */
    @Column(name = JpaConst.POS_COL_PASS, length = 64, nullable = false)
    private String password;

    /**
     *登録日時
     */
    @Column(name = JpaConst.POS_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

}