package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * トピックのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_TOP)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_TOP_GET_ALL,
            query = JpaConst.Q_TOP_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_TOP_COUNT,
            query = JpaConst.Q_TOP_COUNT_DEF),
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Topic {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.TOP_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * トピックを投稿した利用者のID
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TOP_COL_POS, nullable = false)
    private Post post;

    /**
     * トピックのタイトル
     */
    @Column(name = JpaConst.TOP_COL_TITLE, length = 255, nullable = false)
    private String title;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.TOP_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

}