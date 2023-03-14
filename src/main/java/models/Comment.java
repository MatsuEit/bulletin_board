package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * コメントのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_COM)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_COM_GET_ALL,
            query = JpaConst.Q_COM_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_COM_COUNT,
            query = JpaConst.Q_COM_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_COM_GET_ALL_MINE,
            query = JpaConst.Q_COM_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_COM_COUNT_ALL_MINE,
            query = JpaConst.Q_COM_COUNT_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Comment {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.COM_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * コメントを投稿した利用者
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.COM_COL_POS, nullable = false)
    private Post post;

    /**
     * コメントを投稿したトピック
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.COM_COL_TOP, nullable = false)
    private Topic topic;

    /**
     * コメントの内容
     */
    @Lob
    @Column(name = JpaConst.COM_COL_TITLE, nullable = false)
    private String title;

    /**
     * 投稿日時
     */
    @Column(name = JpaConst.COM_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

}