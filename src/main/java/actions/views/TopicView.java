package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * トピック情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class TopicView {

    /**
     * id
     */
    private Integer id;

    /**
     * トピックを投稿した利用者
     */
    private PostView post;

    /**
     * トピックのタイトル
     */
    private String title;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;
}